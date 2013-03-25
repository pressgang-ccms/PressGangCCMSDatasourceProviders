package org.jboss.pressgang.ccms.provider;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedTopicProvider {
    TranslatedTopicWrapper getTranslatedTopic(final int id);

    TranslatedTopicWrapper getTranslatedTopic(final int id, final Integer revision);

    CollectionWrapper<TagWrapper> getTranslatedTopicTags(int id, final Integer revision);

    CollectionWrapper<PropertyTagInTopicWrapper> getTranslatedTopicProperties(int id, Integer revision);

    CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicOutgoingRelationships(int id, Integer revision);

    CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicIncomingRelationships(int id, Integer revision);

    CollectionWrapper<TopicSourceURLWrapper> getTranslatedTopicSourceUrls(int id, Integer revision);

    UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings(int id, Integer revision);

    CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicRevisions(int id, Integer revision);

    CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicsWithQuery(String query);

    TranslatedTopicWrapper createTranslatedTopic(TranslatedTopicWrapper translatedTopic) throws Exception;

    CollectionWrapper<TranslatedTopicWrapper> createTranslatedTopics(
            CollectionWrapper<TranslatedTopicWrapper> translatedTopics) throws Exception;

    TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper translatedTopic) throws Exception;

    CollectionWrapper<TranslatedTopicWrapper> updateTranslatedTopics(
            CollectionWrapper<TranslatedTopicWrapper> translatedTopics) throws Exception;

    boolean deleteTranslatedTopic(Integer id) throws Exception;

    boolean deleteTranslatedTopics(List<Integer> ids) throws Exception;

    TranslatedTopicWrapper newTranslatedTopic();

    CollectionWrapper<TranslatedTopicWrapper> newTranslatedTopicCollection();
}
