package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;

public class DBTagToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInTagWrapper, TagToPropertyTag> {
    public DBTagToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TagToPropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInTagWrapper.class);
    }
}