package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSTranslatedNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTCSTranslatedNodeV1Wrapper extends RESTBaseWrapper<CSTranslatedNodeWrapper,
        RESTCSTranslatedNodeV1> implements CSTranslatedNodeWrapper {
    private final RESTCSTranslatedNodeV1 node;

    protected RESTCSTranslatedNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSTranslatedNodeV1 entity,
            boolean isRevision) {
        super(providerFactory, isRevision);
        node = RESTEntityProxyFactory.createProxy(providerFactory, entity, isRevision);
    }

    @Override
    protected RESTCSTranslatedNodeV1 getProxyEntity() {
        return node;
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTCSTranslatedNodeV1.class, true);
    }

    @Override
    public CSTranslatedNodeWrapper clone(boolean deepCopy) {
        return getWrapperFactory().create(getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public Integer getNodeId() {
        return getProxyEntity().getNodeId();
    }

    @Override
    public void setNodeId(Integer id) {
        getEntity().explicitSetNodeId(id);
    }

    @Override
    public Integer getNodeRevision() {
        return getProxyEntity().getNodeRevision();
    }

    @Override
    public void setNodeRevision(Integer revision) {
        getEntity().setNodeRevision(revision);
    }

    @Override
    public String getZanataId() {
        return "CS" + getNodeId() + "-" + getNodeRevision();
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> getTranslatedStrings() {
        final CollectionWrapper<CSTranslatedNodeStringWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getTranslatedNodeStrings_OTM(), RESTCSTranslatedNodeStringV1.class, isRevisionEntity(), getProxyEntity());
        return (UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper>) collection;
    }

    @Override
    public void setTranslatedStrings(CollectionWrapper<CSTranslatedNodeStringWrapper> translatedStrings) {
        getEntity().explicitSetTranslatedNodeString_OTM(
                translatedStrings == null ? null : (RESTCSTranslatedNodeStringCollectionV1) translatedStrings.unwrap());
    }

    @Override
    public CSNodeWrapper getCSNode() {
        return getWrapperFactory().create(getProxyEntity().getNode(), true);
    }

    @Override
    public void setCSNode(CSNodeWrapper node) {
        getProxyEntity().setNode(node == null ? null : (RESTCSNodeV1) node.unwrap());
    }
}
