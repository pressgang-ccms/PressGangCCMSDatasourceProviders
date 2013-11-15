package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBBlobConstantWrapper extends DBBaseEntityWrapper<BlobConstantWrapper, BlobConstants> implements BlobConstantWrapper {

    private final BlobConstants blobConstant;

    public DBBlobConstantWrapper(final DBProviderFactory providerFactory, final BlobConstants blobConstant, boolean isRevision) {
        super(providerFactory, isRevision, BlobConstants.class);
        this.blobConstant = blobConstant;
    }

    @Override
    protected BlobConstants getEntity() {
        return blobConstant;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setBlobConstantsId(id);
    }

    @Override
    public String getName() {
        return getEntity().getConstantName();
    }

    @Override
    public void setName(String name) {
        getEntity().setConstantName(name);
    }

    @Override
    public byte[] getValue() {
        return getEntity().getConstantValue();
    }

    @Override
    public void setValue(byte[] value) {
        getEntity().setConstantValue(value);
    }
}
