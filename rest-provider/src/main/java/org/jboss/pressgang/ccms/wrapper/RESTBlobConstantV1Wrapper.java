package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTBlobConstantV1Wrapper extends RESTBaseEntityWrapper<BlobConstantWrapper, RESTBlobConstantV1> implements BlobConstantWrapper {

    protected RESTBlobConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTBlobConstantV1 blobConstant,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, blobConstant, isRevision, isNewEntity);
    }

    protected RESTBlobConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTBlobConstantV1 blobConstant,
            boolean isRevision, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, blobConstant, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public BlobConstantWrapper clone(boolean deepCopy) {
        return new RESTBlobConstantV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
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
}
