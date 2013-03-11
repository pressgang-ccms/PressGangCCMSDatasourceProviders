package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeTypeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;

public class RESTCSNodeV1Wrapper extends RESTBaseWrapper<CSNodeWrapper, RESTCSNodeV1> implements CSNodeWrapper {
    private final RESTCSNodeV1 node;

    protected RESTCSNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSNodeV1 entity, boolean isRevision) {
        super(providerFactory, isRevision);
        node = RESTEntityProxyFactory.createProxy(providerFactory, entity, isRevision);
    }

    @Override
    protected RESTCSNodeV1 getProxyEntity() {
        return node;
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTCSNodeV1.class, true);
    }

    @Override
    public CSNodeWrapper clone(boolean deepCopy) {
        return getWrapperFactory().create(getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getChildren() {
        return getWrapperFactory().createCollection(getProxyEntity().getChildren_OTM(), RESTCSNodeV1.class, isRevisionEntity());
    }

    @Override
    public void setChildren(CollectionWrapper<CSNodeWrapper> nodes) {
        getEntity().explicitSetChildren_OTM(nodes == null ? null : (RESTCSNodeCollectionV1) nodes.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getRelatedToNodes(), RESTCSRelatedNodeV1.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes) {
        getEntity().explicitSetRelatedToNodes(relatedToNodes == null ? null : (RESTCSRelatedNodeCollectionV1) relatedToNodes.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getRelatedFromNodes(), RESTCSRelatedNodeV1.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes) {
        getEntity().explicitSetRelatedFromNodes(
                relatedFromNodes == null ? null : (RESTCSRelatedNodeCollectionV1) relatedFromNodes.unwrap());
    }

    @Override
    public CSNodeWrapper getParent() {
        return getWrapperFactory().create(getProxyEntity().getParent(), isRevisionEntity());
    }

    @Override
    public void setParent(CSNodeWrapper parent) {
        getEntity().explicitSetParent(parent == null ? null : (RESTCSNodeV1) parent.unwrap());
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return getWrapperFactory().create(getProxyEntity().getContentSpec(), isRevisionEntity());
    }

    @Override
    public void setContentSpec(ContentSpecWrapper contentSpec) {
        getEntity().explicitSetContentSpec(contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap());
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().explicitSetTitle(title);
    }

    @Override
    public String getTargetId() {
        return getProxyEntity().getAdditionalText();
    }

    @Override
    public void setTargetId(String targetId) {
        getEntity().explicitSetAdditionalText(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getProxyEntity().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getEntity().explicitSetAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getProxyEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().explicitSetCondition(getCondition());
    }

    @Override
    public Integer getEntityId() {
        return getProxyEntity().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getEntity().explicitSetEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getProxyEntity().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getEntity().explicitSetEntityRevision(revision);
    }

    @Override
    public Integer getNextNodeId() {
        return getProxyEntity().getNextNodeId();
    }

    @Override
    public void setNextNodeId(Integer id) {
        getEntity().explicitSetNextNodeId(id);
    }

    @Override
    public Integer getPreviousNodeId() {
        return getProxyEntity().getPreviousNodeId();
    }

    @Override
    public void setPreviousNodeId(Integer id) {
        getEntity().explicitSetPreviousNodeId(id);
    }

    @Override
    public Integer getNodeType() {
        return RESTCSNodeTypeV1.getNodeTypeId(getProxyEntity().getNodeType());
    }

    @Override
    public void setNodeType(Integer typeId) {
        getEntity().explicitSetNodeType(RESTCSNodeTypeV1.getNodeType(typeId));
    }
}