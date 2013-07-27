package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSRelatedNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBCSNodeWrapper extends DBBaseWrapper<CSNodeWrapper, CSNode> implements CSNodeWrapper {
    private CSNodeCollectionEventListener csNodeCollectionEventListener = new CSNodeCollectionEventListener();
    private final CSRelatedToNodeCollectionEventListener csRelatedToNodeCollectionEventListener =
            new CSRelatedToNodeCollectionEventListener();
    private final CSRelatedFromNodeCollectionEventListener csRelatedFromNodeCollectionEventListener =
            new CSRelatedFromNodeCollectionEventListener();

    private final CSNode csNode;

    public DBCSNodeWrapper(final DBProviderFactory providerFactory, final CSNode csNode, boolean isRevision) {
        super(providerFactory, isRevision, CSNode.class);
        this.csNode = csNode;
    }

    @Override
    protected CSNode getEntity() {
        return csNode;
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getRelatedToNodes(),
                CSNodeToCSNode.class, isRevisionEntity());
        final DBCSRelatedNodeCollectionWrapper dbCollection = (DBCSRelatedNodeCollectionWrapper) collection;
        dbCollection.registerEventListener(csRelatedToNodeCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes) {
        if (relatedToNodes == null) return;
        final DBCSRelatedNodeCollectionWrapper dbRelatedToNodes = (DBCSRelatedNodeCollectionWrapper) relatedToNodes;
        dbRelatedToNodes.registerEventListener(csRelatedToNodeCollectionEventListener);

        // Remove the current Related Nodes
        final Set<CSNodeToCSNode> currentRelatedToNodes = new HashSet<CSNodeToCSNode>(getEntity().getRelatedToNodes());
        for (final CSNodeToCSNode relatedToNode : currentRelatedToNodes) {
            getEntity().removeRelatedTo(relatedToNode);
        }

        // Add new Related Nodes
        final Collection<CSNodeToCSNode> newRelatedToNodes = dbRelatedToNodes.unwrap();
        for (final CSNodeToCSNode relatedToNode : newRelatedToNodes) {
            relatedToNode.setMainNode(getEntity());
            getEntity().addRelatedTo(relatedToNode);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getRelatedFromNodes(),
                CSNodeToCSNode.class, isRevisionEntity());
        final DBCSRelatedNodeCollectionWrapper dbCollection = (DBCSRelatedNodeCollectionWrapper) collection;
        dbCollection.registerEventListener(csRelatedFromNodeCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes) {
        if (relatedFromNodes == null) return;
        final DBCSRelatedNodeCollectionWrapper dbRelatedFromNodes = (DBCSRelatedNodeCollectionWrapper) relatedFromNodes;
        dbRelatedFromNodes.registerEventListener(csRelatedFromNodeCollectionEventListener);

        // Remove the current Related Nodes
        final Set<CSNodeToCSNode> currentRelatedFromNodes = new HashSet<CSNodeToCSNode>(getEntity().getRelatedFromNodes());
        for (final CSNodeToCSNode relatedFromNode : currentRelatedFromNodes) {
            getEntity().removeRelatedFrom(relatedFromNode);
        }

        // Add new Related Nodes
        final Collection<CSNodeToCSNode> newRelatedFromNodes = dbRelatedFromNodes.unwrap();
        for (final CSNodeToCSNode relatedFromNode : newRelatedFromNodes) {
            relatedFromNode.setMainNode(getEntity());
            getEntity().addRelatedFrom(relatedFromNode);
        }
    }

    @Override
    public CSNodeWrapper getParent() {
        return getWrapperFactory().create(getEntity().getParent(), isRevisionEntity());
    }

    @Override
    public void setParent(CSNodeWrapper parent) {
        getEntity().setParent(parent == null ? null : (CSNode) parent.unwrap());
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return getWrapperFactory().create(getEntity().getContentSpec(), isRevisionEntity());
    }

    @Override
    public void setContentSpec(ContentSpecWrapper contentSpec) {
        getEntity().setContentSpec(contentSpec == null ? null : (ContentSpec) contentSpec.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getChildren() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getChildren(), CSNode.class,
                isRevisionEntity());
        final DBCSNodeCollectionWrapper dbCollection = (DBCSNodeCollectionWrapper) collection;
        dbCollection.registerEventListener(csNodeCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes) {
        if (nodes == null) return;
        final DBCSNodeCollectionWrapper dbNodes = (DBCSNodeCollectionWrapper) nodes;
        dbNodes.registerEventListener(csNodeCollectionEventListener);

        // Remove the current children
        final Set<CSNode> children = new HashSet<CSNode>(getEntity().getChildren());
        for (final CSNode child : children) {
            getEntity().removeChild(child);
        }

        // Set the new children
        final Collection<CSNode> newChildren = dbNodes.unwrap();
        for (final CSNode child : newChildren) {
            getEntity().addChild(child);
        }
    }

    @Override
    public String getTitle() {
        return getEntity().getCSNodeTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setCSNodeTitle(title);
    }

    @Override
    public String getTargetId() {
        return getEntity().getCSNodeTargetId();
    }

    @Override
    public void setTargetId(String targetId) {
        getEntity().setCSNodeTargetId(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getEntity().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getEntity().setAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(condition);
    }

    @Override
    public Integer getEntityId() {
        return getEntity().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getEntity().setEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getEntity().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getEntity().setEntityRevision(revision);
    }

    @Override
    public CSNodeWrapper getNextNode() {
        return getWrapperFactory().create(getEntity().getNext(), isRevisionEntity());
    }

    @Override
    public void setNextNode(CSNodeWrapper nextNode) {
        getEntity().setNextAndClean(nextNode == null ? null : (CSNode) nextNode.unwrap());
    }

    @Override
    public Integer getNodeType() {
        return getEntity().getCSNodeType();
    }

    @Override
    public void setNodeType(Integer typeId) {
        getEntity().setCSNodeType(typeId);
    }

    @Override
    public void setId(Integer id) {
        getEntity().setCSNodeId(id);
    }

    @Override
    public CSNode unwrap() {
        return csNode;
    }

    /**
     *
     */
    private class CSNodeCollectionEventListener implements UpdateableCollectionEventListener<CSNode> {
        @Override
        public void onAddItem(final CSNode entity) {
            getEntity().addChild(entity);
        }

        @Override
        public void onRemoveItem(final CSNode entity) {
            getEntity().removeChild(entity);
        }

        @Override
        public void onUpdateItem(final CSNode entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CSNodeCollectionEventListener;
        }
    }

    /**
     *
     */
    private class CSRelatedToNodeCollectionEventListener implements UpdateableCollectionEventListener<CSNodeToCSNode> {
        @Override
        public void onAddItem(final CSNodeToCSNode entity) {
            getEntity().addRelatedTo(entity);
        }

        @Override
        public void onRemoveItem(final CSNodeToCSNode entity) {
            getEntity().removeRelatedTo(entity);
        }

        @Override
        public void onUpdateItem(final CSNodeToCSNode entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CSRelatedToNodeCollectionEventListener;
        }
    }

    /**
     *
     */
    private class CSRelatedFromNodeCollectionEventListener implements UpdateableCollectionEventListener<CSNodeToCSNode> {
        @Override
        public void onAddItem(final CSNodeToCSNode entity) {
            getEntity().addRelatedFrom(entity);
        }

        @Override
        public void onRemoveItem(final CSNodeToCSNode entity) {
            getEntity().removeRelatedFrom(entity);
        }

        @Override
        public void onUpdateItem(final CSNodeToCSNode entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CSRelatedFromNodeCollectionEventListener;
        }
    }
}
