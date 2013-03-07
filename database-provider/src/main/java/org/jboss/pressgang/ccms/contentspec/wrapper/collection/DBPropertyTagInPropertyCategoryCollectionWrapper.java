package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;

public class DBPropertyTagInPropertyCategoryCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper,
        PropertyTagToPropertyTagCategory> {
    public DBPropertyTagInPropertyCategoryCollectionWrapper(final DBWrapperFactory wrapperFactory,
            final Collection<PropertyTagToPropertyTagCategory> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInPropertyCategoryWrapper.class);
    }
}