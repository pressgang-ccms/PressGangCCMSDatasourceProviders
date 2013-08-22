package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTPropertyTagInPropertyCategoryV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInPropertyCategoryWrapper,
        RESTPropertyTagInPropertyCategoryV1> implements PropertyTagInPropertyCategoryWrapper {
    private final RESTPropertyCategoryV1 parent;

    protected RESTPropertyTagInPropertyCategoryV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryV1 propertyTag, boolean isRevision, final RESTPropertyCategoryV1 parent,
            boolean isNewEntity) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity);
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
                parent, isNewEntity());
    }

    @Override
    public CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTPropertyTagInPropertyCategoryV1.class, true,
                parent);
    }
}
