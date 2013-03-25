package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.RESTTranslatedContentSpecV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTranslatedContentSpecCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedContentSpecProvider extends RESTDataProvider implements TranslatedContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedContentSpecProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTranslatedContentSpecProvider(RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec(int id) {
        return getTranslatedContentSpec(id, null);
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec(int id, Integer revision) {
        try {
            final RESTTranslatedContentSpecV1 node;
            if (entityCache.containsKeyValue(RESTTranslatedContentSpecV1.class, id, revision)) {
                node = entityCache.get(RESTTranslatedContentSpecV1.class, id, revision);
            } else {
                if (revision == null) {
                    node = client.getJSONTranslatedContentSpec(id, "");
                    entityCache.add(node);
                } else {
                    node = client.getJSONTranslatedContentSpecRevision(id, revision, "");
                    entityCache.add(node, revision);
                }
            }
            return getWrapperFactory().create(node, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve Translated Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes(int id, Integer revision) {
        try {
            RESTTranslatedContentSpecV1 node = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedContentSpecV1.class, id, revision)) {
                node = entityCache.get(RESTTranslatedContentSpecV1.class, id, revision);

                if (node.getTranslatedNodes_OTM() != null) {
                    final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                            node.getTranslatedNodes_OTM(), RESTTranslatedCSNodeV1.class, revision != null);
                    return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
                }
            }

            /* We need to expand the all the translated nodes in the translated content collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(
                    new ExpandDataDetails(RESTTranslatedContentSpecV1.TRANSLATED_NODES_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedContentSpecV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedContentSpec(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedContentSpecRevision(id, revision, expandString);
            }

            if (node == null) {
                node = tempTopic;
                if (revision == null) {
                    entityCache.add(node);
                } else {
                    entityCache.add(node, revision);
                }
            } else {
                node.setTranslatedNodes_OTM(tempTopic.getTranslatedNodes_OTM());
            }

            final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                    node.getTranslatedNodes_OTM(), RESTTranslatedCSNodeV1.class, revision != null);
            return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
        } catch (Exception e) {
            log.error(
                    "Unable to retrieve the Translated ContentSpec Nodes for Translated ContentSpec " + id + (revision == null ? "" : ("," +
                            " Revision" + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecRevisions(int id, Integer revision) {
        try {
            RESTTranslatedContentSpecV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedContentSpecV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTTranslatedContentSpecV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTTranslatedContentSpecV1.class, true);
                }
            }

            // We need to expand the revisions in the content spec translated node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedContentSpecV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec translated node from the REST Interface
            final RESTTranslatedContentSpecV1 tempTranslatedContentSpec;
            if (revision == null) {
                tempTranslatedContentSpec = client.getJSONTranslatedContentSpec(id, expandString);
            } else {
                tempTranslatedContentSpec = client.getJSONTranslatedContentSpecRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempTranslatedContentSpec;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setRevisions(tempTranslatedContentSpec.getRevisions());
            }

            return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTTranslatedContentSpecV1.class, true);
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Translated Content Spec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecsWithQuery(String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(
                    new ExpandDataDetails(RESTv1Constants.TRANSLATED_CONTENT_SPEC_EXPANSION_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(topicsExpand));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedContentSpecCollectionV1 contentSpecs = client.getJSONTranslatedContentSpecsWithQuery(
                    new PathSegmentImpl(query, false), expandString);
            entityCache.add(contentSpecs);

            return getWrapperFactory().createCollection(contentSpecs, RESTTranslatedContentSpecV1.class, false);
        } catch (Exception e) {
            log.error("Failed to retrieve Translated Content Specs with a query", e);
        }
        return null;
    }

    @Override
    public TranslatedContentSpecWrapper createTranslatedContentSpec(
            TranslatedContentSpecWrapper translatedContentSpecEntity) throws Exception {
        final RESTTranslatedContentSpecV1 translatedContentSpec = ((RESTTranslatedContentSpecV1Wrapper) translatedContentSpecEntity)
                .unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(translatedContentSpec);

        final RESTTranslatedContentSpecV1 updatedTranslatedContentSpec = client.createJSONTranslatedContentSpec("", translatedContentSpec);
        if (updatedTranslatedContentSpec != null) {
            entityCache.expire(RESTTranslatedContentSpecV1.class, translatedContentSpecEntity.getId());
            entityCache.add(updatedTranslatedContentSpec);
            return getWrapperFactory().create(updatedTranslatedContentSpec, false);
        } else {
            return null;
        }
    }

    @Override
    public TranslatedContentSpecWrapper updateTranslatedContentSpec(
            TranslatedContentSpecWrapper translatedContentSpecEntity) throws Exception {
        final RESTTranslatedContentSpecV1 translatedContentSpec = ((RESTTranslatedContentSpecV1Wrapper) translatedContentSpecEntity)
                .unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(translatedContentSpec);

        final RESTTranslatedContentSpecV1 updatedTranslatedContentSpec = client.updateJSONTranslatedContentSpec("", translatedContentSpec);
        if (updatedTranslatedContentSpec != null) {
            entityCache.expire(RESTTranslatedContentSpecV1.class, translatedContentSpecEntity.getId());
            entityCache.add(updatedTranslatedContentSpec);
            return getWrapperFactory().create(updatedTranslatedContentSpec, false);
        } else {
            return null;
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> createTranslatedContentSpecs(
            CollectionWrapper<TranslatedContentSpecWrapper> translatedNodes) throws Exception {
        final RESTTranslatedContentSpecCollectionV1 unwrappedNodes = ((RESTTranslatedContentSpecCollectionV1Wrapper) translatedNodes)
                .unwrap();

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandNodes = new ExpandDataTrunk(
                new ExpandDataDetails(RESTv1Constants.TRANSLATED_CONTENT_SPEC_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandNodes));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTranslatedContentSpecCollectionV1 createdNodes = client.createJSONTranslatedContentSpecs(expandString, unwrappedNodes);
        if (createdNodes != null) {
            entityCache.add(createdNodes, false);
            return getWrapperFactory().createCollection(createdNodes, RESTTranslatedContentSpecV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public TranslatedContentSpecWrapper newTranslatedContentSpec() {
        return getWrapperFactory().create(new RESTTranslatedContentSpecV1(), false, TranslatedContentSpecWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedContentSpecWrapper> newTranslatedContentSpecCollection() {
        final CollectionWrapper<TranslatedContentSpecWrapper> collection = getWrapperFactory().createCollection(
                new RESTTranslatedContentSpecCollectionV1(), RESTTranslatedContentSpecV1.class, false);
        return (UpdateableCollectionWrapper<TranslatedContentSpecWrapper>) collection;
    }
}
