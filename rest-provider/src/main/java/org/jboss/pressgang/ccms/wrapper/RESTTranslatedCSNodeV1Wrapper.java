package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTranslatedCSNodeV1Wrapper extends RESTBaseWrapper<TranslatedCSNodeWrapper,
        RESTTranslatedCSNodeV1> implements TranslatedCSNodeWrapper {

    protected RESTTranslatedCSNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeV1 entity,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTranslatedCSNodeV1.class, true);
    }

    @Override
    public TranslatedCSNodeWrapper clone(boolean deepCopy) {
        return new RESTTranslatedCSNodeV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
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
        getEntity().explicitSetNodeRevision(revision);
    }

    @Override
    public String getOriginalString() {
        return getProxyEntity().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getEntity().explicitSetOriginalString(originalString);
    }

    @Override
    public String getZanataId() {
        return "CS" + getNodeId() + "-" + getNodeRevision();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedStrings() {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getTranslatedNodeStrings_OTM(), RESTTranslatedCSNodeStringV1.class, isRevisionEntity(), getProxyEntity());
        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }

    @Override
    public void setTranslatedStrings(UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> translatedStrings) {
        getEntity().explicitSetTranslatedNodeString_OTM(
                translatedStrings == null ? null : (RESTTranslatedCSNodeStringCollectionV1) translatedStrings.unwrap());
    }

    @Override
    public CSNodeWrapper getCSNode() {
        return getWrapperFactory().create(getProxyEntity().getNode(), true);
    }

    @Override
    public void setCSNode(CSNodeWrapper node) {
        getEntity().setNode(node == null ? null : (RESTCSNodeV1) node.unwrap());
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec() {
        return getWrapperFactory().create(getProxyEntity().getTranslatedContentSpec(), isRevisionEntity(),
                TranslatedContentSpecWrapper.class);
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic() {
        return getWrapperFactory().create(getProxyEntity().getTranslatedTopic(), isRevisionEntity(), TranslatedTopicWrapper.class);
    }

    @Override
    public void setTranslatedTopic(TranslatedTopicWrapper translatedTopic) {
        getEntity().explicitSetTranslatedTopic(translatedTopic == null ? null : (RESTTranslatedTopicV1) translatedTopic.unwrap());
    }
}
