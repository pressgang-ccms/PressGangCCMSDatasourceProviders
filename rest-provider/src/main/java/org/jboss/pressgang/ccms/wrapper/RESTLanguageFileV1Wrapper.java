package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTLanguageFileV1Wrapper extends RESTBaseEntityWrapper<LanguageFileWrapper, RESTLanguageFileV1> implements LanguageFileWrapper {

    protected RESTLanguageFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileV1 file, boolean isRevision,
            final RESTFileV1 parent, boolean isNewEntity) {
        super(providerFactory, file, isRevision, parent, isNewEntity);
    }

    protected RESTLanguageFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileV1 file, boolean isRevision,
            final RESTFileV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, file, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTFileV1 getParentEntity() {
        return (RESTFileV1) super.getParentEntity();
    }

    @Override
    public LanguageFileWrapper clone(boolean deepCopy) {
        return new RESTLanguageFileV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                (deepCopy ? getParentEntity().clone(deepCopy) : getParentEntity()), isNewEntity());
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
}
