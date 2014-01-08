package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;

public class RESTPropertyTagInPropertyCategoryV1Wrapper extends RESTBasePropertyTagV1Wrapper<PropertyTagInPropertyCategoryWrapper,
        RESTPropertyTagInPropertyCategoryV1> implements PropertyTagInPropertyCategoryWrapper {

    protected RESTPropertyTagInPropertyCategoryV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryV1 propertyTag, boolean isRevision, final RESTPropertyCategoryV1 parent,
            boolean isNewEntity) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity);
    }

    protected RESTPropertyTagInPropertyCategoryV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryV1 propertyTag, boolean isRevision, final RESTPropertyCategoryV1 parent,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, propertyTag, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTPropertyCategoryV1 getParentEntity() {
        return (RESTPropertyCategoryV1) super.getParentEntity();
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
                getParentEntity(), isNewEntity());
    }

}
