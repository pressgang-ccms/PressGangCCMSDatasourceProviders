package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBLanguageFileProvider extends DBDataProvider implements LanguageFileProvider {

    protected DBLanguageFileProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
    }

    @Override
    public LanguageFileWrapper getLanguageFile(int id, Integer revision) {
        if (revision == null) {
            return getWrapperFactory().create(getEntity(LanguageFile.class, id), false);
        } else {
            return getWrapperFactory().create(getRevisionEntity(LanguageFile.class, id, revision), true);
        }
    }

    @Override
    public byte[] getLanguageFileData(int id, Integer revision) {
        return getLanguageFile(id, revision).getFileData();
    }

    @Override
    public CollectionWrapper<LanguageFileWrapper> getLanguageFileRevisions(int id, Integer revision) {
        final List<LanguageFile> revisions = getRevisionList(LanguageFile.class, id);
        return getWrapperFactory().createCollection(revisions, LanguageFile.class, revision != null);
    }
}
