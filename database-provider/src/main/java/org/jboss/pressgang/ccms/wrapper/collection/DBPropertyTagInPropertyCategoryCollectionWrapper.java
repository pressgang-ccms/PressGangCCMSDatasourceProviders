package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;

public class DBPropertyTagInPropertyCategoryCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper,
        PropertyTagToPropertyTagCategory> {
    public DBPropertyTagInPropertyCategoryCollectionWrapper(final DBWrapperFactory wrapperFactory,
            final Collection<PropertyTagToPropertyTagCategory> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInPropertyCategoryWrapper.class);
    }
}