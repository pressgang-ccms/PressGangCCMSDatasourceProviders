package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTContentSpecV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTContentSpecProvider extends RESTDataProvider implements ContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTContentSpecProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTContentSpecProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    public RESTContentSpecV1 getRESTContentSpec(int id) {
        return getRESTContentSpec(id, null);
    }

    @Override
    public ContentSpecWrapper getContentSpec(int id) {
        return getContentSpec(id, null);
    }

    public RESTContentSpecV1 getRESTContentSpec(int id, Integer revision) {
        try {
            final RESTContentSpecV1 contentSpec;
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTContentSpecV1.class, id, revision);
            } else {
                if (revision == null) {
                    contentSpec = client.getJSONContentSpec(id, "");
                    entityCache.add(contentSpec);
                } else {
                    contentSpec = client.getJSONContentSpecRevision(id, revision, "");
                    entityCache.add(contentSpec, revision);
                }
            }
            return contentSpec;
        } catch (Exception e) {
            log.error("Failed to retrieve Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public ContentSpecWrapper getContentSpec(int id, Integer revision) {
        return getWrapperFactory().create(getContentSpec(id, revision), revision != null);
    }

    public RESTContentSpecCollectionV1 getRESTContentSpecsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(new ExpandDataDetails("contentSpecs"));

            expand.setBranches(CollectionUtilities.toArrayList(topicsExpand));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTContentSpecCollectionV1 contentSpecs = client.getJSONContentSpecsWithQuery(new PathSegmentImpl(query, false),
                    expandString);
            entityCache.add(contentSpecs);

            return contentSpecs;
        } catch (Exception e) {
            log.error("Failed to retrieve ContentSpecs with a query", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getContentSpecsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        return getWrapperFactory().createCollection(getRESTContentSpecsWithQuery(query), RESTContentSpecV1.class, false);
    }

    public RESTTagCollectionV1 getRESTContentSpecTags(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getTags() != null) {
                    return contentSpec.getTags();
                }
            }

            // We need to expand the tags in the content spec collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTContentSpecV1.TAGS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec;
            if (revision == null) {
                tempContentSpec = client.getJSONContentSpec(id, expandString);
            } else {
                tempContentSpec = client.getJSONContentSpecRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setTags(tempContentSpec.getTags());
            }

            return contentSpec.getTags();
        } catch (Exception e) {
            log.error("Failed to retrieve the Tags for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getContentSpecTags(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTContentSpecTags(id, revision), RESTTagV1.class, revision != null);
    }

    public RESTCSNodeCollectionV1 getRESTContentSpecNodes(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = (RESTContentSpecV1) entityCache.get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getChildren_OTM() != null) {
                    return contentSpec.getChildren_OTM();
                }
            }

            // We need to expand the tags in the content spec collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTContentSpecV1.NODES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec;
            if (revision == null) {
                tempContentSpec = client.getJSONContentSpec(id, expandString);
            } else {
                tempContentSpec = client.getJSONContentSpecRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setChildren_OTM(tempContentSpec.getChildren_OTM());
            }

            return contentSpec.getChildren_OTM();
        } catch (Exception e) {
            log.error("Failed to retrieve the Children Nodes for Content Spec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getContentSpecNodes(int id, Integer revision) {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getRESTContentSpecNodes(id, revision),
                    RESTCSNodeV1.class, revision != null);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    public RESTTranslatedContentSpecCollectionV1 getRESTContentSpecTranslations(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = (RESTContentSpecV1) entityCache.get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getTranslatedContentSpecs() != null) {
                    return contentSpec.getTranslatedContentSpecs();
                }
            }

            // We need to expand the tags in the content spec collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTContentSpecV1.TRANSLATED_CONTENT_SPECS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec;
            if (revision == null) {
                tempContentSpec = client.getJSONContentSpec(id, expandString);
            } else {
                tempContentSpec = client.getJSONContentSpecRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setTranslatedContentSpecs(tempContentSpec.getTranslatedContentSpecs());
            }

            return contentSpec.getTranslatedContentSpecs();
        } catch (Exception e) {
            log.error("Failed to retrieve the Translations for Content Spec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getContentSpecTranslations(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTContentSpecTranslations(id, revision), RESTTranslatedContentSpecV1.class,
                revision != null);
    }

    public RESTContentSpecCollectionV1 getRESTContentSpecRevisions(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return contentSpec.getRevisions();
                }
            }

            // We need to expand the revisions in the content spec collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTContentSpecV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec;
            if (revision == null) {
                tempContentSpec = client.getJSONContentSpec(id, expandString);
            } else {
                tempContentSpec = client.getJSONContentSpecRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setRevisions(tempContentSpec.getRevisions());
            }

            return contentSpec.getRevisions();
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getContentSpecRevisions(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTContentSpecRevisions(id, revision), RESTContentSpecV1.class, true);
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTContentSpecProperties(int id, final Integer revision) {
        try {
            RESTContentSpecV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                topic = entityCache.get(RESTContentSpecV1.class, id, revision);

                if (topic.getProperties() != null) {
                    return topic.getProperties();
                }
            }

            // We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTContentSpecV1.PROPERTIES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST Interface
            final RESTContentSpecV1 tempContentSpec;
            if (revision == null) {
                tempContentSpec = client.getJSONContentSpec(id, expandString);
            } else {
                tempContentSpec = client.getJSONContentSpecRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempContentSpec;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setProperties(tempContentSpec.getProperties());
            }

            return topic.getProperties();
        } catch (Exception e) {
            log.error("Failed to retrieve the Properties for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getContentSpecProperties(int id, final Integer revision) {
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                getRESTContentSpecProperties(id, revision), RESTAssignedPropertyTagV1.class, revision != null,
                PropertyTagInContentSpecWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
    }

    @Override
    public String getContentSpecAsString(int id) {
        return getContentSpecAsString(id, null);
    }

    @Override
    public String getContentSpecAsString(int id, Integer revision) {
        try {
            final String contentSpec;
            if (revision == null) {
                contentSpec = client.getTEXTContentSpec(id);
            } else {
                contentSpec = client.getTEXTContentSpecRevision(id, revision);
            }
            return contentSpec;
        } catch (Exception e) {
            log.error("Failed to retrieve Content Spec String " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public ContentSpecWrapper createContentSpec(final ContentSpecWrapper contentSpecEntity) throws Exception {
        final RESTContentSpecV1 contentSpec = ((RESTContentSpecV1Wrapper) contentSpecEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(contentSpec);

        final RESTContentSpecV1 updatedContentSpec = client.createJSONContentSpec("", contentSpec);
        if (updatedContentSpec != null) {
            entityCache.add(updatedContentSpec);
            return getWrapperFactory().create(updatedContentSpec, false);
        } else {
            return null;
        }
    }

    @Override
    public ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpecEntity) throws Exception {
        final RESTContentSpecV1 contentSpec = ((RESTContentSpecV1Wrapper) contentSpecEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(contentSpec);

        final RESTContentSpecV1 updatedContentSpec = client.updateJSONContentSpec("", contentSpec);
        if (updatedContentSpec != null) {
            entityCache.expire(RESTContentSpecV1.class, contentSpecEntity.getId());
            entityCache.add(updatedContentSpec);
            return getWrapperFactory().create(updatedContentSpec, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteContentSpec(Integer id) throws Exception {
        final RESTContentSpecV1 contentSpec = client.deleteJSONContentSpec(id, "");
        return contentSpec != null;
    }

    @Override
    public ContentSpecWrapper newContentSpec() {
        return getWrapperFactory().create(new RESTContentSpecV1(), false);
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> newContentSpecCollection() {
        return getWrapperFactory().createCollection(new RESTContentSpecCollectionV1(), RESTContentSpecV1.class, false);
    }
}
