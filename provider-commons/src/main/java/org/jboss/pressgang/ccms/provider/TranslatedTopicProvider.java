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

    TranslatedTopicWrapper createTranslatedTopic(TranslatedTopicWrapper translatedTopic);

    TranslatedTopicWrapper createTranslatedTopic(TranslatedTopicWrapper translatedTopic, LogMessageWrapper logMessage);

    CollectionWrapper<TranslatedTopicWrapper> createTranslatedTopics(CollectionWrapper<TranslatedTopicWrapper> translatedTopics);

    TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper translatedTopic);

    TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper translatedTopic, LogMessageWrapper logMessage);

    CollectionWrapper<TranslatedTopicWrapper> updateTranslatedTopics(CollectionWrapper<TranslatedTopicWrapper> translatedTopics);

    boolean deleteTranslatedTopic(Integer id);

    boolean deleteTranslatedTopics(List<Integer> ids);

    TranslatedTopicWrapper newTranslatedTopic();

    CollectionWrapper<TranslatedTopicWrapper> newTranslatedTopicCollection();
}
