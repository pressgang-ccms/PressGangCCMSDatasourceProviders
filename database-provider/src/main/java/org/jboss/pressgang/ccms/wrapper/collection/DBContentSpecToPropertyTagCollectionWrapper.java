package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;

public class DBContentSpecToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInContentSpecWrapper,
        ContentSpecToPropertyTag> {
    public DBContentSpecToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory,
            final Collection<ContentSpecToPropertyTag> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInContentSpecWrapper.class);
    }
}