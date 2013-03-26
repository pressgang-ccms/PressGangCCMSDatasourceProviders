package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTTagInCategoryCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTagInCategoryProvider extends RESTTagProvider implements TagInCategoryProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTagInCategoryProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTagInCategoryProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<TagInCategoryWrapper> getTagInCategoryRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get TagInCategory revisions using V1 of the REST Interface.");
    }

    public RESTTagInCategoryCollectionV1 getRESTTagInCategoryRevisions(int id, Integer revision, final RESTBaseCategoryV1<?, ?, ?> parent) {
        final Integer categoryId = parent.getId();
        final Integer categoryRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTCategoryV1 category = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCategoryV1.class, categoryId, categoryRevision)) {
                category = entityCache.get(RESTCategoryV1.class, categoryId, categoryRevision);
            }
    
            /* We need to expand the all the items in the category collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTCategoryV1.TAGS_NAME));
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.REVISIONS_NAME));

            expandTags.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTCategoryV1 tempCategory;
            if (categoryRevision == null) {
                tempCategory = client.getJSONCategory(categoryId, expandString);
            } else {
                tempCategory = client.getJSONCategoryRevision(categoryId, categoryRevision, expandString);
            }

            if (category == null) {
                category = tempCategory;
                if (categoryRevision == null) {
                    entityCache.add(category);
                } else {
                    entityCache.add(category, categoryRevision);
                }
            } else if (category.getTags() == null) {
                category.setTags(tempCategory.getTags());
            } else {
                // Iterate over the current and old source urls and add any missing objects.
                final List<RESTTagInCategoryV1> tags = category.getTags().returnItems();
                final List<RESTTagInCategoryV1> newTags = tempCategory.getTags().returnItems();
                for (final RESTTagInCategoryV1 newTag : newTags) {
                    boolean found = false;

                    for (final RESTTagInCategoryV1 tag : tags) {
                        if (tag.getId().equals(newTag.getId())) {
                            tag.setRevisions(newTag.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        category.getTags().addItem(newTag);
                    }
                }
            }

            for (final RESTTagInCategoryCollectionItemV1 tagItem : category.getTags().getItems()) {
                final RESTTagInCategoryV1 tag = tagItem.getItem();

                if (tag.getId() == id && (revision == null || tag.getRevision().equals(revision))) {
                    return tag.getRevisions();
                }
            }

        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for TagInCategory " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }

        return null;
    }

    public CollectionWrapper<TagInCategoryWrapper> getTagInCategoryRevisions(int id, Integer revision,
            final RESTBaseCategoryV1<?, ?, ?> parent) {
        return getWrapperFactory().createCollection(getRESTTagInCategoryRevisions(id, revision, parent), RESTTagInCategoryV1.class, true,
                parent);
    }
}
