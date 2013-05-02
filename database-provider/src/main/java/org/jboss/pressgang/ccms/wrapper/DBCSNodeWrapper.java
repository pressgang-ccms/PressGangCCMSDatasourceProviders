package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCSNodeWrapper extends DBBaseWrapper<CSNodeWrapper, CSNode> implements CSNodeWrapper {
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
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes) {
        if (relatedToNodes == null) return;

        final List<CSRelatedNodeWrapper> addRelatedToNodes = relatedToNodes.getAddItems();
        final List<CSRelatedNodeWrapper> removeRelatedToNodes = relatedToNodes.getRemoveItems();
        /*
         * There is no need to do update the related to nodes as when the original entities are altered they will automatically be updated
         * when using database entities.
         */
        //final List<PropertyTagInTopicWrapper> updateRelatedToNodes = relatedToNodes.getUpdateItems();

        // Add Related Nodes
        for (final CSRelatedNodeWrapper addRelatedToNode : addRelatedToNodes) {
            getEntity().addRelatedTo((CSNodeToCSNode) addRelatedToNode.unwrap());
        }

        // Remove Related Nodes
        for (final CSRelatedNodeWrapper removeRelatedToNode : removeRelatedToNodes) {
            getEntity().removeRelatedTo((CSNodeToCSNode) removeRelatedToNode.unwrap());
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getRelatedFromNodes(),
                CSNodeToCSNode.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes) {
        if (relatedFromNodes == null) return;

        final List<CSRelatedNodeWrapper> addRelatedFromNodes = relatedFromNodes.getAddItems();
        final List<CSRelatedNodeWrapper> removeRelatedFromNodes = relatedFromNodes.getRemoveItems();
        /*
         * There is no need to do update the related from nodes as when the original entities are altered they will automatically be
         * updated when using database entities.
         */
        //final List<CSRelatedNodeWrapper> updateRelatedFromNodes = relatedFromNodes.getUpdateItems();

        // Add Related Nodes
        for (final CSRelatedNodeWrapper addRelatedFromNode : addRelatedFromNodes) {
            getEntity().addRelatedFrom((CSNodeToCSNode) addRelatedFromNode.unwrap());
        }

        // Remove Related Nodes
        for (final CSRelatedNodeWrapper removeRelatedFromNode : removeRelatedFromNodes) {
            getEntity().removeRelatedFrom((CSNodeToCSNode) removeRelatedFromNode.unwrap());
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
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes) {
        if (nodes == null) return;

        final List<CSNodeWrapper> addNodes = nodes.getAddItems();
        final List<CSNodeWrapper> removeNodes = nodes.getRemoveItems();
        /*
         * There is no need to do update the child nodes as when the original entities are altered they will automatically be
         * updated when using database entities.
         */
        //final List<CSNodeWrapper> updateNodes = relatedFromNodes.getUpdateItems();

        // Add Child Nodes
        for (final CSNodeWrapper addNode : addNodes) {
            getEntity().addChild((CSNode) addNode.unwrap());
        }

        // Remove Child Nodes
        for (final CSNodeWrapper removeNode : removeNodes) {
            getEntity().removeChild((CSNode) removeNode.unwrap());
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
}
