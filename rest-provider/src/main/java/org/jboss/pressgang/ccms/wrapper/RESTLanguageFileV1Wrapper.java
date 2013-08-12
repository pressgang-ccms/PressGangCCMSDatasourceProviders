package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTLanguageFileV1Wrapper extends RESTBaseWrapper<LanguageFileWrapper, RESTLanguageFileV1> implements LanguageFileWrapper {
    private RESTLanguageFileV1 file;
    private final RESTFileV1 parent;

    protected RESTLanguageFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileV1 file, boolean isRevision,
            final RESTFileV1 parent) {
        super(providerFactory, isRevision);
        this.parent = parent;
        this.file = RESTEntityProxyFactory.createProxy(providerFactory, file, isRevision, parent);
    }

    @Override
    protected RESTLanguageFileV1 getProxyEntity() {
        return file;
    }

    @Override
    public LanguageFileWrapper clone(boolean deepCopy) {
        return new RESTLanguageFileV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                (deepCopy ? parent.clone(deepCopy) : parent));
    }

    @Override
    public String getOriginalFilename() {
        return getProxyEntity().getFilename();
    }

    @Override
    public void setOriginalFilename(final String filename) {
        getEntity().explicitSetFilename(filename);

    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public void setLocale(final String locale) {
        getEntity().explicitSetLocale(locale);
    }

    @Override
    public byte[] getFileData() {
        return getProxyEntity().getFileData();
    }

    @Override
    public void setFileData(byte[] fileData) {
        getEntity().explicitSetFileData(fileData);
    }

    @Override
    public CollectionWrapper<LanguageFileWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTLanguageFileV1.class, true, parent);
    }
}
