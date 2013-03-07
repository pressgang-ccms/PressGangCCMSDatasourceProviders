package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.model.TagToPropertyTag;

public class DBTagToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInTagWrapper, TagToPropertyTag> {
    public DBTagToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TagToPropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInTagWrapper.class);
    }
}