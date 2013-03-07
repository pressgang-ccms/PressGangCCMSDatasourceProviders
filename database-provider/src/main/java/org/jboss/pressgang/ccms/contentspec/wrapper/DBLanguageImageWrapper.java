package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBLanguageImageWrapper extends DBBaseWrapper<LanguageImageWrapper> implements LanguageImageWrapper {

    private final LanguageImage languageImage;

    public DBLanguageImageWrapper(final DBProviderFactory providerFactory, final LanguageImage languageImage, boolean isRevision) {
        super(providerFactory, isRevision);
        this.languageImage = languageImage;
    }

    protected LanguageImage getLanguageImage() {
        return languageImage;
    }

    @Override
    public Integer getId() {
        return getLanguageImage().getId();
    }

    @Override
    public void setId(Integer id) {
        getLanguageImage().setLanguageImageId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getLanguageImage().getRevision();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getLanguageImage()),
                LanguageImage.class, true);
    }

    @Override
    public LanguageImage unwrap() {
        return languageImage;
    }

    @Override
    public boolean isRevisionEntity() {
        return getLanguageImage().getRevision() != null;
    }

    @Override
    public String getFilename() {
        return getLanguageImage().getOriginalFileName();
    }

    @Override
    public String getLocale() {
        return getLanguageImage().getLocale();
    }

    @Override
    public byte[] getImageData() {
        return getLanguageImage().getImageData();
    }

    @Override
    public byte[] getImageDataBase64() {
        return getLanguageImage().getImageDataBase64();
    }

    @Override
    public byte[] getThumbnail() {
        return getLanguageImage().getThumbnailData();
    }

}
