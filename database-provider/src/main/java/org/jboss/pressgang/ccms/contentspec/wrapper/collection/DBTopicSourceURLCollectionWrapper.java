package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;

public class DBTopicSourceURLCollectionWrapper extends DBCollectionWrapper<TopicSourceURLWrapper, TopicSourceUrl> {
    public DBTopicSourceURLCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TopicSourceUrl> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TopicSourceURLWrapper.class);
    }
}
