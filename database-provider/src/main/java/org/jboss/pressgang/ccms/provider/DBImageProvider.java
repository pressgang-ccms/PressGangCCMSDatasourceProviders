package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBImageWrapper;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBImageProvider extends DBDataProvider implements ImageProvider {
    protected DBImageProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public ImageWrapper getImage(int id) {
        return getWrapperFactory().create(getEntity(ImageFile.class, id), false);
    }

    @Override
    public ImageWrapper getImage(int id, Integer revision) {
        if (revision == null) {
            return getImage(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(ImageFile.class, id, revision), true);
        }
    }

    public CollectionWrapper<LanguageImageWrapper> getImageLanguageImages(int id, Integer revision) {
        final DBImageWrapper image = (DBImageWrapper) getImage(id, revision);
        if (image == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(image.unwrap().getLanguageImages(), LanguageImage.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<ImageWrapper> getImageRevisions(int id, Integer revision) {
        final List<ImageFile> revisions = getRevisionList(ImageFile.class, id);
        return getWrapperFactory().createCollection(revisions, ImageFile.class, revision != null);
    }
}
