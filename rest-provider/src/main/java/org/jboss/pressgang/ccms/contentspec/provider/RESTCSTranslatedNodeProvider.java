package org.jboss.pressgang.ccms.contentspec.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.RESTCSTranslatedNodeCollectionV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSTranslatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSTranslatedNodeProvider extends RESTDataProvider implements CSTranslatedNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCSTranslatedNodeProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTCSTranslatedNodeProvider(RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CSTranslatedNodeWrapper getCSTranslatedNode(int id) {
        return getCSTranslatedNode(id, null);
    }

    @Override
    public CSTranslatedNodeWrapper getCSTranslatedNode(int id, Integer revision) {
        try {
            final RESTCSTranslatedNodeV1 node;
            if (entityCache.containsKeyValue(RESTCSTranslatedNodeV1.class, id, revision)) {
                node = entityCache.get(RESTCSTranslatedNodeV1.class, id, revision);
            } else {
                if (revision == null) {
                    node = client.getJSONContentSpecTranslatedNode(id, "");
                    entityCache.add(node);
                } else {
                    node = client.getJSONContentSpecTranslatedNodeRevision(id, revision, "");
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
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStrings(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Node Strings using V1 of the REST Interface.");
    }

    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStrings(int id, Integer revision,
            final RESTCSTranslatedNodeV1 parent) {
        try {
            RESTCSTranslatedNodeV1 node = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSTranslatedNodeV1.class, id, revision)) {
                node = entityCache.get(RESTCSTranslatedNodeV1.class, id, revision);

                if (node.getTranslatedNodeStrings_OTM() != null) {
                    final CollectionWrapper<CSTranslatedNodeStringWrapper> collection = getWrapperFactory().createCollection(
                            node.getTranslatedNodeStrings_OTM(), RESTCSTranslatedNodeStringV1.class, revision != null, parent);
                    return (UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper>) collection;
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTCSTranslatedNodeV1.TRANSLATED_STRING_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTCSTranslatedNodeV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONContentSpecTranslatedNode(id, expandString);
            } else {
                tempTopic = client.getJSONContentSpecTranslatedNodeRevision(id, revision, expandString);
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

            final CollectionWrapper<CSTranslatedNodeStringWrapper> collection = getWrapperFactory().createCollection(
                    node.getTranslatedNodeStrings_OTM(), RESTCSTranslatedNodeStringV1.class, revision != null, parent);
            return (UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper>) collection;
        } catch (Exception e) {
            log.error(
                    "Unable to retrieve the Translated Node Strings for ContentSpec Translated Node " + id + (revision == null ? "" : ("," +
                            " Revision" + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeWrapper> getCSTranslatedNodeRevisions(int id, Integer revision) {
        try {
            RESTCSTranslatedNodeV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSTranslatedNodeV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTCSTranslatedNodeV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTCSTranslatedNodeV1.class, true);
                }
            }

            // We need to expand the revisions in the content spec translated node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTCSTranslatedNodeV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec translated node from the REST Interface
            final RESTCSTranslatedNodeV1 tempCSTranslatedNode;
            if (revision == null) {
                tempCSTranslatedNode = client.getJSONContentSpecTranslatedNode(id, expandString);
            } else {
                tempCSTranslatedNode = client.getJSONContentSpecTranslatedNodeRevision(id, revision, expandString);
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

            return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTCSTranslatedNodeV1.class, true);
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeWrapper> createCSTranslatedNodes(
            CollectionWrapper<CSTranslatedNodeWrapper> translatedNodes) throws Exception {
        final RESTCSTranslatedNodeCollectionV1 unwrappedNodes = ((RESTCSTranslatedNodeCollectionV1Wrapper) translatedNodes).unwrap();

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandNodes = new ExpandDataTrunk(
                new ExpandDataDetails(RESTv1Constants.CONTENT_SPEC_TRANSLATED_NODE_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandNodes));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTCSTranslatedNodeCollectionV1 createdNodes = client.createJSONContentSpecTranslatedNodes(expandString, unwrappedNodes);
        if (createdNodes != null) {
            entityCache.add(createdNodes, false);
            return getWrapperFactory().createCollection(createdNodes, RESTCSTranslatedNodeV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public CSTranslatedNodeWrapper newCSTranslatedNode() {
        return getWrapperFactory().create(new RESTCSTranslatedNodeV1(), false, CSTranslatedNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeWrapper> newCSTranslatedNodeCollection() {
        final CollectionWrapper<CSTranslatedNodeWrapper> collection = getWrapperFactory().createCollection(
                new RESTCSTranslatedNodeCollectionV1(), RESTCSTranslatedNodeV1.class, false);
        return (UpdateableCollectionWrapper<CSTranslatedNodeWrapper>) collection;
    }
}
