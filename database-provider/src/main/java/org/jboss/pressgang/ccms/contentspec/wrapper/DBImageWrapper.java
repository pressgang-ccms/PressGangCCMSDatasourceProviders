package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBImageWrapper extends DBBaseWrapper<ImageWrapper> implements ImageWrapper {

    private final ImageFile image;

    public DBImageWrapper(final DBProviderFactory providerFactory, final ImageFile image, boolean isRevision) {
        super(providerFactory, isRevision);
        this.image = image;
    }

    protected ImageFile getImage() {
        return image;
    }

    @Override
    public Integer getId() {
        return getImage().getId();
    }

    @Override
    public void setId(Integer id) {
        getImage().setImageFileId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getImage().getRevision();
    }

    @Override
    public CollectionWrapper<ImageWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getImage()),
                ImageFile.class, true);
    }

    @Override
    public ImageFile unwrap() {
        return image;
    }

    @Override
    public boolean isRevisionEntity() {
        return getImage().getRevision() != null;
    }

    @Override
    public String getDescription() {
        return getImage().getDescription();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImages() {
        return getWrapperFactory().createCollection(getImage().getLanguageImages(), LanguageImage.class, isRevisionEntity());
    }

}
