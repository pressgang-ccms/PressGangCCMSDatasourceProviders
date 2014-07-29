/*
  Copyright 2011-2014 Red Hat

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

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
