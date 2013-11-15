package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTStringConstantV1Wrapper extends RESTBaseEntityWrapper<StringConstantWrapper,
        RESTStringConstantV1> implements StringConstantWrapper {

    protected RESTStringConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTStringConstantV1 stringConstant,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, stringConstant, isRevision, isNewEntity);
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
        return new RESTStringConstantV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
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
