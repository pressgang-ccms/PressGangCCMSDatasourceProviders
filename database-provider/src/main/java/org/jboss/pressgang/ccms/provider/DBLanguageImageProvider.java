package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBLanguageImageProvider extends DBDataProvider implements LanguageImageProvider {

    protected DBLanguageImageProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
    }

    @Override
    public LanguageImageWrapper getLanguageImage(int id, Integer revision) {
        if (revision == null) {
            return getWrapperFactory().create(getEntity(LanguageImage.class, id), false);
        } else {
            return getWrapperFactory().create(getRevisionEntity(LanguageImage.class, id, revision), true);
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
        final List<LanguageImage> revisions = getRevisionList(LanguageImage.class, id);
        return getWrapperFactory().createCollection(revisions, LanguageImage.class, revision != null);
    }
}
