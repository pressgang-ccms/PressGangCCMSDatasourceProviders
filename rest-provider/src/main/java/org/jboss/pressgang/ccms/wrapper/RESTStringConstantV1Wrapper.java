package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTStringConstantV1Wrapper extends RESTBaseWrapper<StringConstantWrapper,
        RESTStringConstantV1> implements StringConstantWrapper {

    private final RESTStringConstantV1 stringConstant;

    protected RESTStringConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTStringConstantV1 stringConstant,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.stringConstant = RESTEntityProxyFactory.createProxy(providerFactory, stringConstant, isRevision);
    }

    @Override
    protected RESTStringConstantV1 getProxyEntity() {
        return stringConstant;
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }

    @Override
    public void setName(String name) {
        getProxyEntity().explicitSetName(name);
    }

    @Override
    public StringConstantWrapper clone(boolean deepCopy) {
        return new RESTStringConstantV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public String getValue() {
        return getProxyEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getProxyEntity().explicitSetValue(value);
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTStringConstantV1.class, true);
    }
}
