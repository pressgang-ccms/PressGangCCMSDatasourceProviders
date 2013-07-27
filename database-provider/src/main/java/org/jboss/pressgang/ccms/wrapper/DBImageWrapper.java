package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageImageCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBImageWrapper extends DBBaseWrapper<ImageWrapper, ImageFile> implements ImageWrapper {
    private final LanguageImageCollectionEventListener languageImageCollectionEventListener = new LanguageImageCollectionEventListener();

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
    public UpdateableCollectionWrapper<LanguageImageWrapper> getLanguageImages() {
        final CollectionWrapper<LanguageImageWrapper> collection = getWrapperFactory().createCollection(getEntity().getLanguageImages(),
                LanguageImage.class, isRevisionEntity());
        final DBLanguageImageCollectionWrapper dbCollection = (DBLanguageImageCollectionWrapper) collection;
        dbCollection.registerEventListener(languageImageCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setLanguageImages(UpdateableCollectionWrapper<LanguageImageWrapper> languageImages) {
        if (languageImages == null) return;
        final DBLanguageImageCollectionWrapper dbLanguageImages = (DBLanguageImageCollectionWrapper) languageImages;
        dbLanguageImages.registerEventListener(languageImageCollectionEventListener);

        // Remove the current children
        final Set<LanguageImage> currentLanguageImages = new HashSet<LanguageImage>(getEntity().getLanguageImages());
        for (final LanguageImage languageImage : currentLanguageImages) {
            getEntity().removeLanguageImage(languageImage);
        }

        // Set the new children
        final Collection<LanguageImage> newLanguageImages = dbLanguageImages.unwrap();
        for (final LanguageImage languageImage : newLanguageImages) {
            getEntity().addLanguageImage(languageImage);
        }
    }

    /**
     *
     */
    private class LanguageImageCollectionEventListener implements UpdateableCollectionEventListener<LanguageImage> {
        @Override
        public void onAddItem(final LanguageImage entity) {
            getEntity().addLanguageImage(entity);
        }

        @Override
        public void onRemoveItem(final LanguageImage entity) {
            getEntity().removeLanguageImage(entity);
        }

        @Override
        public void onUpdateItem(final LanguageImage entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof LanguageImageCollectionEventListener;
        }
    }
}
