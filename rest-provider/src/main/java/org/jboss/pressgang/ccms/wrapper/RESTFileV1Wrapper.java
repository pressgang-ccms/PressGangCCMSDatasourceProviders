package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTFileV1Wrapper extends RESTBaseWrapper<FileWrapper, RESTFileV1> implements FileWrapper {
    private RESTFileV1 file;

    protected RESTFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTFileV1 file, boolean isRevision) {
        super(providerFactory, isRevision);
        this.file = RESTEntityProxyFactory.createProxy(providerFactory, file, isRevision);
    }

    @Override
    protected RESTFileV1 getProxyEntity() {
        return file;
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
    public RESTFileV1Wrapper clone(boolean deepCopy) {
        return new RESTFileV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public String getDescription() {
        return getProxyEntity().getDescription();
    }

    @Override
    public void setDescription(final String description) {
        getEntity().explicitSetDescription(description);
    }

    @Override
    public String getFilePath() {
        return getProxyEntity().getFilePath();
    }

    @Override
    public void setFilePath(final String filePath) {
        getEntity().explicitSetFilePath(filePath);
    }

    @Override
    public String getFilename() {
        return getProxyEntity().getFileName();
    }

    @Override
    public void setFilename(final String filename) {
        getEntity().explicitSetFileName(filename);
    }

    @Override
    public boolean isExplodeArchive() {
        return getProxyEntity().getExplodeArchive();
    }

    @Override
    public void setExplodeArchive(boolean explodeArchive) {
        getEntity().explicitSetExplodeArchive(explodeArchive);
    }

    @Override
    public UpdateableCollectionWrapper<LanguageFileWrapper> getLanguageFiles() {
        final CollectionWrapper<LanguageFileWrapper> collection = getWrapperFactory().createCollection(
                getProxyEntity().getLanguageFiles_OTM(), RESTLanguageFileV1.class, isRevisionEntity(), getProxyEntity());
        return (UpdateableCollectionWrapper<LanguageFileWrapper>) collection;
    }

    @Override
    public void setLanguageFiles(UpdateableCollectionWrapper<LanguageFileWrapper> languageFiles) {
        getEntity().explicitSetLanguageFiles_OTM(languageFiles == null ? null : (RESTLanguageFileCollectionV1) languageFiles.unwrap());
    }

    @Override
    public CollectionWrapper<FileWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTFileV1.class, true);
    }
}
