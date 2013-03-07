package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;

public class RESTPropertyTagInPropertyCategoryV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInPropertyCategoryWrapper,
        RESTPropertyTagInPropertyCategoryV1> implements PropertyTagInPropertyCategoryWrapper {
    private final RESTPropertyCategoryV1 parent;
    private final RESTPropertyTagInPropertyCategoryV1 propertyTag;

    protected RESTPropertyTagInPropertyCategoryV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryV1 propertyTag, boolean isRevision, final RESTPropertyCategoryV1 parent) {
        super(providerFactory, isRevision);
        this.propertyTag = RESTEntityProxyFactory.createProxy(providerFactory, propertyTag, isRevision, parent);
        this.parent = parent;
    }

    @Override
    public Integer getSort() {
        return getProxyEntity().getRelationshipSort();
    }

    @Override
    public void setSort(Integer sort) {
        getEntity().explicitSetRelationshipSort(sort);
    }

    @Override
    public Integer getRelationshipId() {
        return getProxyEntity().getRelationshipId();
    }

    @Override
    public void setName(String name) {
        getEntity().setName(name);
    }

    @Override
    public RESTPropertyTagInPropertyCategoryV1Wrapper clone(boolean deepCopy) {
        return new RESTPropertyTagInPropertyCategoryV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                parent);
    }

    @Override
    protected RESTPropertyTagInPropertyCategoryV1 getProxyEntity() {
        return propertyTag;
    }

    @Override
    public CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTPropertyTagInPropertyCategoryV1.class, true,
                parent);
    }
}
