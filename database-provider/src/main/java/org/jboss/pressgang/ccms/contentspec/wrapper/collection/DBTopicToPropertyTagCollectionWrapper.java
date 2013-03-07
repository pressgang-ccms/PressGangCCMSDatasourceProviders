package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;

public class DBTopicToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInTopicWrapper, TopicToPropertyTag> {
    public DBTopicToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TopicToPropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInTopicWrapper.class);
    }
}