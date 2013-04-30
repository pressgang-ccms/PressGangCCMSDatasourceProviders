package org.jboss.pressgang.ccms.provider;

import java.util.List;

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

    TopicWrapper updateTopic(TopicWrapper topic);

    boolean deleteTopic(Integer id);

    CollectionWrapper<TopicWrapper> createTopics(final CollectionWrapper<TopicWrapper> topics);

    CollectionWrapper<TopicWrapper> updateTopics(final CollectionWrapper<TopicWrapper> topics);

    boolean deleteTopics(final List<Integer> topicIds);

    TopicWrapper newTopic();

    CollectionWrapper<TopicWrapper> newTopicCollection();
}
