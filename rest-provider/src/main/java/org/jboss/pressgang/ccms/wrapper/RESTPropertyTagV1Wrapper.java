package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTPropertyTagV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagWrapper,
        RESTPropertyTagV1> implements PropertyTagWrapper {

    private final RESTPropertyTagV1 propertyTag;

    protected RESTPropertyTagV1Wrapper(final RESTProviderFactory providerFactory, final RESTPropertyTagV1 propertyTag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.propertyTag = RESTEntityProxyFactory.createProxy(providerFactory, propertyTag, isRevision);
    }

    @Override
    public void setName(String name) {
        getEntity().explicitSetName(name);
    }

    @Override
    public RESTPropertyTagV1Wrapper clone(boolean deepCopy) {
        return new RESTPropertyTagV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity());
    }

    @Override
    protected RESTPropertyTagV1 getProxyEntity() {
        return propertyTag;
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTPropertyTagV1.class, true);
    }
}