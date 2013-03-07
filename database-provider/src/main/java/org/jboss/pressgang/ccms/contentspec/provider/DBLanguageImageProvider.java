package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBLanguageImageProvider extends DBDataProvider implements LanguageImageProvider {

    protected DBLanguageImageProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public LanguageImageWrapper getLanguageImage(int id, Integer revision) {
        if (revision == null) {
            final LanguageImage languageImage = getEntityManager().find(LanguageImage.class, id);
            return getWrapperFactory().create(languageImage, false);
        } else {
            final LanguageImage dummyLanguageImage = new LanguageImage();
            dummyLanguageImage.setLanguageImageId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyLanguageImage, revision), true);
        }
    }

    @Override
    public byte[] getLanguageImageData(int id, Integer revision) {
        return getLanguageImage(id, revision).getImageData();
    }

    @Override
    public byte[] getLanguageImageDataBase64(int id, Integer revision) {
        return getLanguageImage(id, revision).getImageDataBase64();
    }

    @Override
    public byte[] getLanguageImageThumbnail(int id, Integer revision) {
        return getLanguageImage(id, revision).getThumbnail();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImageRevisions(int id, Integer revision) {
        final LanguageImage user = new LanguageImage();
        user.setLanguageImageId(id);
        final Map<Number, LanguageImage> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), user);

        final List<LanguageImage> revisions = new ArrayList<LanguageImage>();
        for (final Map.Entry<Number, LanguageImage> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, LanguageImage.class, revision != null);
    }
}
