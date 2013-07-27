package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBasePropertyTagWrapper;

public class DBPropertyTagToPropertyTagCategoryWrapper extends DBBasePropertyTagWrapper<PropertyTagInPropertyCategoryWrapper,
        PropertyTagToPropertyTagCategory> implements PropertyTagInPropertyCategoryWrapper {

    private final PropertyTagToPropertyTagCategory propertyTagToPropertyCategory;

    public DBPropertyTagToPropertyTagCategoryWrapper(final DBProviderFactory providerFactory,
            final PropertyTagToPropertyTagCategory propertyTag, boolean isRevision) {
        super(providerFactory, isRevision, PropertyTagToPropertyTagCategory.class);
        this.propertyTagToPropertyCategory = propertyTag;
    }

    @Override
    protected PropertyTagToPropertyTagCategory getEntity() {
        return propertyTagToPropertyCategory;
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getEntity().getPropertyTag();
    }

    @Override
    public PropertyTagToPropertyTagCategory unwrap() {
        return propertyTagToPropertyCategory;
    }

    @Override
    public Integer getSort() {
        return getEntity().getSorting();
    }

    @Override
    public void setSort(Integer sort) {
        getEntity().setSorting(sort);
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }
}
