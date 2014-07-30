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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.RESTTranslatedTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTranslatedTopicCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedTopicProvider extends RESTDataProvider implements TranslatedTopicProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedTopicProvider.class);

    // DEFAULT EXPANSION
    protected static final Map<String, Collection<String>> DEFAULT_EXPAND_MAP = new HashMap<String, Collection<String>>() {{
        put(RESTTranslatedTopicV1.TAGS_NAME, Arrays.asList(RESTTagV1.CATEGORIES_NAME));
        put(RESTTranslatedTopicV1.PROPERTIES_NAME, null);
        put(RESTTranslatedTopicV1.TRANSLATED_CSNODE_NAME, null);
    }};
    protected static final Set<String> DEFAULT_METHOD_MAP = new HashSet<String>() {{
        add("getTags");
        add("getProperties");
        add("getTranslatedCSNode");
    }};

    // DEFAULT EXPANSION WITH TOPIC
    protected static final Map<String, Collection<String>> DEFAULT_EXPAND_MAP_WITH_TOPIC = new HashMap<String, Collection<String>>() {{
        put(RESTTranslatedTopicV1.TOPIC_NAME, null);
        put(RESTTranslatedTopicV1.TAGS_NAME, Arrays.asList(RESTTagV1.CATEGORIES_NAME));
        put(RESTTranslatedTopicV1.PROPERTIES_NAME, null);
        put(RESTTranslatedTopicV1.TRANSLATED_CSNODE_NAME, null);
    }};
    protected static final Set<String> DEFAULT_METHOD_MAP_WITH_TOPIC = new HashSet<String>() {{
        add("getTopic");
        add("getTags");
        add("getProperties");
        add("getTranslatedCSNode");
    }};

    // DEFAULT EXPANSION WITH TOPIC AND TRANSLATED STRINGS
    protected static final Map<String, Collection<String>> DEFAULT_EXPAND_MAP_WITH_TOPIC_AND_STRINGS = new HashMap<String,
            Collection<String>>() {{
        put(RESTTranslatedTopicV1.TOPIC_NAME, null);
        put(RESTTranslatedTopicV1.TAGS_NAME, Arrays.asList(RESTTagV1.CATEGORIES_NAME));
        put(RESTTranslatedTopicV1.PROPERTIES_NAME, null);
        put(RESTTranslatedTopicV1.TRANSLATED_CSNODE_NAME, null);
        put(RESTTranslatedTopicV1.TRANSLATEDTOPICSTRING_NAME, null);
    }};
    protected static final Set<String> DEFAULT_METHOD_MAP_WITH_TOPIC_AND_STRINGS = new HashSet<String>() {{
        add("getTopic");
        add("getTags");
        add("getProperties");
        add("getTranslatedCSNode");
        add("getTranslatedTopicStrings_OTM");
    }};

    protected RESTTranslatedTopicProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTTranslatedTopicV1 loadTranslatedTopic(int id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTranslatedTopic(id, expandString);
        } else {
            return getRESTClient().getJSONTranslatedTopicRevision(id, revision, expandString);
        }
    }

    public RESTTranslatedTopicV1 getRESTTranslatedTopic(int id) {
        return getRESTTranslatedTopic(id, null);
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic(int id) {
        return getTranslatedTopic(id, null);
    }

    public RESTTranslatedTopicV1 getRESTTranslatedTopic(int id, final Integer revision) {
        try {
            final RESTTranslatedTopicV1 translatedTopic;
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);
            } else {
                final String expandString = super.getExpansionString(DEFAULT_EXPAND_MAP_WITH_TOPIC);
                translatedTopic = loadTranslatedTopic(id, revision, expandString);
                getRESTEntityCache().add(translatedTopic, revision);
            }
            return translatedTopic;
        } catch (Exception e) {
            log.debug("Failed to retrieve Translated Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic(int id, final Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTTranslatedTopic(id, revision))
                .isRevision(revision != null)
                .expandedMethods(DEFAULT_METHOD_MAP_WITH_TOPIC)
                .build();
    }

    public RESTTagCollectionV1 getRESTTranslatedTopicTags(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getTags() != null) {
                    return translatedTopic.getTags();
                }
            }

            // We need to expand the all the tags in the translated topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.TAGS_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setTags(tempTranslatedTopic.getTags());
            }

            return translatedTopic.getTags();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Tags for Translated Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTranslatedTopicTags(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicTags(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getTags"))
                .build();
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTTranslatedTopicProperties(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getProperties() != null) {
                    return topic.getProperties();
                }
            }

            // We need to expand the all the properties in the translated topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.PROPERTIES_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTopic = loadTranslatedTopic(id, revision, expandString);

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, revision);
            } else {
                topic.setProperties(tempTopic.getProperties());
            }

            return topic.getProperties();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Properties for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<PropertyTagInTopicWrapper> getTranslatedTopicProperties(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<PropertyTagInTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicProperties(id, revision))
                .isRevisionCollection(revision != null)
                .entityWrapperInterface(PropertyTagInTopicWrapper.class)
                .expandedEntityMethods(Arrays.asList("getProperties"))
                .build();
    }

    public RESTTranslatedTopicCollectionV1 getRESTTranslatedTopicOutgoingRelationships(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getOutgoingRelationships() != null) {
                    return translatedTopic.getOutgoingRelationships();
                }
            }

            // We need to expand the all the outgoing relationships in the topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.OUTGOING_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setOutgoingRelationships(tempTranslatedTopic.getOutgoingRelationships());
            }

            return translatedTopic.getOutgoingRelationships();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Outgoing Topics for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicOutgoingRelationships(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicOutgoingRelationships(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getOutgoingRelationships"))
                .build();
    }

    public RESTTranslatedTopicCollectionV1 getRESTTranslatedTopicIncomingRelationships(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getIncomingRelationships() != null) {
                    return translatedTopic.getIncomingRelationships();
                }
            }

            // We need to expand the all the incoming relationships in the topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.INCOMING_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setIncomingRelationships(tempTranslatedTopic.getIncomingRelationships());
            }

            return translatedTopic.getIncomingRelationships();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Incoming Topics for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicIncomingRelationships(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicIncomingRelationships(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getIncomingRelationships"))
                .build();
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTranslatedTopicSourceUrls(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
    }

    public RESTTopicSourceUrlCollectionV1 getRESTTranslatedTopicSourceUrls(int id, Integer revision, RESTBaseTopicV1<?, ?, ?> parent) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getSourceUrls_OTM() != null) {
                    return translatedTopic.getSourceUrls_OTM();
                }
            }

            // We need to expand the all the source urls in the topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.SOURCE_URLS_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setSourceUrls_OTM(tempTranslatedTopic.getSourceUrls_OTM());
            }

            return translatedTopic.getSourceUrls_OTM();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Source URLs for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<TopicSourceURLWrapper> getTranslatedTopicSourceUrls(int id, Integer revision,
            RESTBaseTopicV1<?, ?, ?> parent) {
        return RESTCollectionWrapperBuilder.<TopicSourceURLWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicSourceUrls(id, revision, parent))
                .parent(parent)
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getSourceUrls_OTM"))
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public RESTTranslatedTopicStringCollectionV1 getRESTTranslatedTopicStrings(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getTranslatedTopicStrings_OTM() != null) {
                    return translatedTopic.getTranslatedTopicStrings_OTM();
                }
            }

            // We need to expand the all the translated strings in the topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.TRANSLATEDTOPICSTRING_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setTranslatedTopicStrings_OTM(tempTranslatedTopic.getTranslatedTopicStrings_OTM());
            }

            return translatedTopic.getTranslatedTopicStrings_OTM();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Translated Topic Strings for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings(int id, Integer revision,
            RESTTranslatedTopicV1 parent) {
        return (UpdateableCollectionWrapper<TranslatedTopicStringWrapper>) RESTCollectionWrapperBuilder
                .<TranslatedTopicStringWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicStrings(id, revision))
                .isRevisionCollection(revision != null)
                .parent(parent)
                .build();
    }

    public RESTTranslatedTopicCollectionV1 getRESTTranslatedTopicRevisions(int id, final Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getRevisions() != null) {
                    return translatedTopic.getRevisions();
                }
            }

            // We need to expand the all the revisions in the topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.REVISIONS_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setRevisions(tempTranslatedTopic.getRevisions());
            }

            return translatedTopic.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Translated Topic " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicRevisions(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicRevisions(id, revision))
                .isRevisionCollection()
                .build();
    }

    public RESTTranslatedCSNodeV1 getRESTTranslatedTopicTranslatedCSNode(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getTranslatedCSNode() != null) {
                    return translatedTopic.getTranslatedCSNode();
                }
            }

            // We need to expand the translated cs node in the topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.TRANSLATED_CSNODE_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(id, revision, expandString);

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                getRESTEntityCache().add(translatedTopic, revision);
            } else {
                translatedTopic.setTranslatedCSNode(tempTranslatedTopic.getTranslatedCSNode());
            }

            return translatedTopic.getTranslatedCSNode();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Translated Content Spec Node for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTTranslatedTopicCollectionV1 getRESTTranslatedTopicsWithQuery(String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            // We need to expand the all the translated topics in the collection
            final String expandString = getExpansionString(RESTv1Constants.TRANSLATEDTOPICS_EXPANSION_NAME, DEFAULT_EXPAND_MAP_WITH_TOPIC);
            final RESTTranslatedTopicCollectionV1 topics = getRESTClient().getJSONTranslatedTopicsWithQuery(
                    new PathSegmentImpl(query, false), expandString);
            getRESTEntityCache().add(topics);

            return topics;
        } catch (Exception e) {
            log.debug("Failed to retrieve Translated Topics with Query: " + query, e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicsWithQuery(String query) {
        if (query == null || query.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedTopicsWithQuery(query))
                .expandedEntityMethods(DEFAULT_METHOD_MAP_WITH_TOPIC)
                .build();
    }

    @Override
    public TranslatedTopicWrapper createTranslatedTopic(final TranslatedTopicWrapper topicEntity) {
        return createTranslatedTopic(topicEntity, null);
    }

    @Override
    public TranslatedTopicWrapper createTranslatedTopic(final TranslatedTopicWrapper topicEntity, final LogMessageWrapper logMessage) {
        try {
            final RESTTranslatedTopicV1 translatedTopic = ((RESTTranslatedTopicV1Wrapper) topicEntity).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(translatedTopic);

            final String expansionString = getExpansionString(DEFAULT_EXPAND_MAP_WITH_TOPIC);

            final RESTTranslatedTopicV1 createdTopic;
            if (logMessage != null) {
                createdTopic = getRESTClient().createJSONTranslatedTopic(expansionString, translatedTopic, logMessage.getMessage(),
                        logMessage.getFlags(), logMessage.getUser());
            } else {
                createdTopic = getRESTClient().createJSONTranslatedTopic(expansionString, translatedTopic);
            }
            if (createdTopic != null) {
                getRESTEntityCache().add(createdTopic);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(createdTopic)
                        .expandedMethods(DEFAULT_METHOD_MAP_WITH_TOPIC)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper topicEntity) {
        return updateTranslatedTopic(topicEntity, null);
    }

    @Override
    public TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper topicEntity, LogMessageWrapper logMessage) {
        try {
            final RESTTranslatedTopicV1 translatedTopic = ((RESTTranslatedTopicV1Wrapper) topicEntity).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(translatedTopic);

            final String expansionString = getExpansionString(DEFAULT_EXPAND_MAP_WITH_TOPIC);

            final RESTTranslatedTopicV1 updatedTopic;
            if (logMessage != null) {
                updatedTopic = getRESTClient().updateJSONTranslatedTopic(expansionString, translatedTopic, logMessage.getMessage(),
                        logMessage.getFlags(), logMessage.getUser());
            } else {
                updatedTopic = getRESTClient().updateJSONTranslatedTopic(expansionString, translatedTopic);
            }
            if (updatedTopic != null) {
                getRESTEntityCache().expire(RESTTranslatedTopicV1.class, updatedTopic.getId());
                getRESTEntityCache().add(updatedTopic);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(updatedTopic)
                        .expandedMethods(DEFAULT_METHOD_MAP_WITH_TOPIC)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public boolean deleteTranslatedTopic(Integer id) {
        try {
            final RESTTranslatedTopicV1 topic = getRESTClient().deleteJSONTranslatedTopic(id, "");
            return topic != null;
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> createTranslatedTopics(CollectionWrapper<TranslatedTopicWrapper> topics) {
        try {
            final RESTTranslatedTopicCollectionV1 unwrappedTopics = ((RESTTranslatedTopicCollectionV1Wrapper) topics).unwrap();

            final String expandString = getExpansionString(RESTv1Constants.TRANSLATEDTOPICS_EXPANSION_NAME, DEFAULT_EXPAND_MAP_WITH_TOPIC);
            final RESTTranslatedTopicCollectionV1 createdTopics = getRESTClient().createJSONTranslatedTopics(expandString, unwrappedTopics);
            if (createdTopics != null) {
                getRESTEntityCache().add(createdTopics, false);
                return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                        .providerFactory(getProviderFactory())
                        .collection(createdTopics)
                        .expandedEntityMethods(DEFAULT_METHOD_MAP_WITH_TOPIC)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> updateTranslatedTopics(CollectionWrapper<TranslatedTopicWrapper> topics) {
        try {
            final RESTTranslatedTopicCollectionV1 unwrappedTopics = ((RESTTranslatedTopicCollectionV1Wrapper) topics).unwrap();

            final String expandString = getExpansionString(RESTv1Constants.TRANSLATEDTOPICS_EXPANSION_NAME, DEFAULT_EXPAND_MAP_WITH_TOPIC);
            final RESTTranslatedTopicCollectionV1 updatedTopics = getRESTClient().updateJSONTranslatedTopics(expandString, unwrappedTopics);
            if (updatedTopics != null) {
                // Expire the old cached data
                for (final RESTTranslatedTopicV1 topic : updatedTopics.returnItems()) {
                    getRESTEntityCache().expire(RESTTranslatedTopicV1.class, topic.getId());
                }
                // Add the new data to the cache
                getRESTEntityCache().add(updatedTopics, false);
                return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                        .providerFactory(getProviderFactory())
                        .collection(updatedTopics)
                        .expandedEntityMethods(DEFAULT_METHOD_MAP_WITH_TOPIC)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public boolean deleteTranslatedTopics(final List<Integer> topicIds) {
        try {
            final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
            final PathSegment path = new PathSegmentImpl(pathString, false);
            final RESTTranslatedTopicCollectionV1 topics = getRESTClient().deleteJSONTranslatedTopics(path, "");
            return topics != null;
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public TranslatedTopicWrapper newTranslatedTopic() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTTranslatedTopicV1())
                .newEntity()
                .build();
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> newTranslatedTopicCollection() {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTTranslatedTopicCollectionV1())
                .build();
    }
}
