package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;

public class DBContentSpecToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInContentSpecWrapper,
        ContentSpecToPropertyTag> {
    public DBContentSpecToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory,
            final Collection<ContentSpecToPropertyTag> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInContentSpecWrapper.class);
    }
}