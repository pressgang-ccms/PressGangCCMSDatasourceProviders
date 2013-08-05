package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class RESTTranslatedContentSpecV1Wrapper extends RESTBaseWrapper<TranslatedContentSpecWrapper,
        RESTTranslatedContentSpecV1> implements TranslatedContentSpecWrapper {

    private final RESTTranslatedContentSpecV1 translatedContentSpec;

    protected RESTTranslatedContentSpecV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedContentSpecV1 translatedContentSpec,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.translatedContentSpec = RESTEntityProxyFactory.createProxy(providerFactory, translatedContentSpec, isRevision);
    }

    @Override
    protected RESTTranslatedContentSpecV1 getProxyEntity() {
        return translatedContentSpec;
    }

    @Override
    public TranslatedContentSpecWrapper clone(boolean deepCopy) {
        return new RESTTranslatedContentSpecV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTranslatedContentSpecV1.class, true);
    }

    @Override
    public Integer getContentSpecId() {
        return getProxyEntity().getContentSpecId();
    }

    @Override
    public void setContentSpecId(Integer id) {
        getEntity().explicitSetContentSpecId(id);
    }

    @Override
    public Integer getContentSpecRevision() {
        return getProxyEntity().getContentSpecRevision();
    }

    @Override
    public void setContentSpecRevision(Integer revision) {
        getEntity().explicitSetContentSpecRevision(revision);
    }

    @Override
    public String getZanataId() {
        return ComponentTranslatedContentSpecV1.returnZanataId(getProxyEntity());
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes() {
        final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(getProxyEntity()
                .getTranslatedNodes_OTM(), RESTTranslatedCSNodeV1.class, isRevisionEntity(), TranslatedCSNodeWrapper.class);
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
    }

    @Override
    public void setTranslatedNodes(UpdateableCollectionWrapper<TranslatedCSNodeWrapper> translatedNodes) {
        getEntity().explicitSetTranslatedNodes_OTM(
                translatedNodes == null ? null : (RESTTranslatedCSNodeCollectionV1) translatedNodes.unwrap());
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return getWrapperFactory().create(getProxyEntity().getContentSpec(), true, ContentSpecWrapper.class);
    }

    @Override
    public void setContentSpec(ContentSpecWrapper contentSpec) {
        getEntity().setContentSpec(contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap());
    }

    @Override
    public String getEditorURL(ZanataDetails zanataDetails, String locale) {
        return ComponentTranslatedContentSpecV1.returnEditorURL(getProxyEntity(), zanataDetails, locale);
    }
}
