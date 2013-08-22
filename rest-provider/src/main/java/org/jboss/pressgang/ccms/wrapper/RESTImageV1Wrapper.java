package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTImageV1Wrapper extends RESTBaseWrapper<ImageWrapper, RESTImageV1> implements ImageWrapper {

    protected RESTImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageV1 image, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, image, isRevision, isNewEntity);
    }

    @Override
    public RESTImageV1Wrapper clone(boolean deepCopy) {
        return new RESTImageV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getDescription() {
        return getProxyEntity().getDescription();
    }

    @Override
    public UpdateableCollectionWrapper<LanguageImageWrapper> getLanguageImages() {
        final CollectionWrapper<LanguageImageWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getLanguageImages_OTM(), RESTLanguageImageV1.class, isRevisionEntity(), getProxyEntity());
        return (UpdateableCollectionWrapper<LanguageImageWrapper>) collection;
    }

    @Override
    public void setLanguageImages(UpdateableCollectionWrapper<LanguageImageWrapper> languageImages) {
        getEntity().explicitSetLanguageImages_OTM(languageImages == null ? null : (RESTLanguageImageCollectionV1) languageImages.unwrap());
    }

    @Override
    public CollectionWrapper<ImageWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTImageV1.class, true);
    }
}
