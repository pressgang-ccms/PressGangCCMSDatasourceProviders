package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;

public class DBTopicToPropertyTagCollectionWrapper extends DBUpdateableCollectionWrapper<PropertyTagInTopicWrapper, TopicToPropertyTag> {
    public DBTopicToPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TopicToPropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagInTopicWrapper.class);
    }
}