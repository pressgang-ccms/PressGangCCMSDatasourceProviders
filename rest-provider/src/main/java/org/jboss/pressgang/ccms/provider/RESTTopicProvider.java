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

package org.jboss.pressgang.ccms.provider;

import javax.ws.rs.core.PathSegment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.query.RESTTopicQueryBuilderV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.RESTTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTopicCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTopicProvider extends RESTDataProvider implements TopicProvider {
    private static final Logger LOG = LoggerFactory.getLogger(RESTTopicProvider.class);
    protected static final Map<String, Collection<String>> DEFAULT_EXPAND_MAP = new HashMap<String, Collection<String>>() {{
        put(RESTTopicV1.TAGS_NAME, Arrays.asList(RESTTagV1.CATEGORIES_NAME));
        put(RESTTopicV1.PROPERTIES_NAME, null);
    }};
    protected static final Set<String> DEFAULT_METHOD_MAP = new HashSet<String>() {{
        add("getTags");
        add("getProperties");
    }};
    protected static final Map<String, Collection<String>> DEFAULT_EXPAND_MAP_WITH_TRANSLATIONS = new HashMap<String,
            Collection<String>>() {{
        put(RESTTopicV1.TAGS_NAME, Arrays.asList(RESTTagV1.CATEGORIES_NAME));
        put(RESTTopicV1.PROPERTIES_NAME, null);
        put(RESTTopicV1.TRANSLATEDTOPICS_NAME, Arrays.asList(RESTTranslatedTopicV1.TRANSLATED_CSNODE_NAME));
    }};
    protected static final Set<String> DEFAULT_METHOD_MAP_WITH_TRANSLATIONS = new HashSet<String>() {{
        add("getTags");
        add("getProperties");
        add("getTranslatedTopics_OTM");
    }};

    private boolean expandTranslations = false;

    protected RESTTopicProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTTopicV1 loadTopic(int id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTopic(id, expandString);
        } else {
            return getRESTClient().getJSONTopicRevision(id, revision, expandString);
        }
    }

    public boolean isExpandTranslations() {
        return expandTranslations;
    }

    public void setExpandTranslations(final boolean expandTranslations) {
        this.expandTranslations = expandTranslations;
    }

    protected Map<String, Collection<String>> getDefaultTopicExpansionList() {
        if (expandTranslations) {
            return DEFAULT_EXPAND_MAP_WITH_TRANSLATIONS;
        } else {
            return DEFAULT_EXPAND_MAP;
        }
    }

    protected Set<String> getDefaultTopicMethodList() {
        if (expandTranslations) {
            return DEFAULT_METHOD_MAP_WITH_TRANSLATIONS;
        } else {
            return DEFAULT_METHOD_MAP;
        }
    }

    public RESTTopicV1 getRESTTopic(int id) {
        return getRESTTopic(id, null);
    }

    @Override
    public TopicWrapper getTopic(int id) {
        return getTopic(id, null);
    }

    public RESTTopicV1 getRESTTopic(int id, Integer revision) {
        try {
            final RESTTopicV1 topic;
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);
            } else {
                final String expansionString = getExpansionString(getDefaultTopicExpansionList());
                topic = loadTopic(id, revision, expansionString);
                getRESTEntityCache().add(topic, revision);
            }
            return topic;
        } catch (Exception e) {
            LOG.debug("Failed to retrieve Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TopicWrapper getTopic(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTTopic(id, revision))
                .expandedMethods(getDefaultTopicMethodList())
                .isRevision(revision != null)
                .build();
    }

    public RESTTagCollectionV1 getRESTTopicTags(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getTags() != null) {
                    return topic.getTags();
                }
            }

            // We need to expand the tags in the topic
            final String expandString = getExpansionString(RESTTopicV1.TAGS_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setTags(tempTopic.getTags());
            }
            getRESTEntityCache().add(topic.getTags(), revision != null);

            return topic.getTags();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Tags for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTopicTags(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicTags(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getTags"))
                .build();
    }

    public RESTTopicCollectionV1 getRESTTopics(final List<Integer> ids) {
        if (ids.isEmpty()) return null;

        try {
            final RESTTopicCollectionV1 topics = new RESTTopicCollectionV1();
            final Set<Integer> queryIds = new HashSet<Integer>();

            for (final Integer id : ids) {
                if (!getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id)) {
                    queryIds.add(id);
                } else {
                    topics.addItem(getRESTEntityCache().get(RESTTopicV1.class, id));
                }
            }

            // Get the missing topics from the REST interface
            if (!queryIds.isEmpty()) {
                final RESTTopicQueryBuilderV1 queryBuilder = new RESTTopicQueryBuilderV1();
                queryBuilder.setTopicIds(new ArrayList<Integer>(queryIds));

                // We need to expand the topic collection
                final String expandString = getExpansionString(RESTv1Constants.TOPICS_EXPANSION_NAME, getDefaultTopicExpansionList());

                // Load the topics from the REST Interface
                final RESTTopicCollectionV1 downloadedTopics = getRESTClient().getJSONTopicsWithQuery(queryBuilder.buildQueryPath(),
                        expandString);
                getRESTEntityCache().add(downloadedTopics);

                // Transfer the downloaded data to the current topic list
                if (downloadedTopics != null && downloadedTopics.getItems() != null) {
                    final List<RESTTopicV1> items = downloadedTopics.returnItems();
                    for (final RESTTopicV1 item : items) {
                        topics.addItem(item);
                    }
                }
            }

            return topics;
        } catch (Exception e) {
            LOG.debug("Failed to retrieve all Topics for the Ids: " + CollectionUtilities.toSeperatedString(ids), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopics(final List<Integer> ids) {
        if (ids.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopics(ids))
                .expandedEntityMethods(getDefaultTopicMethodList())
                .build();
    }

    public RESTTopicCollectionV1 getRESTTopicsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            // We need to expand the all the topics in the collection
            final String expandString = getExpansionString(RESTv1Constants.TOPICS_EXPANSION_NAME, getDefaultTopicExpansionList());

            final RESTTopicCollectionV1 topics = getRESTClient().getJSONTopicsWithQuery(new PathSegmentImpl(query, false), expandString);
            getRESTEntityCache().add(topics);

            return topics;
        } catch (Exception e) {
            LOG.debug("Failed to retrieve Topics with Query: " + query, e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicsWithQuery(query))
                .expandedEntityMethods(getDefaultTopicMethodList())
                .build();
    }

    public RESTTranslatedTopicCollectionV1 getRESTTopicTranslations(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getTranslatedTopics_OTM() != null) {
                    return topic.getTranslatedTopics_OTM();
                }
            }

            // We need to expand the the translated topics in the topic

            final String expandString = getExpansionString(RESTTopicV1.TRANSLATEDTOPICS_NAME, RESTTranslatedTopicProvider.DEFAULT_EXPAND_MAP);

            // Load the topic from the REST API
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setTranslatedTopics_OTM(tempTopic.getTranslatedTopics_OTM());
            }

            return topic.getTranslatedTopics_OTM();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Translations for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTopicTranslations(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicTranslations(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(RESTTranslatedTopicProvider.DEFAULT_METHOD_MAP)
                .build();
    }

    public RESTTopicCollectionV1 getRESTTopicRevisions(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getRevisions() != null) {
                    return topic.getRevisions();
                }
            }

            // We need to expand the revisions in the topic
            final String expandString = getExpansionString(RESTTopicV1.REVISIONS_NAME);

            // Load the topic from the REST API
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setRevisions(tempTopic.getRevisions());
            }

            return topic.getRevisions();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Revisions for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicRevisions(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicRevisions(id, revision))
                .isRevisionCollection()
                .build();
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTTopicProperties(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getProperties() != null) {
                    return topic.getProperties();
                }
            }

            // We need to expand the all the properties in the topic
            final String expandString = getExpansionString(RESTTopicV1.PROPERTIES_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setProperties(tempTopic.getProperties());
            }

            return topic.getProperties();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Properties for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getTopicProperties(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Properties using V1 of the REST Interface.");
    }

    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getTopicProperties(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .isRevisionCollection(revision != null)
                .entityWrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    public RESTTopicCollectionV1 getRESTTopicOutgoingRelationships(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getOutgoingRelationships() != null) {
                    return topic.getOutgoingRelationships();
                }
            }

            // We need to expand the outgoing topic relationships in the topic
            final String expandString = getExpansionString(RESTTopicV1.OUTGOING_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setOutgoingRelationships(tempTopic.getOutgoingRelationships());
            }

            return topic.getOutgoingRelationships();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Outgoing Relationships for Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicOutgoingRelationships(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicOutgoingRelationships(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getOutgoingRelationships"))
                .build();
    }

    public RESTTopicCollectionV1 getRESTTopicIncomingRelationships(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getIncomingRelationships() != null) {
                    return topic.getIncomingRelationships();
                }
            }

            // We need to expand the incoming topic relationships in the topic
            final String expandString = getExpansionString(RESTTopicV1.INCOMING_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setIncomingRelationships(tempTopic.getIncomingRelationships());
            }

            return topic.getIncomingRelationships();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Incoming Relationships for Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicIncomingRelationships(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicIncomingRelationships(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getIncomingRelationships"))
                .build();
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceUrls(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
    }

    public RESTTopicSourceUrlCollectionV1 getRESTTopicSourceUrls(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, id, revision);

                if (topic.getSourceUrls_OTM() != null) {
                    return topic.getSourceUrls_OTM();
                }
            }

            // We need to expand the source urls in the topic
            final String expandString = getExpansionString(RESTTopicV1.SOURCE_URLS_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic = loadTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setSourceUrls_OTM(tempTopic.getSourceUrls_OTM());
            }

            return topic.getSourceUrls_OTM();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Source URLs for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceUrls(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        return RESTCollectionWrapperBuilder.<TopicSourceURLWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTopicSourceUrls(id, revision))
                .isRevisionCollection(revision != null)
                .parent(parent)
                .expandedEntityMethods(Arrays.asList("getSourceUrls_OTM"))
                .build();
    }

    @Override
    public TopicWrapper createTopic(final TopicWrapper topicEntity) {
        return createTopic(topicEntity, null);
    }

    @Override
    public TopicWrapper createTopic(TopicWrapper topicEntity, LogMessageWrapper logMessage) {
        try {
            final RESTTopicV1 topic = ((RESTTopicV1Wrapper) topicEntity).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(topic);

            final String expansionString = getExpansionString(getDefaultTopicExpansionList());

            final RESTTopicV1 createdTopic;
            if (logMessage != null) {
                createdTopic = getRESTClient().createJSONTopic(expansionString, topic, logMessage.getMessage(), logMessage.getFlags(),
                        logMessage.getUser());
            } else {
                createdTopic = getRESTClient().createJSONTopic(expansionString, topic);
            }
            if (createdTopic != null) {
                getRESTEntityCache().add(createdTopic);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(createdTopic)
                        .expandedMethods(getDefaultTopicMethodList())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to create Topic", e);
            throw handleException(e);
        }
    }

    @Override
    public TopicWrapper updateTopic(TopicWrapper topicEntity) {
        return updateTopic(topicEntity, null);
    }

    @Override
    public TopicWrapper updateTopic(TopicWrapper topicEntity, LogMessageWrapper logMessage) {
        try {
            final RESTTopicV1 topic = ((RESTTopicV1Wrapper) topicEntity).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(topic);

            final String expansionString = getExpansionString(getDefaultTopicExpansionList());

            final RESTTopicV1 updatedTopic;
            if (logMessage != null) {
                updatedTopic = getRESTClient().updateJSONTopic(expansionString, topic, logMessage.getMessage(), logMessage.getFlags(),
                        logMessage.getUser());
            } else {
                updatedTopic = getRESTClient().updateJSONTopic(expansionString, topic);
            }
            if (updatedTopic != null) {
                getRESTEntityCache().expire(RESTTopicV1.class, updatedTopic.getId());
                getRESTEntityCache().add(updatedTopic);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(updatedTopic)
                        .expandedMethods(getDefaultTopicMethodList())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to update Topic " + topicEntity.getId(), e);
            throw handleException(e);
        }
    }

    @Override
    public boolean deleteTopic(Integer id) {
        return deleteTopic(id, null);
    }

    @Override
    public boolean deleteTopic(Integer id, LogMessageWrapper logMessage) {
        try {
            if (logMessage != null) {
                return getRESTClient().deleteJSONTopic(id, logMessage.getMessage(), logMessage.getFlags(), logMessage.getUser(),
                        "") != null;
            } else {
                return getRESTClient().deleteJSONTopic(id, "") != null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to delete Topic " + id, e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> createTopics(CollectionWrapper<TopicWrapper> topics) {
        return updateTopics(topics, null);
    }

    @Override
    public CollectionWrapper<TopicWrapper> createTopics(CollectionWrapper<TopicWrapper> topics, LogMessageWrapper logMessage) {
        try {
            final RESTTopicCollectionV1 unwrappedTopics = ((RESTTopicCollectionV1Wrapper) topics).unwrap();

            // Clean the collection to remove anything that doesn't need to be sent to the server
            cleanCollectionForSave(unwrappedTopics, false);

            final String expandString = getExpansionString(RESTv1Constants.TOPICS_EXPANSION_NAME, getDefaultTopicExpansionList());
            final RESTTopicCollectionV1 createdTopics;
            if (logMessage != null) {
                createdTopics = getRESTClient().createJSONTopics(expandString, unwrappedTopics, logMessage.getMessage(),
                        logMessage.getFlags(), logMessage.getUser());
            } else {
                createdTopics = getRESTClient().createJSONTopics(expandString, unwrappedTopics);
            }
            if (createdTopics != null) {
                getRESTEntityCache().add(createdTopics, false);
                return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                        .providerFactory(getProviderFactory())
                        .collection(createdTopics)
                        .expandedEntityMethods(getDefaultTopicMethodList())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to create Topics", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> updateTopics(CollectionWrapper<TopicWrapper> topics) {
        return updateTopics(topics, null);
    }

    @Override
    public CollectionWrapper<TopicWrapper> updateTopics(CollectionWrapper<TopicWrapper> topics, LogMessageWrapper logMessage) {
        try {
            final RESTTopicCollectionV1 unwrappedTopics = ((RESTTopicCollectionV1Wrapper) topics).unwrap();

            // Clean the collection to remove anything that doesn't need to be sent to the server
            cleanCollectionForSave(unwrappedTopics, false);

            final String expandString = getExpansionString(RESTv1Constants.TOPICS_EXPANSION_NAME, getDefaultTopicExpansionList());
            final RESTTopicCollectionV1 updatedTopics;
            if (logMessage != null) {
                updatedTopics = getRESTClient().updateJSONTopics(expandString, unwrappedTopics, logMessage.getMessage(),
                        logMessage.getFlags(), logMessage.getUser());
            } else {
                updatedTopics = getRESTClient().updateJSONTopics(expandString, unwrappedTopics);
            }
            if (updatedTopics != null) {
                // Expire the old cached data
                for (final RESTTopicV1 topic : updatedTopics.returnItems()) {
                    getRESTEntityCache().expire(RESTTopicV1.class, topic.getId());
                }
                // Add the new data to the cache
                getRESTEntityCache().add(updatedTopics, false);
                return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                        .providerFactory(getProviderFactory())
                        .collection(updatedTopics)
                        .expandedEntityMethods(getDefaultTopicMethodList())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public boolean deleteTopics(final List<Integer> topicIds) {
        return deleteTopics(topicIds, null);
    }

    @Override
    public boolean deleteTopics(List<Integer> topicIds, LogMessageWrapper logMessage) {
        try {
            final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
            final PathSegment path = new PathSegmentImpl(pathString, false);
            if (logMessage != null) {
                return getRESTClient().deleteJSONTopics(path, logMessage.getMessage(), logMessage.getFlags(), logMessage.getUser(),
                        "") != null;
            } else {
                return getRESTClient().deleteJSONTopics(path, "") != null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to delete Topics " + CollectionUtilities.toSeperatedString(topicIds, ", "), e);
            throw handleException(e);
        }
    }

    @Override
    public TopicWrapper newTopic() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTTopicV1())
                .newEntity()
                .build();
    }

    @Override
    public CollectionWrapper<TopicWrapper> newTopicCollection() {
        return RESTCollectionWrapperBuilder.<TopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTTopicCollectionV1())
                .build();
    }
}
