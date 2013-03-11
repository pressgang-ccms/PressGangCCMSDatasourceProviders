package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeRelationshipTypeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeTypeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTCSRelatedNodeV1Wrapper extends RESTBaseWrapper<CSRelatedNodeWrapper, RESTCSRelatedNodeV1> implements CSRelatedNodeWrapper {
    private final RESTCSRelatedNodeV1 node;

    protected RESTCSRelatedNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeV1 entity, boolean isRevision) {
        super(providerFactory, isRevision);
        node = RESTEntityProxyFactory.createProxy(providerFactory, entity, isRevision);
    }

    @Override
    protected RESTCSRelatedNodeV1 getProxyEntity() {
        return node;
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTCSRelatedNodeV1.class, true);
    }

    @Override
    public CSRelatedNodeWrapper clone(boolean deepCopy) {
        return getWrapperFactory().create(getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setTitle(title);
    }

    @Override
    public String getTargetId() {
        return getProxyEntity().getAdditionalText();
    }

    @Override
    public void setTargetId(String targetId) {
        getEntity().setAdditionalText(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getProxyEntity().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getEntity().setAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getProxyEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(getCondition());
    }

    @Override
    public Integer getEntityId() {
        return getProxyEntity().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getEntity().setEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getProxyEntity().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getEntity().setEntityRevision(revision);
    }

    @Override
    public Integer getNextNodeId() {
        return getProxyEntity().getNextNodeId();
    }

    @Override
    public void setNextNodeId(Integer id) {
        getEntity().setNextNodeId(id);
    }

    @Override
    public Integer getPreviousNodeId() {
        return getProxyEntity().getPreviousNodeId();
    }

    @Override
    public void setPreviousNodeId(Integer id) {
        getEntity().setPreviousNodeId(id);
    }

    @Override
    public Integer getRelationshipType() {
        return RESTCSNodeRelationshipTypeV1.getRelationshipTypeId(getProxyEntity().getRelationshipType());
    }

    @Override
    public void setRelationshipType(Integer typeId) {
        getEntity().explicitSetRelationshipType(RESTCSNodeRelationshipTypeV1.getRelationshipType(typeId));
    }

    @Override
    public Integer getRelationshipId() {
        return getProxyEntity().getRelationshipId();
    }

    @Override
    public void setRelationshipId(Integer id) {
        getEntity().setRelationshipId(id);
    }

    @Override
    public Integer getRelationshipSort() {
        return getProxyEntity().getRelationshipSort();
    }

    @Override
    public void setRelationshipSort(Integer sort) {
        getEntity().setRelationshipSort(sort);
    }

    @Override
    public Integer getNodeType() {
        return RESTCSNodeTypeV1.getNodeTypeId(getProxyEntity().getNodeType());
    }

    @Override
    public void setNodeType(Integer typeId) {
        getEntity().setNodeType(RESTCSNodeTypeV1.getNodeType(typeId));
    }
}