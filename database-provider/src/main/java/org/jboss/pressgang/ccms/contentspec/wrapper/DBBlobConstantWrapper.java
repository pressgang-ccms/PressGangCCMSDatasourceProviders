package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBBlobConstantWrapper extends DBBaseWrapper<BlobConstantWrapper> implements BlobConstantWrapper {

    private final BlobConstants blobConstant;

    public DBBlobConstantWrapper(final DBProviderFactory providerFactory, final BlobConstants blobConstant, boolean isRevision) {
        super(providerFactory, isRevision);
        this.blobConstant = blobConstant;
    }

    protected BlobConstants getBlobConstant() {
        return blobConstant;
    }

    @Override
    public Integer getId() {
        return getBlobConstant().getBlobConstantsId();
    }

    @Override
    public void setId(Integer id) {
        getBlobConstant().setBlobConstantsId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getBlobConstant().getRevision();
    }

    @Override
    public CollectionWrapper<BlobConstantWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getBlobConstant()),
                BlobConstants.class, true);
    }

    @Override
    public BlobConstants unwrap() {
        return blobConstant;
    }

    @Override
    public boolean isRevisionEntity() {
        return getBlobConstant().getRevision() != null;
    }

    @Override
    public String getName() {
        return getBlobConstant().getConstantName();
    }

    @Override
    public void setName(String name) {
        getBlobConstant().setConstantName(name);
    }

    @Override
    public byte[] getValue() {
        return getBlobConstant().getConstantValue();
    }

    @Override
    public void setValue(byte[] value) {
        getBlobConstant().setConstantValue(value);
    }
}
