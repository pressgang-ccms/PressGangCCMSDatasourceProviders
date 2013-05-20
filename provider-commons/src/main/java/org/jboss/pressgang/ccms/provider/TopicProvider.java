package org.jboss.pressgang.ccms.provider;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TopicProvider {
    TopicWrapper getTopic(int id);

    TopicWrapper getTopic(int id, final Integer revision);

    CollectionWrapper<TopicWrapper> getTopics(final List<Integer> ids);

    CollectionWrapper<TopicWrapper> getTopicsWithQuery(String query);

    CollectionWrapper<TagWrapper> getTopicTags(int id, final Integer revision);

    UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getTopicProperties(int id, Integer revision);

    CollectionWrapper<TopicWrapper> getTopicOutgoingRelationships(int id, Integer revision);

    CollectionWrapper<TopicWrapper> getTopicIncomingRelationships(int id, Integer revision);

    CollectionWrapper<TopicSourceURLWrapper> getTopicSourceUrls(int id, Integer revision);

    CollectionWrapper<TranslatedTopicWrapper> getTopicTranslations(int id, final Integer revision);

    CollectionWrapper<TopicWrapper> getTopicRevisions(int id, Integer revision);

    TopicWrapper createTopic(TopicWrapper topic);

    TopicWrapper createTopic(TopicWrapper topic, LogMessageWrapper logMessage);

    TopicWrapper updateTopic(TopicWrapper topic);

    TopicWrapper updateTopic(TopicWrapper topic, LogMessageWrapper logMessage);

    boolean deleteTopic(Integer id);

    boolean deleteTopic(Integer id, LogMessageWrapper logMessage);

    CollectionWrapper<TopicWrapper> createTopics(CollectionWrapper<TopicWrapper> topics);

    CollectionWrapper<TopicWrapper> createTopics(CollectionWrapper<TopicWrapper> topics, LogMessageWrapper logMessage);

    CollectionWrapper<TopicWrapper> updateTopics(CollectionWrapper<TopicWrapper> topics);

    CollectionWrapper<TopicWrapper> updateTopics(CollectionWrapper<TopicWrapper> topics, LogMessageWrapper logMessage);

    boolean deleteTopics(final List<Integer> topicIds);

    boolean deleteTopics(final List<Integer> topicIds, LogMessageWrapper logMessage);

    TopicWrapper newTopic();

    CollectionWrapper<TopicWrapper> newTopicCollection();
}
