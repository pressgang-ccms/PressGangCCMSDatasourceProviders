package org.jboss.pressgang.ccms.provider;

import javax.ws.rs.core.PathSegment;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTCSNodeV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCSNodeCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSNodeProvider extends RESTDataProvider implements CSNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCSNodeProvider.class);

    protected RESTCSNodeProvider(RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
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
                node = loadCSNode(id, revision, "");
                getRESTEntityCache().add(node, revision);
            }
            return node;
        } catch (Exception e) {
            log.error("Failed to retrieve Content Spec Node " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CSNodeWrapper getCSNode(int id, Integer revision) {
        return getWrapperFactory().create(getRESTCSNode(id, revision), revision != null);
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
            log.error("Failed to retrieve the Related To Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedToNodes(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTCSRelatedToNodes(id, revision), RESTCSRelatedNodeV1.class, revision != null);
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
            log.error("Failed to retrieve the Related From Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedFromNodes(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTCSRelatedFromNodes(id, revision), RESTCSRelatedNodeV1.class, revision != null);
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
            final String expandString = getExpansionString(RESTCSNodeV1.CHILDREN_NAME);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode = loadCSNode(id, revision, expandString);

            if (csNode == null) {
                csNode = tempNode;
                getRESTEntityCache().add(csNode, revision);
            } else {
                csNode.setChildren_OTM(tempNode.getChildren_OTM());
            }

            return csNode.getChildren_OTM();
        } catch (Exception e) {
            log.error("Failed to retrieve the Children Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getCSNodeChildren(int id, Integer revision) {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getRESTCSNodeChildren(id, revision),
                RESTCSNodeV1.class, revision != null);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
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
            final String expandString = getExpansionString(RESTCSNodeV1.REVISIONS_NAME);

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
            log.error("Failed to retrieve the Revisions for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getCSNodeRevisions(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTCSNodeRevisions(id, revision), RESTCSNodeV1.class, true);
    }

    @Override
    public CSNodeWrapper createCSNode(final CSNodeWrapper nodeEntity) throws Exception {
        final RESTCSNodeV1 node = ((RESTCSNodeV1Wrapper) nodeEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(node);

        final RESTCSNodeV1 updatedCSNode = getRESTClient().createJSONContentSpecNode("", node);
        if (updatedCSNode != null) {
            getRESTEntityCache().add(updatedCSNode);
            return getWrapperFactory().create(updatedCSNode, false);
        } else {
            return null;
        }
    }

    @Override
    public CSNodeWrapper updateCSNode(CSNodeWrapper nodeEntity) throws Exception {
        final RESTCSNodeV1 node = ((RESTCSNodeV1Wrapper) nodeEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(node);

        final RESTCSNodeV1 updatedCSNode = getRESTClient().updateJSONContentSpecNode("", node);
        if (updatedCSNode != null) {
            getRESTEntityCache().expire(RESTCSNodeV1.class, nodeEntity.getId());
            getRESTEntityCache().add(updatedCSNode);
            return getWrapperFactory().create(updatedCSNode, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCSNode(Integer id) throws Exception {
        final RESTCSNodeV1 topic = getRESTClient().deleteJSONContentSpecNode(id, "");
        return topic != null;
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> createCSNodes(CollectionWrapper<CSNodeWrapper> topics) throws Exception {
        final RESTCSNodeCollectionV1 unwrappedCSNodes = ((RESTCSNodeCollectionV1Wrapper) topics).unwrap();

        // Clean the collection to remove anything that doesn't need to be sent to the server
        cleanCollectionForSave(unwrappedCSNodes);

        final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME);
        final RESTCSNodeCollectionV1 updatedCSNodes = getRESTClient().createJSONContentSpecNodes(expandString, unwrappedCSNodes);
        if (updatedCSNodes != null) {
            getRESTEntityCache().add(updatedCSNodes, false);
            return getWrapperFactory().createCollection(updatedCSNodes, RESTCSNodeV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> updateCSNodes(CollectionWrapper<CSNodeWrapper> topics) throws Exception {
        final RESTCSNodeCollectionV1 unwrappedCSNodes = ((RESTCSNodeCollectionV1Wrapper) topics).unwrap();

        // Clean the collection to remove anything that doesn't need to be sent to the server
        cleanCollectionForSave(unwrappedCSNodes);

        final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME);
        final RESTCSNodeCollectionV1 updatedCSNodes = getRESTClient().updateJSONContentSpecNodes(expandString, unwrappedCSNodes);
        if (updatedCSNodes != null) {
            // Expire the old cached data
            for (final RESTCSNodeV1 topic : unwrappedCSNodes.returnItems()) {
                getRESTEntityCache().expire(RESTCSNodeV1.class, topic.getId());
            }
            // Add the new data to the cache
            getRESTEntityCache().add(updatedCSNodes, false);
            return getWrapperFactory().createCollection(updatedCSNodes, RESTCSNodeV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCSNodes(final List<Integer> topicIds) throws Exception {
        final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
        final PathSegment path = new PathSegmentImpl(pathString, false);
        final RESTCSNodeCollectionV1 topics = getRESTClient().deleteJSONContentSpecNodes(path, "");
        return topics != null;
    }

    @Override
    public CSNodeWrapper newCSNode() {
        return getWrapperFactory().create(new RESTCSNodeV1(), false, CSNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> newCSNodeCollection() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(new RESTCSNodeCollectionV1(),
                RESTCSNodeV1.class, false);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode() {
        return getWrapperFactory().create(new RESTCSRelatedNodeV1(), false, CSRelatedNodeWrapper.class);
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode(final CSNodeWrapper node) {
        final RESTCSNodeV1 csNode = node == null ? null : (RESTCSNodeV1) node.unwrap();
        return getWrapperFactory().create(new RESTCSRelatedNodeV1(csNode), false, CSRelatedNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> newCSRelatedNodeCollection() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(new RESTCSRelatedNodeCollectionV1(),
                RESTCSRelatedNodeV1.class, false);
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void cleanEntityForSave(final RESTBaseEntityV1<?, ?, ?> entity) throws InvocationTargetException, IllegalAccessException {
        if (entity instanceof RESTCSNodeV1) {
            /*
             * If the entity is a CSNode then replace entities, that could cause recursive serialization issues, with a dummy entity.
             */
            final RESTCSNodeV1 node = (RESTCSNodeV1) entity;

            if (node.getParent() != null) {
                final RESTCSNodeV1 dummyParent = new RESTCSNodeV1();
                dummyParent.setId(node.getParent().getId());

                node.setParent(dummyParent);
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
