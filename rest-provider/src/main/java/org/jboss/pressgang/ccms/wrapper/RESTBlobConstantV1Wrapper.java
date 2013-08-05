package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTBlobConstantV1Wrapper extends RESTBaseWrapper<BlobConstantWrapper, RESTBlobConstantV1> implements BlobConstantWrapper {

    private final RESTBlobConstantV1 blobConstant;

    protected RESTBlobConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTBlobConstantV1 blobConstant,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.blobConstant = RESTEntityProxyFactory.createProxy(providerFactory, blobConstant, isRevision);
    }

    @Override
    protected RESTBlobConstantV1 getProxyEntity() {
        return blobConstant;
    }

    @Override
    public BlobConstantWrapper clone(boolean deepCopy) {
        return new RESTBlobConstantV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }

    @Override
    public void setName(String name) {
        getEntity().explicitSetName(name);
    }

    @Override
    public byte[] getValue() {
        return getProxyEntity().getValue();
    }

    @Override
    public void setValue(byte[] value) {
        getEntity().explicitSetValue(value);
    }

    @Override
    public CollectionWrapper<BlobConstantWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTBlobConstantV1.class, true);
    }
}
