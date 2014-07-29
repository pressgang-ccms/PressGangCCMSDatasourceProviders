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

import java.util.Arrays;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInTopicProvider extends RESTPropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInTopicProvider.class);

    protected RESTPropertyTagInTopicProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    /**
     * Get the REST Property Tag Revision Collection for a specific Property Tag defined by ID and Revision.
     *
     * @param id
     * @param revision
     * @param topic
     * @return
     */
    public RESTAssignedPropertyTagCollectionV1 getRESTPropertyTagInTopicRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        final RESTAssignedPropertyTagCollectionV1 propertyTagRevisions;
        if (topic instanceof RESTTranslatedTopicV1) {
            propertyTagRevisions = getRESTTranslatedTopicPropertyRevisions(id, revision, topic);
        } else {
            propertyTagRevisions = getRESTTopicPropertyRevisions(id, revision, topic);
        }

        return propertyTagRevisions;
    }

    /**
     * Get the Wrapped Property Tag Revision Collection for a specific Property Tag defined by ID and Revision.
     *
     * @param id
     * @param revision
     * @param topic
     * @return
     */
    public CollectionWrapper<PropertyTagInTopicWrapper> getPropertyTagInTopicRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        return RESTCollectionWrapperBuilder.<PropertyTagInTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTPropertyTagInTopicRevisions(id, revision, topic))
                .isRevisionCollection()
                .parent(topic)
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .entityWrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @SuppressWarnings("unchecked")
    protected RESTAssignedPropertyTagCollectionV1 getRESTTopicPropertyRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTopicV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, tagId, tagRevision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, tagId, tagRevision);
            }

            // We need to expand the all the property tag revisions in the topic
            final String expandString = getExpansionString(RESTTopicV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (tagRevision == null) {
                tempTopic = getRESTClient().getJSONTopic(tagId, expandString);
            } else {
                tempTopic = getRESTClient().getJSONTopicRevision(tagId, tagRevision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, tagRevision);
            } else if (topic.getProperties() == null) {
                topic.setProperties(tempTopic.getProperties());
            } else {
                // Iterate over the current and old properties and add any missing objects.
                final List<RESTAssignedPropertyTagV1> properties = topic.getProperties().returnItems();
                final List<RESTAssignedPropertyTagV1> newProperties = tempTopic.getProperties().returnItems();
                for (final RESTAssignedPropertyTagV1 newProperty : newProperties) {
                    boolean found = false;

                    for (final RESTAssignedPropertyTagV1 property : properties) {
                        if (property.getId().equals(newProperty.getId())) {
                            property.setRevisions(newProperty.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        topic.getProperties().addItem(newProperty);
                    }
                }
            }

            // Find the matching properties and return it's revisions
            for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : topic.getProperties().getItems()) {
                final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return propertyTag.getRevisions();
                }
            }

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for PropertyTagInTopic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected RESTAssignedPropertyTagCollectionV1 getRESTTranslatedTopicPropertyRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, topicId, topicRevision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, topicId, topicRevision);
            }

            // We need to expand the all the property tag revisions in the translated topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic;
            if (topicRevision == null) {
                tempTranslatedTopic = getRESTClient().getJSONTranslatedTopic(topicId, expandString);
            } else {
                tempTranslatedTopic = getRESTClient().getJSONTranslatedTopicRevision(topicId, topicRevision, expandString);
            }

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, topicRevision);
            } else if (translatedTopic.getSourceUrls_OTM() == null) {
                translatedTopic.setProperties(tempTranslatedTopic.getProperties());
            } else {
                // Iterate over the current and old source urls and add any missing objects.
                final List<RESTAssignedPropertyTagV1> properties = translatedTopic.getProperties().returnItems();
                final List<RESTAssignedPropertyTagV1> newProperties = tempTranslatedTopic.getProperties().returnItems();
                for (final RESTAssignedPropertyTagV1 newProperty : newProperties) {
                    boolean found = false;

                    for (final RESTAssignedPropertyTagV1 property : properties) {
                        if (property.getId().equals(newProperty.getId())) {
                            property.setRevisions(newProperty.getRevisions());

                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        translatedTopic.getProperties().addItem(newProperty);
                    }
                }
            }

            // Find the matching properties and return it's revisions
            for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : translatedTopic.getProperties().getItems()) {
                final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

                if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                    return propertyTag.getRevisions();
                }
            }

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for PropertyTagInTopic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }
}
