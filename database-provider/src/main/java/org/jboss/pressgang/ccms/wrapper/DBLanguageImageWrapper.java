package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;

public class DBLanguageImageWrapper extends DBBaseWrapper<LanguageImageWrapper, LanguageImage> implements LanguageImageWrapper {

    private final LanguageImage languageImage;

    public DBLanguageImageWrapper(final DBProviderFactory providerFactory, final LanguageImage languageImage, boolean isRevision) {
        super(providerFactory, isRevision, LanguageImage.class);
        this.languageImage = languageImage;
    }

    @Override
    protected LanguageImage getEntity() {
        return languageImage;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setLanguageImageId(id);
    }

    @Override
    public LanguageImage unwrap() {
        return languageImage;
    }

    @Override
    public boolean isRevisionEntity() {
        return getEntity().getRevision() != null;
    }

    @Override
    public String getFilename() {
        return getEntity().getOriginalFileName();
    }

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public byte[] getImageData() {
        return getEntity().getImageData();
    }

    @Override
    public byte[] getImageDataBase64() {
        return getEntity().getImageDataBase64();
    }

    @Override
    public byte[] getThumbnail() {
        return getEntity().getThumbnailData();
    }

}
