package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;

public class DBTopicSourceURLCollectionWrapper extends DBUpdateableCollectionWrapper<TopicSourceURLWrapper, TopicSourceUrl> {
    public DBTopicSourceURLCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TopicSourceUrl> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TopicSourceURLWrapper.class);
    }
}
