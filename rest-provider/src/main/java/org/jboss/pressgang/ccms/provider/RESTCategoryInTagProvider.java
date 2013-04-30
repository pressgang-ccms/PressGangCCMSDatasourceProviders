package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTCategoryInTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCategoryInTagProvider extends RESTCategoryProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCategoryInTagProvider.class);

    protected RESTCategoryInTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTTagV1 loadTag(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTag(id, expandString);
        } else {
            return getRESTClient().getJSONTagRevision(id, revision, expandString);
        }
    }

    public RESTCategoryInTagCollectionV1 getRESTCategoryInTagRevisions(int id, Integer revision, final RESTBaseTagV1<?, ?, ?> parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTagV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, tagId, tagRevision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, tagId, tagRevision);
            }

            // We need to expand the all the categories and their revisions in the tag
            final String expandString = getExpansionString(RESTTagV1.CATEGORIES_NAME, RESTCategoryV1.REVISIONS_NAME);

            final RESTTagV1 tempTag = loadTag(tagId, tagRevision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, tagRevision);
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

            // Find the category in the collection and return it's revisions.
            for (final RESTCategoryInTagCollectionItemV1 categoryItem : tag.getCategories().getItems()) {
                final RESTCategoryInTagV1 category = categoryItem.getItem();

                if (category.getId() == id && (revision == null || category.getRevision().equals(revision))) {
                    return category.getRevisions();
                }
            }

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revision for CategoryInTag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<CategoryInTagWrapper> getCategoryInTagRevisions(int id, Integer revision,
            final RESTBaseTagV1<?, ?, ?> parent) {
        return getWrapperFactory().createCollection(getRESTCategoryInTagRevisions(id, revision, parent), RESTCategoryInTagV1.class, true, parent);
    }
}
