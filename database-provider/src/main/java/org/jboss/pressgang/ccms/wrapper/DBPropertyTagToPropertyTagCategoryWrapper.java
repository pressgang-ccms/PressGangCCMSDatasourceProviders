package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBasePropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBPropertyTagToPropertyTagCategoryWrapper extends DBBasePropertyTagWrapper<PropertyTagInPropertyCategoryWrapper> implements
        PropertyTagInPropertyCategoryWrapper {

    private final PropertyTagToPropertyTagCategory propertyTagToPropertyCategory;

    public DBPropertyTagToPropertyTagCategoryWrapper(final DBProviderFactory providerFactory,
            final PropertyTagToPropertyTagCategory propertyTag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.propertyTagToPropertyCategory = propertyTag;
    }

    protected PropertyTagToPropertyTagCategory getPropertyTagToPropertyCategory() {
        return propertyTagToPropertyCategory;
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getPropertyTagToPropertyCategory().getPropertyTag();
    }

    @Override
    public CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getRevisions() {
        return getWrapperFactory().createCollection(
                EnversUtilities.getRevisionEntities(getEntityManager(), getPropertyTagToPropertyCategory()),
                PropertyTagToPropertyTagCategory.class, true);
    }

    @Override
    public PropertyTagToPropertyTagCategory unwrap() {
        return propertyTagToPropertyCategory;
    }

    @Override
    public Integer getSort() {
        return getPropertyTagToPropertyCategory().getSorting();
    }

    @Override
    public void setSort(Integer sort) {
        getPropertyTagToPropertyCategory().setSorting(sort);
    }

    @Override
    public Integer getRelationshipId() {
        return getPropertyTagToPropertyCategory().getId();
    }
}
