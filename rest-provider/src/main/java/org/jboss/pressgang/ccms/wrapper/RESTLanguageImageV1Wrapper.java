package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTLanguageImageV1Wrapper extends RESTBaseWrapper<LanguageImageWrapper, RESTLanguageImageV1> implements LanguageImageWrapper {

    private RESTLanguageImageV1 image;
    private final RESTImageV1 parent;

    protected RESTLanguageImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageImageV1 image, boolean isRevision,
            final RESTImageV1 parent) {
        super(providerFactory, isRevision);
        this.parent = parent;
        this.image = RESTEntityProxyFactory.createProxy(providerFactory, image, isRevision, parent);
    }

    @Override
    protected RESTLanguageImageV1 getProxyEntity() {
        return image;
    }

    @Override
    public LanguageImageWrapper clone(boolean deepCopy) {
        return new RESTLanguageImageV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                (deepCopy ? parent.clone(deepCopy) : parent));
    }

    @Override
    public String getFilename() {
        return getProxyEntity().getFilename();
    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public byte[] getImageData() {
        return getProxyEntity().getImageData();
    }

    @Override
    public byte[] getImageDataBase64() {
        return getProxyEntity().getImageDataBase64();
    }

    @Override
    public byte[] getThumbnail() {
        return getProxyEntity().getThumbnail();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTLanguageImageV1.class, true, parent);
    }
}
