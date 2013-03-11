package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface TopicSourceURLProvider {

    CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, Integer revision);

    TopicSourceURLWrapper newTopicSourceURL(TopicWrapper parent);

    CollectionWrapper<TopicSourceURLWrapper> newTopicSourceURLCollection(TopicWrapper parent);
}
