package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTImageV1Wrapper extends RESTBaseWrapper<ImageWrapper, RESTImageV1> implements ImageWrapper {
    private RESTImageV1 image;

    protected RESTImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageV1 image, boolean isRevision) {
        super(providerFactory, isRevision);
        this.image = RESTEntityProxyFactory.createProxy(providerFactory, image, isRevision);
    }

    @Override
    protected RESTImageV1 getProxyEntity() {
        return image;
    }

    @Override
    public Integer getId() {
        return getProxyEntity().getId();
    }

    @Override
    public Integer getRevision() {
        return getProxyEntity().getRevision();
    }

    @Override
    public RESTImageV1Wrapper clone(boolean deepCopy) {
        return new RESTImageV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public String getDescription() {
        return getProxyEntity().getDescription();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImages() {
        return getWrapperFactory().createCollection(getProxyEntity().getLanguageImages_OTM(), RESTLanguageImageV1.class, isRevisionEntity(),
                getProxyEntity());
    }

    @Override
    public CollectionWrapper<ImageWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTImageV1.class, true);
    }
}
