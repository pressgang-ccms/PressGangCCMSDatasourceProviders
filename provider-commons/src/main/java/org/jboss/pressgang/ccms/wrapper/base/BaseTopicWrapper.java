/*
  Copyright 2011-2014 Red Hat, Inc

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

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.LocaleWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public interface BaseTopicWrapper<T extends BaseTopicWrapper<T>> extends AuditedEntityWrapper<T> {
    Integer getTopicId();

    Integer getTopicRevision();

    /**
     * Get the Topics Title.
     *
     * @return The topic title.
     */
    String getTitle();

    void setTitle(String title);

    /**
     * Get the Topics XML Content.
     *
     * @return The topics XML.
     */
    String getXml();

    void setXml(String xml);

    Integer getXmlFormat();

    LocaleWrapper getLocale();

    void setLocale(LocaleWrapper locale);

    CollectionWrapper<TagWrapper> getTags();

    void setTags(CollectionWrapper<TagWrapper> tags);

    CollectionWrapper<T> getOutgoingRelationships();

    void setOutgoingRelationships(CollectionWrapper<T> outgoingTopics);

    CollectionWrapper<T> getIncomingRelationships();

    void setIncomingRelationships(CollectionWrapper<T> incomingTopics);

    UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties();

    void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties);

    UpdateableCollectionWrapper<TopicSourceURLWrapper> getSourceURLs();

    void setSourceURLs(UpdateableCollectionWrapper<TopicSourceURLWrapper> sourceURLs);

    List<TagWrapper> getTagsInCategories(final List<Integer> categoryIds);

    boolean hasTag(final int tagId);

    PropertyTagInTopicWrapper getProperty(final int propertyId);

    List<PropertyTagInTopicWrapper> getProperties(final int propertyId);

    String getBugzillaBuildId();

    String getEditorURL(final ZanataDetails zanataDetails);

    String getPressGangURL();

    String getErrorXRefId();

    String getXRefId();

    String getXRefPropertyOrId(final int propertyId);
}
