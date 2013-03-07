package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBImageWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBImageProvider extends DBDataProvider implements ImageProvider {
    protected DBImageProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public ImageWrapper getImage(int id) {
        final ImageFile image = getEntityManager().find(ImageFile.class, id);
        return getWrapperFactory().create(image, false);
    }

    @Override
    public ImageWrapper getImage(int id, Integer revision) {
        if (revision == null) {
            return getImage(id);
        } else {
            final ImageFile dummyImage = new ImageFile();
            dummyImage.setImageFileId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyImage, revision), true);
        }
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getImageLanguageImages(int id, Integer revision) {
        final DBImageWrapper image = (DBImageWrapper) getImage(id, revision);
        if (image == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(image.unwrap().getLanguageImages(), LanguageImage.class,
                    revision != null);
        }
    }

    @Override
    public CollectionWrapper<ImageWrapper> getImageRevisions(int id, Integer revision) {
        final ImageFile image = new ImageFile();
        image.setImageFileId(id);
        final Map<Number, ImageFile> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), image);

        final List<ImageFile> revisions = new ArrayList<ImageFile>();
        for (final Map.Entry<Number, ImageFile> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, ImageFile.class, revision != null);
    }
}
