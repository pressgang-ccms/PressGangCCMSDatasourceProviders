package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBBlobConstantProvider extends DBDataProvider implements BlobConstantProvider {
    protected DBBlobConstantProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id) {
        final BlobConstants blobConstant = getEntityManager().find(BlobConstants.class, id);
        return getWrapperFactory().create(blobConstant, false);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id, Integer revision) {
        if (revision == null) {
            return getBlobConstant(id);
        } else {
            final BlobConstants dummyBlobConstant = new BlobConstants();
            dummyBlobConstant.setBlobConstantsId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyBlobConstant, revision),
                    true);
        }
    }

    @Override
    public CollectionWrapper<BlobConstantWrapper> getBlobConstantRevisions(int id, Integer revision) {
        final BlobConstants tag = new BlobConstants();
        tag.setBlobConstantsId(id);
        final Map<Number, BlobConstants> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), tag);

        final List<BlobConstants> revisions = new ArrayList<BlobConstants>();
        for (final Map.Entry<Number, BlobConstants> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, BlobConstants.class, revision != null);
    }
}
