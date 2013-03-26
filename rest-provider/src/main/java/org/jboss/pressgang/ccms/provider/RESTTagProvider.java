package org.jboss.pressgang.ccms.provider;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.rest.v1.query.RESTTagQueryBuilderV1;
import org.jboss.pressgang.ccms.utils.RESTCollectionCache;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTagProvider extends RESTDataProvider implements TagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTagProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTCollectionCache collectionsCache;
    private final RESTInterfaceV1 client;

    protected RESTTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        this.client = restManager.getRESTClient();
        this.entityCache = restManager.getRESTEntityCache();
        this.collectionsCache = restManager.getRESTCollectionCache();
    }

    public RESTTagV1 getRESTTag(int id) {
        return getRESTTag(id, null);
    }

    @Override
    public TagWrapper getTag(int id) {
        return getTag(id, null);
    }

    public RESTTagV1 getRESTTag(int id, Integer revision) {
        try {
            final RESTTagV1 tag;
            if (entityCache.containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = entityCache.get(RESTTagV1.class, id, revision);
            } else {
                if (revision == null) {
                    tag = client.getJSONTag(id, "");
                    entityCache.add(tag);
                } else {
                    tag = client.getJSONTagRevision(id, revision, "");
                    entityCache.add(tag, revision);
                }
            }
            return tag;
        } catch (Exception e) {
            log.error("Failed to retrieve Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public TagWrapper getTag(int id, Integer revision) {
        return getWrapperFactory().create(getRESTTag(id, revision), revision != null);
    }

    public RESTTagCollectionV1 getRESTTagsByName(final String name) {
        final List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add(name);

        try {
            final RESTTagCollectionV1 tags;
            if (collectionsCache.containsKey(RESTTagV1.class, keys)) {
                tags = collectionsCache.get(RESTTagV1.class, RESTTagCollectionV1.class, keys);
            } else {
                final RESTTagQueryBuilderV1 queryBuilder = new RESTTagQueryBuilderV1();
                queryBuilder.setTagName(name);

                // We need to expand the Tags collection
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
                expand.setBranches(CollectionUtilities.toArrayList(expandTags));
                final String expandString = mapper.writeValueAsString(expand);

                // Load the tags from the REST Interface
                tags = client.getJSONTagsWithQuery(queryBuilder.buildQueryPath(), expandString);
                entityCache.add(tags);
            }

            return tags;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagsByName(final String name) {
        return getWrapperFactory().createCollection(getRESTTagsByName(name), RESTTagV1.class, false);
    }

    public RESTCategoryInTagCollectionV1 getRESTTagCategories(int id, final Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = entityCache.get(RESTTagV1.class, id, revision);

                if (tag.getCategories() != null) {
                    return tag.getCategories();
                }
            }

            // We need to expand the categories in the tag collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.CATEGORIES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag;
            if (revision == null) {
                tempTag = client.getJSONTag(id, expandString);
            } else {
                tempTag = client.getJSONTagRevision(id, revision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (revision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, revision);
                }
            } else {
                tag.setCategories(tempTag.getCategories());
            }

            return tag.getCategories();
        } catch (Exception e) {
            log.error("Failed to load the Categories for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getTagCategories(int id, final Integer revision) {
        final CollectionWrapper<CategoryInTagWrapper> collection = getWrapperFactory().createCollection(getRESTTagCategories(id, revision),
                RESTCategoryInTagV1.class, revision != null, CategoryInTagWrapper.class);
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
    }

    public RESTTagCollectionV1 getRESTTagChildTags(int id, final Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = entityCache.get(RESTTagV1.class, id, revision);

                if (tag.getChildTags() != null) {
                    return tag.getChildTags();
                }
            }

            // We need to expand the all child tags in the tag collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.CHILD_TAGS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandProperties));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the Tag from the REST Interface
            final RESTTagV1 tempTag;
            if (revision == null) {
                tempTag = client.getJSONTag(id, expandString);
            } else {
                tempTag = client.getJSONTagRevision(id, revision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (revision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, revision);
                }
            } else {
                tag.setChildTags(tempTag.getChildTags());
            }

            return tag.getChildTags();
        } catch (Exception e) {
            log.error("Failed to retrieve the Child Tags for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagChildTags(int id, final Integer revision) {
        return getWrapperFactory().createCollection(getRESTTagChildTags(id, revision), RESTTagV1.class, revision != null);
    }

    public RESTTagCollectionV1 getRESTTagParentTags(int id, Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = entityCache.get(RESTTagV1.class, id, revision);

                if (tag.getParentTags() != null) {
                    return tag.getParentTags();
                }
            }

            // We need to expand the parent tags in the tag collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.PARENT_TAGS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandProperties));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag;
            if (revision == null) {
                tempTag = client.getJSONTag(id, expandString);
            } else {
                tempTag = client.getJSONTagRevision(id, revision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (revision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, revision);
                }
            } else {
                tag.setParentTags(tempTag.getParentTags());
            }

            return tag.getParentTags();
        } catch (Exception e) {
            log.error("Failed to retrieve the Parent Tags for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagParentTags(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTTagParentTags(id, revision), RESTTagV1.class, revision != null);
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTTagProperties(int id, Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = entityCache.get(RESTTagV1.class, id, revision);

                if (tag.getProperties() != null) {
                    return tag.getProperties();
                }
            }

            // We need to expand the properties in the tag collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.PROPERTIES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandProperties));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag;
            if (revision == null) {
                tempTag = client.getJSONTag(id, expandString);
            } else {
                tempTag = client.getJSONTagRevision(id, revision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (revision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, revision);
                }
            } else {
                tag.setProperties(tempTag.getProperties());
            }

            return tag.getProperties();
        } catch (Exception e) {
            log.error("Failed to retrieve the Properties for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> getTagProperties(int id, Integer revision) {
        final CollectionWrapper<PropertyTagInTagWrapper> collection = getWrapperFactory().createCollection(
                getRESTTagProperties(id, revision), RESTAssignedPropertyTagV1.class, revision != null, PropertyTagInTagWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) collection;
    }

    public RESTTagCollectionV1 getRESTTagRevisions(int id, final Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = entityCache.get(RESTTagV1.class, id, revision);

                if (tag.getRevisions() != null) {
                    return tag.getRevisions();
                }
            }

            // We need to expand the revisions in the tag collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTagV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the tags from the REST Interface
            final RESTTagV1 tempTag;
            if (revision == null) {
                tempTag = client.getJSONTag(id, expandString);
            } else {
                tempTag = client.getJSONTagRevision(id, revision, expandString);
            }

            if (tag == null) {
                tag = tempTag;
                if (revision == null) {
                    entityCache.add(tag);
                } else {
                    entityCache.add(tag, revision);
                }
            } else {
                tag.setRevisions(tempTag.getRevisions());
            }

            return tag.getRevisions();
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagRevisions(int id, final Integer revision) {
        return getWrapperFactory().createCollection(getRESTTagRevisions(id, revision), RESTTagV1.class, true);
    }

    @Override
    public TagWrapper newTag() {
        return getWrapperFactory().create(new RESTTagV1(), false);
    }

    @Override
    public TagInCategoryWrapper newTagInCategory() {
        return getWrapperFactory().create(new RESTTagInCategoryV1(), false, TagInCategoryWrapper.class);
    }

    @Override
    public CollectionWrapper<TagWrapper> newTagCollection() {
        return getWrapperFactory().createCollection(new RESTTagCollectionV1(), RESTTagV1.class, false);
    }

    @Override
    public CollectionWrapper<TagInCategoryWrapper> newTagInCategoryCollection() {
        return getWrapperFactory().createCollection(new RESTTagInCategoryCollectionV1(), RESTTagInCategoryV1.class, false,
                TagInCategoryWrapper.class);
    }
}
