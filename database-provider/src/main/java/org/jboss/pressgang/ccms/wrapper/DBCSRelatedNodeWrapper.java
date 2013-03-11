package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.CSNodeProvider;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBCSRelatedNodeWrapper extends DBBaseWrapper<CSRelatedNodeWrapper> implements CSRelatedNodeWrapper {
    private final CSNodeToCSNode csNodeToCSNode;

    public DBCSRelatedNodeWrapper(final DBProviderFactory providerFactory, final CSNodeToCSNode csNodeToCSNode, boolean isRevision) {
        super(providerFactory, isRevision);
        this.csNodeToCSNode = csNodeToCSNode;
    }

    protected CSNodeToCSNode getCSNodeToCSNode() {
        return csNodeToCSNode;
    }

    protected CSNode getCSNode() {
        return getCSNodeToCSNode().getRelatedNode();
    }

    @Override
    public String getTitle() {
        return getCSNode().getCSNodeTitle();
    }

    @Override
    public void setTitle(String title) {
        getCSNode().setCSNodeTitle(title);
    }

    @Override
    public String getTargetId() {
        return getCSNode().getAdditionalText();
    }

    @Override
    public void setTargetId(String targetId) {
        getCSNode().setAdditionalText(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getCSNode().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getCSNode().setAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getCSNode().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getCSNode().setCondition(condition);
    }

    @Override
    public Integer getEntityId() {
        return getCSNode().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getCSNode().setEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getCSNode().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getCSNode().setEntityRevision(revision);
    }

    @Override
    public Integer getNextNodeId() {
        return getCSNode().getNext() == null ? null : getCSNode().getNext().getCSNodeId();
    }

    @Override
    public void setNextNodeId(Integer id) {
        final CSNodeWrapper node = getDatabaseProvider().getProvider(CSNodeProvider.class).getCSNode(id);
        getCSNode().setNext(node == null ? null : (CSNode) node.unwrap());
    }

    @Override
    public Integer getPreviousNodeId() {
        return getCSNode().getPrevious() == null ? null : getCSNode().getPrevious().getCSNodeId();
    }

    @Override
    public void setPreviousNodeId(Integer id) {
        final CSNodeWrapper node = getDatabaseProvider().getProvider(CSNodeProvider.class).getCSNode(id);
        getCSNode().setPrevious(node == null ? null : (CSNode) node.unwrap());
    }

    @Override
    public Integer getNodeType() {
        return getCSNode().getCSNodeType();
    }

    @Override
    public void setNodeType(Integer typeId) {
        getCSNode().setCSNodeType(typeId);
    }

    @Override
    public Integer getId() {
        return getCSNode().getId();
    }

    @Override
    public void setId(Integer id) {
        getCSNode().setCSNodeId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getCSNode().getRevision();
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getCSNodeToCSNode()),
                CSNodeToCSNode.class, true);
    }

    @Override
    public CSNodeToCSNode unwrap() {
        return csNodeToCSNode;
    }

    @Override
    public Integer getRelationshipType() {
        return getCSNodeToCSNode().getRelationshipType();
    }

    @Override
    public void setRelationshipType(Integer typeId) {
        getCSNodeToCSNode().setRelationshipType(typeId);
    }

    @Override
    public Integer getRelationshipId() {
        return getCSNodeToCSNode().getId();
    }

    @Override
    public void setRelationshipId(Integer id) {
        getCSNodeToCSNode().setCSNodeToCSNodeId(id);
    }

    @Override
    public Integer getRelationshipSort() {
        return getCSNodeToCSNode().getRelationshipSort();
    }

    @Override
    public void setRelationshipSort(Integer sort) {
        getCSNodeToCSNode().setRelationshipSort(sort);
    }
}
