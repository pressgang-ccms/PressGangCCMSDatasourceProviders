package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCategoryProvider extends RESTDataProvider implements CategoryProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCategoryProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTCategoryProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    public RESTCategoryV1 getRESTCategory(int id) {
        return getRESTCategory(id, null);
    }

    @Override
    public CategoryWrapper getCategory(int id) {
        return getCategory(id, null);
    }

    public RESTCategoryV1 getRESTCategory(int id, final Integer revision) {
        try {
            final RESTCategoryV1 category;
            if (entityCache.containsKeyValue(RESTCategoryV1.class, id, revision)) {
                category = entityCache.get(RESTCategoryV1.class, id, revision);
            } else {
                if (revision == null) {
                    category = client.getJSONCategory(id, "");
                    entityCache.add(category);
                } else {
                    category = client.getJSONCategoryRevision(id, revision, "");
                    entityCache.add(category, revision);
                }
            }
            return category;
        } catch (Exception e) {
            log.error("Failed to retrieve Category " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CategoryWrapper getCategory(int id, final Integer revision) {
        return getWrapperFactory().create(getRESTCategory(id, revision), revision != null);
    }

    public RESTTagInCategoryCollectionV1 getRESTCategoryTags(int id, final Integer revision) {
        try {
            RESTCategoryV1 category = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCategoryV1.class, id, revision)) {
                category = entityCache.get(RESTCategoryV1.class, id, revision);

                if (category.getTags() != null) {
                    return category.getTags();
                }
            }

            // We need to expand the tags in the category collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTCategoryV1.TAGS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the category from the REST Interface
            final RESTCategoryV1 tempCategory;
            if (revision == null) {
                tempCategory = client.getJSONCategory(id, expandString);
            } else {
                tempCategory = client.getJSONCategoryRevision(id, revision, expandString);
            }

            if (category == null) {
                category = tempCategory;
                if (revision == null) {
                    entityCache.add(category);
                } else {
                    entityCache.add(category, revision);
                }
            } else {
                category.setTags(tempCategory.getTags());
            }

            return category.getTags();
        } catch (Exception e) {
            log.error("Failed to retrieve the Tags for Category " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getCategoryTags(int id, final Integer revision) {
        final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(getRESTCategoryTags(id, revision),
                RESTTagInCategoryV1.class, revision != null, TagInCategoryWrapper.class);
        return (UpdateableCollectionWrapper<TagInCategoryWrapper>) collection;
    }

    public RESTCategoryCollectionV1 getRESTCategoryRevisions(int id, final Integer revision) {
        try {
            RESTCategoryV1 category = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCategoryV1.class, id, revision)) {
                category = entityCache.get(RESTCategoryV1.class, id, revision);

                if (category.getRevisions() != null) {
                    return category.getRevisions();
                }
            }

            // We need to expand the revisions in the category collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTCategoryV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the category from the REST Interface
            final RESTCategoryV1 tempCategory;
            if (revision == null) {
                tempCategory = client.getJSONCategory(id, expandString);
            } else {
                tempCategory = client.getJSONCategoryRevision(id, revision, expandString);
            }

            if (category == null) {
                category = tempCategory;
                if (revision == null) {
                    entityCache.add(category);
                } else {
                    entityCache.add(category, revision);
                }
            } else {
                category.setRevisions(tempCategory.getRevisions());
            }

            return category.getRevisions();
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Category " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CategoryWrapper> getCategoryRevisions(int id, final Integer revision) {
        return getWrapperFactory().createCollection(getRESTCategoryRevisions(id, revision), RESTCategoryV1.class, true);
    }
}
