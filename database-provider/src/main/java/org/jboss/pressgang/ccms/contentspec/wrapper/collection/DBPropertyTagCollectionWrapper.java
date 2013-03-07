package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.model.PropertyTag;

public class DBPropertyTagCollectionWrapper extends DBCollectionWrapper<PropertyTagWrapper, PropertyTag> {
    public DBPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<PropertyTag> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagWrapper.class);
    }
}