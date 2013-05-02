package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBImageWrapper extends DBBaseWrapper<ImageWrapper, ImageFile> implements ImageWrapper {

    private final ImageFile image;

    public DBImageWrapper(final DBProviderFactory providerFactory, final ImageFile image, boolean isRevision) {
        super(providerFactory, isRevision, ImageFile.class);
        this.image = image;
    }

    @Override
    protected ImageFile getEntity() {
        return image;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setImageFileId(id);
    }

    @Override
    public CollectionWrapper<ImageWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getEntity()), ImageFile.class,
                true);
    }

    @Override
    public ImageFile unwrap() {
        return image;
    }

    @Override
    public boolean isRevisionEntity() {
        return getEntity().getRevision() != null;
    }

    @Override
    public String getDescription() {
        return getEntity().getDescription();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImages() {
        return getWrapperFactory().createCollection(getEntity().getLanguageImages(), LanguageImage.class, isRevisionEntity());
    }

}
