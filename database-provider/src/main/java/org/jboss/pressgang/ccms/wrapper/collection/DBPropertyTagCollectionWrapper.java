package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;

public class DBPropertyTagCollectionWrapper extends DBCollectionWrapper<PropertyTagWrapper, PropertyTag> {
    public DBPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<PropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagWrapper.class);
    }
}