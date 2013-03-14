package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTranslatedCSNodeCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedCSNodeProvider extends RESTDataProvider implements TranslatedCSNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedCSNodeProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTranslatedCSNodeProvider(RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public TranslatedCSNodeWrapper getTranslatedCSNode(int id) {
        return getTranslatedCSNode(id, null);
    }

    @Override
    public TranslatedCSNodeWrapper getTranslatedCSNode(int id, Integer revision) {
        try {
            final RESTTranslatedCSNodeV1 node;
            if (entityCache.containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                node = entityCache.get(RESTTranslatedCSNodeV1.class, id, revision);
            } else {
                if (revision == null) {
                    node = client.getJSONTranslatedContentSpecNode(id, "");
                    entityCache.add(node);
                } else {
                    node = client.getJSONTranslatedContentSpecNodeRevision(id, revision, "");
                    entityCache.add(node, revision);
                }
            }
            return getWrapperFactory().create(node, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve Content Spec Translated Node " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStrings(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Node Strings using V1 of the REST Interface.");
    }

    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStrings(int id, Integer revision,
            final RESTTranslatedCSNodeV1 parent) {
        try {
            RESTTranslatedCSNodeV1 node = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                node = entityCache.get(RESTTranslatedCSNodeV1.class, id, revision);

                if (node.getTranslatedNodeStrings_OTM() != null) {
                    final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                            node.getTranslatedNodeStrings_OTM(), RESTTranslatedCSNodeStringV1.class, revision != null, parent);
                    return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedCSNodeV1.TRANSLATED_STRING_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedCSNodeV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedContentSpecNode(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedContentSpecNodeRevision(id, revision, expandString);
            }

            if (node == null) {
                node = tempTopic;
                if (revision == null) {
                    entityCache.add(node);
                } else {
                    entityCache.add(node, revision);
                }
            } else {
                node.setTranslatedNodeStrings_OTM(tempTopic.getTranslatedNodeStrings_OTM());
            }

            final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                    node.getTranslatedNodeStrings_OTM(), RESTTranslatedCSNodeStringV1.class, revision != null, parent);
            return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
        } catch (Exception e) {
            log.error(
                    "Unable to retrieve the Translated Node Strings for ContentSpec Translated Node " + id + (revision == null ? "" : ("," +
                            " Revision" + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedCSNodeRevisions(int id, Integer revision) {
        try {
            RESTTranslatedCSNodeV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTTranslatedCSNodeV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTTranslatedCSNodeV1.class, true);
                }
            }

            // We need to expand the revisions in the content spec translated node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedCSNodeV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec translated node from the REST Interface
            final RESTTranslatedCSNodeV1 tempCSTranslatedNode;
            if (revision == null) {
                tempCSTranslatedNode = client.getJSONTranslatedContentSpecNode(id, expandString);
            } else {
                tempCSTranslatedNode = client.getJSONTranslatedContentSpecNodeRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempCSTranslatedNode;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setRevisions(tempCSTranslatedNode.getRevisions());
            }

            return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTTranslatedCSNodeV1.class, true);
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> createTranslatedCSNodes(CollectionWrapper<TranslatedCSNodeWrapper> translatedNodes)
            throws Exception {
        final RESTTranslatedCSNodeCollectionV1 unwrappedNodes = ((RESTTranslatedCSNodeCollectionV1Wrapper) translatedNodes).unwrap();

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandNodes = new ExpandDataTrunk(
                new ExpandDataDetails(RESTv1Constants.CONTENT_SPEC_TRANSLATED_NODE_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandNodes));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTranslatedCSNodeCollectionV1 createdNodes = client.createJSONTranslatedContentSpecNodes(expandString, unwrappedNodes);
        if (createdNodes != null) {
            entityCache.add(createdNodes, false);
            return getWrapperFactory().createCollection(createdNodes, RESTTranslatedCSNodeV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public TranslatedCSNodeWrapper newTranslatedCSNode() {
        return getWrapperFactory().create(new RESTTranslatedCSNodeV1(), false, TranslatedCSNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> newTranslatedCSNodeCollection() {
        final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                new RESTTranslatedCSNodeCollectionV1(), RESTTranslatedCSNodeV1.class, false);
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
    }
}
