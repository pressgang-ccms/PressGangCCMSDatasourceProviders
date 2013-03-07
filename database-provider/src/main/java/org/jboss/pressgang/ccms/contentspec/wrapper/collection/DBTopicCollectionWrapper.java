package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.model.Topic;

public class DBTopicCollectionWrapper extends DBCollectionWrapper<TopicWrapper, Topic> {
    public DBTopicCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<Topic> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TopicWrapper.class);
    }
}
