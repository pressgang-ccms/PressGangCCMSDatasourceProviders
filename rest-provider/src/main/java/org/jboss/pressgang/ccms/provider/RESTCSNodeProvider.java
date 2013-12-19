package org.jboss.pressgang.ccms.provider;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseObjectV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSNodeProvider extends RESTDataProvider implements CSNodeProvider {
    private static final Logger log = LoggerFactory.getLogger(RESTCSNodeProvider.class);
    protected static final List<String> DEFAULT_EXPANSION = Arrays.asList(RESTCSNodeV1.NEXT_NODE_NAME,
            RESTCSNodeV1.RELATED_TO_NAME);
    protected static final List<String> DEFAULT_METHODS = Arrays.asList("getNextNode", "getRelatedToNodes");

    protected RESTCSNodeProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTCSNodeV1 loadCSNode(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONContentSpecNode(id, expandString);
        } else {
            return getRESTClient().getJSONContentSpecNodeRevision(id, revision, expandString);
        }
    }

    public RESTCSNodeV1 getRESTCSNode(int id) {
        return getRESTCSNode(id, null);
    }

    @Override
    public CSNodeWrapper getCSNode(int id) {
        return getCSNode(id, null);
    }

    public RESTCSNodeV1 getRESTCSNode(int id, Integer revision) {
        try {
            final RESTCSNodeV1 node;
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                node = getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);
            } else {
                final String expansionString = getExpansionString(DEFAULT_EXPANSION);
                node = loadCSNode(id, revision, expansionString);
                getRESTEntityCache().add(node, revision);
                getRESTEntityCache().add(node.getNextNode(), revision != null);
            }
            return node;
        } catch (Exception e) {
            log.debug("Failed to retrieve Content Spec Node " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CSNodeWrapper getCSNode(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTCSNextNode(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTCSNodeCollectionV1 getRESTCSNodesWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            // We need to expand the all the content specs in the collection
            final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME, DEFAULT_EXPANSION);

            final RESTCSNodeCollectionV1 csNodes = getRESTClient().getJSONContentSpecNodesWithQuery(new PathSegmentImpl(query,
                    false), expandString);
            getRESTEntityCache().add(csNodes);

            return csNodes;
        } catch (Exception e) {
            log.debug("Failed to retrieve CSNodes with Query: " + query, e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getCSNodesWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<CSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCSNodesWithQuery(query))
                .expandedEntityMethods(DEFAULT_METHODS)
                .build();
    }

    public RESTCSRelatedNodeCollectionV1 getRESTCSRelatedToNodes(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getRelatedToNodes() != null) {
                    return csNode.getRelatedToNodes();
                }
            }

            // We need to expand the related to nodes in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.RELATED_TO_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setRelatedToNodes(tempNode.getRelatedToNodes());
            }

            return csNode.getRelatedToNodes();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Related To Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedToNodes(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<CSRelatedNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCSRelatedToNodes(id, revision))
                .isRevisionCollection(revision != null)
                .build();
    }

    public RESTCSRelatedNodeCollectionV1 getRESTCSRelatedFromNodes(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getRelatedFromNodes() != null) {
                    return csNode.getRelatedFromNodes();
                }
            }

            // We need to expand the related from nodes in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.RELATED_FROM_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setRelatedFromNodes(tempNode.getRelatedFromNodes());
            }

            return csNode.getRelatedFromNodes();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Related From Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedFromNodes(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<CSRelatedNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCSRelatedFromNodes(id, revision))
                .isRevisionCollection(revision != null)
                .build();
    }

    public RESTCSNodeCollectionV1 getRESTCSNodeChildren(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getChildren_OTM() != null) {
                    return csNode.getChildren_OTM();
                }
            }

            // We need to expand the children nodes in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.CHILDREN_NAME, DEFAULT_EXPANSION);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setChildren_OTM(tempNode.getChildren_OTM());
            }
            getRESTEntityCache().add(csNode.getChildren_OTM(), revision != null);

            return csNode.getChildren_OTM();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Children Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getCSNodeChildren(int id, Integer revision) {
        return (UpdateableCollectionWrapper<CSNodeWrapper>) RESTCollectionWrapperBuilder.<CSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCSNodeChildren(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(DEFAULT_METHODS)
                .build();
    }

    public RESTCSNodeCollectionV1 getRESTCSNodeRevisions(int id, Integer revision) {
        try {
            RESTCSNodeV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return contentSpec.getRevisions();
                }
            }

            // We need to expand the revisions in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.REVISIONS_NAME, DEFAULT_EXPANSION);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempCSNode = loadCSNode(id, revision, expandString);

            if (contentSpec == null) {
                contentSpec = tempCSNode;
                getRESTEntityCache().add(contentSpec, revision);
            } else {
                contentSpec.setRevisions(tempCSNode.getRevisions());
            }

            return contentSpec.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getCSNodeRevisions(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<CSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCSNodeRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(DEFAULT_METHODS)
                .build();
    }

    public String getRESTCSNodeInheritedCondition(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getInheritedCondition() != null) {
                    return csNode.getInheritedCondition();
                }
            }

            // We need to expand the inherited children
            final String expandString = getExpansionString(RESTCSNodeV1.INHERITED_CONDITION_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setInheritedCondition(tempNode.getInheritedCondition());
            }

            return csNode.getInheritedCondition();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Next Node for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTCSNodeV1 getRESTCSNextNode(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getNextNode() != null) {
                    return csNode.getNextNode();
                }
            }

            // We need to expand the children nodes in the content spec node
            final String expandString = getExpansionString(DEFAULT_EXPANSION);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setNextNode(tempNode.getNextNode());
            }
            getRESTEntityCache().add(csNode.getNextNode(), revision != null);

            return csNode.getNextNode();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Next Node for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTCSNodeV1 getRESTCSNodeParent(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getParent() != null) {
                    return csNode.getParent();
                }
            }

            // We need to expand the children nodes in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.PARENT_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setParent(tempNode.getParent());
            }
            getRESTEntityCache().add(csNode.getParent(), revision != null);

            return csNode.getParent();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Parent Node for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTContentSpecV1 getRESTCSNodeContentSpec(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getContentSpec() != null) {
                    return csNode.getContentSpec();
                }
            }

            // We need to expand the children nodes in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.CONTENT_SPEC_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setContentSpec(tempNode.getContentSpec());
            }
            getRESTEntityCache().add(csNode.getContentSpec(), revision != null);

            return csNode.getContentSpec();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Content Spec for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTTranslatedCSNodeCollectionV1 getRESTTranslatedCSNodes(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) getRESTEntityCache().get(RESTCSNodeV1.class, id, revision);

                if (csNode.getTranslatedNodes_OTM() != null) {
                    return csNode.getTranslatedNodes_OTM();
                }
            }

            // We need to expand the related from nodes in the content spec node
            final String expandString = getExpansionString(RESTCSNodeV1.TRANSLATED_NODES_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setTranslatedNodes_OTM(tempNode.getTranslatedNodes_OTM());
            }

            return csNode.getTranslatedNodes_OTM();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Translated Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

