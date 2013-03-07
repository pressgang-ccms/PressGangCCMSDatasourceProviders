package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;

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
