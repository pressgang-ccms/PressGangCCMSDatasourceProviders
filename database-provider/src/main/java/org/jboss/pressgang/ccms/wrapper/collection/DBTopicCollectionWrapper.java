package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;

public class DBTopicCollectionWrapper extends DBCollectionWrapper<TopicWrapper, Topic> {
    public DBTopicCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<Topic> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TopicWrapper.class);
    }
}
