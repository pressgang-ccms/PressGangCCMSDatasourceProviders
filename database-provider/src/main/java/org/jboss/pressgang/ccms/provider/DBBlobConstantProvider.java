package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBBlobConstantProvider extends DBDataProvider implements BlobConstantProvider {
    protected DBBlobConstantProvider(EntityManager entityManager, DBProviderFactory providerFactory, final List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id) {
        return getWrapperFactory().create(getEntity(BlobConstants.class, id), false);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id, Integer revision) {
        if (revision == null) {
            return getBlobConstant(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(BlobConstants.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<BlobConstantWrapper> getBlobConstantRevisions(int id, Integer revision) {
        final List<BlobConstants> revisions = getRevisionList(BlobConstants.class, id);
        return getWrapperFactory().createCollection(revisions, BlobConstants.class, revision != null);
    }
}
