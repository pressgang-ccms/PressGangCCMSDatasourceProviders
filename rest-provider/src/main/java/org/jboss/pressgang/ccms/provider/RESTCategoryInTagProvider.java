package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTCategoryInTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCategoryInTagProvider extends RESTCategoryProvider implements CategoryInTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCategoryInTagProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTCategoryInTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<CategoryInTagWrapper> getCategoryInTagRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get CategoryInTag revisions using V1 of the REST Interface.");
    }

    public RESTCategoryInTagCollectionV1 getRESTCategoryInTagRevisions(int id, Integer revision, final RESTBaseTagV1<?, ?, ?> parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, tagId, tagRevision)) {
                tag = entityCache.get(RESTTagV1.class, tagId, tagRevision);
            }

                    /* We need to expand the all the items in the tag collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.CATEGORIES_NAME));
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTCategoryV1.REVISIONS_NAME));

            expandCategories.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTagV1 tempTag;
            if (tagRevision == null) {
                tempTag = client.getJSONTag(tagId, expandString);
            } else {
                tempTag = client.getJSONTagRevision(tagId, tagRevision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (tagRevision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, tagRevision);
                }
            } else if (tag.getCategories() == null) {
                tag.setCategories(tempTag.getCategories());
            } else {
                // Iterate over the current and old source urls and add any missing objects.
                final List<RESTCategoryInTagV1> categories = tag.getCategories().returnItems();
                final List<RESTCategoryInTagV1> newCategories = tempTag.getCategories().returnItems();
                for (final RESTCategoryInTagV1 newCategory : newCategories) {
                    boolean found = false;

                    for (final RESTCategoryInTagV1 category : categories) {
                        if (category.getId().equals(newCategory.getId())) {
                            category.setRevisions(newCategory.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        tag.getCategories().addItem(newCategory);
                    }
                }
            }

            for (final RESTCategoryInTagCollectionItemV1 categoryItem : tag.getCategories().getItems()) {
                final RESTCategoryInTagV1 category = categoryItem.getItem();

                if (category.getId() == id && (revision == null || category.getRevision().equals(revision))) {
                    return category.getRevisions();
                }
            }
        } catch (Exception e) {
            log.error("Unable to retrieve the Revision for CategoryInTag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }

        return null;
    }

    public CollectionWrapper<CategoryInTagWrapper> getCategoryInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        return getWrapperFactory().createCollection(getRESTCategoryInTagRevisions(id, revision, parent), RESTCategoryInTagV1.class, true, parent);
    }
}
