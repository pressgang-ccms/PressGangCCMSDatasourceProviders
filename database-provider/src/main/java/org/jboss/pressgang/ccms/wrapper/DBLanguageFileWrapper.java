package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;

public class DBLanguageFileWrapper extends DBBaseWrapper<LanguageFileWrapper, LanguageFile> implements LanguageFileWrapper {

    private final LanguageFile languageFile;

    public DBLanguageFileWrapper(final DBProviderFactory providerFactory, final LanguageFile languageFile, boolean isRevision) {
        super(providerFactory, isRevision, LanguageFile.class);
        this.languageFile = languageFile;
    }

    @Override
    protected LanguageFile getEntity() {
        return languageFile;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setLanguageFileId(id);
    }

    @Override
    public LanguageFile unwrap() {
        return languageFile;
    }

    @Override
    public boolean isRevisionEntity() {
        return getEntity().getRevision() != null;
    }

    @Override
    public String getOriginalFilename() {
        return getEntity().getOriginalFileName();
    }

    @Override
    public void setOriginalFilename(final String filename) {
        getEntity().setOriginalFileName(filename);
    }

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public void setLocale(final String locale) {
        getEntity().setLocale(locale);
    }

    @Override
    public byte[] getFileData() {
        return getEntity().getFileData();
    }

    @Override
    public void setFileData(byte[] fileData) {
        getEntity().setFileData(fileData);
    }
}
