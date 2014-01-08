package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTImageV1Wrapper extends RESTBaseEntityWrapper<ImageWrapper, RESTImageV1> implements ImageWrapper {

    protected RESTImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageV1 image, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, image, isRevision, isNewEntity);
    }

    protected RESTImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageV1 image, boolean isRevision,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, image, isRevision, isNewEntity, expandedMethods);
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
        return (UpdateableCollectionWrapper<LanguageImageWrapper>) RESTCollectionWrapperBuilder.<LanguageImageWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getLanguageImages_OTM())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public void setLanguageImages(UpdateableCollectionWrapper<LanguageImageWrapper> languageImages) {
        getEntity().explicitSetLanguageImages_OTM(languageImages == null ? null : (RESTLanguageImageCollectionV1) languageImages.unwrap());
    }
}