//    @Override
//    public CSNodeWrapper createCSNode(final CSNodeWrapper nodeEntity) throws Exception {
//        final RESTCSNodeV1 node = ((RESTCSNodeV1Wrapper) nodeEntity).unwrap();
//
//        // Clean the entity to remove anything that doesn't need to be sent to the server
//        cleanEntityForSave(node);
//
//        final RESTCSNodeV1 updatedCSNode = getRESTClient().createJSONContentSpecNode("", node);
//        if (updatedCSNode != null) {
//            getRESTEntityCache().add(updatedCSNode);
//            return getWrapperFactory().create(updatedCSNode, false);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public CSNodeWrapper updateCSNode(CSNodeWrapper nodeEntity) throws Exception {
//        final RESTCSNodeV1 node = ((RESTCSNodeV1Wrapper) nodeEntity).unwrap();
//
//        // Clean the entity to remove anything that doesn't need to be sent to the server
//        cleanEntityForSave(node);
//
//        final RESTCSNodeV1 updatedCSNode = getRESTClient().updateJSONContentSpecNode("", node);
//        if (updatedCSNode != null) {
//            getRESTEntityCache().expire(RESTCSNodeV1.class, nodeEntity.getId());
//            getRESTEntityCache().add(updatedCSNode);
//            return getWrapperFactory().create(updatedCSNode, false);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public boolean deleteCSNode(Integer id) throws Exception {
//        final RESTCSNodeV1 topic = getRESTClient().deleteJSONContentSpecNode(id, "");
//        return topic != null;
//    }
//
//    @Override
//    public CollectionWrapper<CSNodeWrapper> createCSNodes(CollectionWrapper<CSNodeWrapper> topics) throws Exception {
//        final RESTCSNodeCollectionV1 unwrappedCSNodes = ((RESTCSNodeCollectionV1Wrapper) topics).unwrap();
//
//        // Clean the collection to remove anything that doesn't need to be sent to the server
//        cleanCollectionForSave(unwrappedCSNodes);
//
//        final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME);
//        final RESTCSNodeCollectionV1 updatedCSNodes = getRESTClient().createJSONContentSpecNodes(expandString, unwrappedCSNodes);
//        if (updatedCSNodes != null) {
//            getRESTEntityCache().add(updatedCSNodes, false);
//            return getWrapperFactory().createCollection(updatedCSNodes, RESTCSNodeV1.class, false);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public CollectionWrapper<CSNodeWrapper> updateCSNodes(CollectionWrapper<CSNodeWrapper> topics) throws Exception {
//        final RESTCSNodeCollectionV1 unwrappedCSNodes = ((RESTCSNodeCollectionV1Wrapper) topics).unwrap();
//
//        // Clean the collection to remove anything that doesn't need to be sent to the server
//        cleanCollectionForSave(unwrappedCSNodes);
//
//        final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME);
//        final RESTCSNodeCollectionV1 updatedCSNodes = getRESTClient().updateJSONContentSpecNodes(expandString, unwrappedCSNodes);
//        if (updatedCSNodes != null) {
//            // Expire the old cached data
//            for (final RESTCSNodeV1 topic : unwrappedCSNodes.returnItems()) {
//                getRESTEntityCache().expire(RESTCSNodeV1.class, topic.getId());
//            }
//            // Add the new data to the cache
//            getRESTEntityCache().add(updatedCSNodes, false);
//            return getWrapperFactory().createCollection(updatedCSNodes, RESTCSNodeV1.class, false);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public boolean deleteCSNodes(final List<Integer> topicIds) throws Exception {
//        final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
//        final PathSegment path = new PathSegmentImpl(pathString, false);
//        final RESTCSNodeCollectionV1 topics = getRESTClient().deleteJSONContentSpecNodes(path, "");
//        return topics != null;
//    }

    @Override
    public CSNodeWrapper newCSNode() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTCSNodeV1())
                .newEntity()
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> newCSNodeCollection() {
        return (UpdateableCollectionWrapper<CSNodeWrapper>) RESTCollectionWrapperBuilder.<CSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTCSNodeCollectionV1())
                .build();
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTCSRelatedNodeV1())
                .newEntity()
                .build();
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode(final CSNodeWrapper node) {
        final RESTCSNodeV1 csNode = node == null ? null : (RESTCSNodeV1) node.unwrap();
        final RESTCSRelatedNodeV1 relatedNode = new RESTCSRelatedNodeV1();
        if (csNode != null) {
            relatedNode.setId(csNode.getId());
            relatedNode.setTitle(csNode.getTitle());
            relatedNode.setAdditionalText(csNode.getAdditionalText());
            relatedNode.setCondition(csNode.getCondition());
            relatedNode.setEntityId(csNode.getEntityId());
            relatedNode.setEntityRevision(csNode.getEntityRevision());
            relatedNode.setTargetId(csNode.getTargetId());
        }
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(relatedNode)
                .newEntity()
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> newCSRelatedNodeCollection() {
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) RESTCollectionWrapperBuilder.<CSRelatedNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTCSRelatedNodeCollectionV1())
                .build();
    }

    @Override
    public void cleanEntityForSave(final RESTBaseObjectV1<?> entity) throws InvocationTargetException, IllegalAccessException {
        if (entity instanceof RESTCSNodeV1) {
            /*
             * If the entity is a CSNode then replace entities, that could cause recursive serialization issues, with a dummy entity.
             */
            final RESTCSNodeV1 node = (RESTCSNodeV1) entity;

            if (node.getParent() != null) {
                node.setParent(null);
            }

            if (node.getContentSpec() != null) {
                final RESTContentSpecV1 dummyContentSpec = new RESTContentSpecV1();
                dummyContentSpec.setId(node.getContentSpec().getId());

                node.setContentSpec(dummyContentSpec);
            }
        }

        super.cleanEntityForSave(entity);
    }
}
