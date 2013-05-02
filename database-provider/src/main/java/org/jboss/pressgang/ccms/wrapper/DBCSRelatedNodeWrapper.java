package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;

public class DBCSRelatedNodeWrapper extends DBBaseWrapper<CSRelatedNodeWrapper, CSNodeToCSNode> implements CSRelatedNodeWrapper {
    private final CSNodeToCSNode csNodeToCSNode;

    public DBCSRelatedNodeWrapper(final DBProviderFactory providerFactory, final CSNodeToCSNode csNodeToCSNode, boolean isRevision) {
        super(providerFactory, isRevision, CSNodeToCSNode.class);
        this.csNodeToCSNode = csNodeToCSNode;
    }

    @Override
    protected CSNodeToCSNode getEntity() {
        return csNodeToCSNode;
    }

    protected CSNode getCSNode() {
        return getEntity().getRelatedNode();
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
    public CSNodeToCSNode unwrap() {
        return csNodeToCSNode;
    }

    @Override
    public Integer getRelationshipType() {
        return getEntity().getRelationshipType();
    }

    @Override
    public void setRelationshipType(Integer typeId) {
        getEntity().setRelationshipType(typeId);
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }

    @Override
    public void setRelationshipId(Integer id) {
        getEntity().setCSNodeToCSNodeId(id);
    }

    @Override
    public Integer getRelationshipSort() {
        return getEntity().getRelationshipSort();
    }

    @Override
    public void setRelationshipSort(Integer sort) {
        getEntity().setRelationshipSort(sort);
    }
}
