package org.jboss.pressgang.ccms.provider;

import javax.ws.rs.core.PathSegment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTopicCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.rest.v1.query.RESTTopicQueryBuilderV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTopicProvider extends RESTDataProvider implements TopicProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTopicProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTopicProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        this.client = restManager.getRESTClient();
        this.entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public TopicWrapper getTopic(int id) {
        return getTopic(id, null);
    }

    @Override
    public TopicWrapper getTopic(int id, Integer revision) {
        try {
            final RESTTopicV1 topic;
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);
            } else {
                if (revision == null) {
                    topic = client.getJSONTopic(id, "");
                    entityCache.add(topic);
                } else {
                    topic = client.getJSONTopicRevision(id, revision, "");
                    entityCache.add(topic, revision);
                }
            }
            return getWrapperFactory().create(topic, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTopicTags(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getTags() != null) {
                    return getWrapperFactory().createCollection(topic.getTags(), RESTTagV1.class, revision != null);
                }
            }

            // We need to expand the tags in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.TAGS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setTags(tempTopic.getTags());
            }

            return getWrapperFactory().createCollection(topic.getTags(), RESTTagV1.class, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Tags for topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopics(final List<Integer> ids) {
        if (ids.isEmpty()) return null;

        try {
            final RESTTopicCollectionV1 topics = new RESTTopicCollectionV1();
            final Set<Integer> queryIds = new HashSet<Integer>();

            for (final Integer id : ids) {
                if (!entityCache.containsKeyValue(RESTTopicV1.class, id)) {
                    queryIds.add(id);
                } else {
                    topics.addItem(entityCache.get(RESTTopicV1.class, id));
                }
            }

            // Get the missing topics from the REST interface
            if (!queryIds.isEmpty()) {
                final RESTTopicQueryBuilderV1 queryBuilder = new RESTTopicQueryBuilderV1();
                queryBuilder.setTopicIds(new ArrayList<Integer>(queryIds));

                // We need to expand the topic collection
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(new ExpandDataDetails("topics"));
                expand.setBranches(CollectionUtilities.toArrayList(topicsExpand));
                final String expandString = mapper.writeValueAsString(expand);

                // Load the topics from the REST Interface
                final RESTTopicCollectionV1 downloadedTopics = client.getJSONTopicsWithQuery(queryBuilder.buildQueryPath(), expandString);
                entityCache.add(downloadedTopics);

                // Transfer the downloaded data to the current topic list
                if (downloadedTopics != null && downloadedTopics.getItems() != null) {
                    final List<RESTTopicV1> items = downloadedTopics.returnItems();
                    for (final RESTTopicV1 item : items) {
                        topics.addItem(item);
                    }
                }
            }

            return getWrapperFactory().createCollection(topics, RESTTopicV1.class, false);
        } catch (Exception e) {
            log.error("Failed to retrieve all Topics for the specified ids", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(new ExpandDataDetails("topics"));

            expand.setBranches(CollectionUtilities.toArrayList(topicsExpand));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTopicCollectionV1 topics = client.getJSONTopicsWithQuery(new PathSegmentImpl(query, false), expandString);
            entityCache.add(topics);

            return getWrapperFactory().createCollection(topics, RESTTopicV1.class, false);
        } catch (Exception e) {
            log.error("Failed to retrieve Topics with a query", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTopicTranslations(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getTranslatedTopics_OTM() != null) {
                    return getWrapperFactory().createCollection(topic.getTranslatedTopics_OTM(), RESTTranslatedTopicV1.class,
                            revision != null);
                }
            }

            // We need to expand the the translated topics in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandOutgoing = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.TRANSLATEDTOPICS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandOutgoing));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST API
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setTranslatedTopics_OTM(tempTopic.getTranslatedTopics_OTM());
            }

            return getWrapperFactory().createCollection(topic.getTranslatedTopics_OTM(), RESTTranslatedTopicV1.class, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Translations for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicRevisions(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getRevisions() != null) {
                    return getWrapperFactory().createCollection(topic.getRevisions(), RESTTopicV1.class, true);
                }
            }

            // We need to expand the revisions in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTopics));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST API
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setRevisions(tempTopic.getRevisions());
            }

            return getWrapperFactory().createCollection(topic.getRevisions(), RESTTopicV1.class, true);
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getTopicProperties(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getProperties() != null) {
                    final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                            topic.getProperties(), RESTAssignedPropertyTagV1.class, revision != null, PropertyTagInTopicWrapper.class);
                    return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
                }
            }

            // We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.PROPERTIES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setProperties(tempTopic.getProperties());
            }

            final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(topic.getProperties(),
                    RESTAssignedPropertyTagV1.class, revision != null, PropertyTagInTopicWrapper.class);
            return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
        } catch (Exception e) {
            log.error("Failed to retrieve the Properties for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicOutgoingRelationships(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getOutgoingRelationships() != null) {
                    return getWrapperFactory().createCollection(topic.getOutgoingRelationships(), RESTTopicV1.class, revision != null);
                }
            }

            // We need to expand the outgoing topic relationships in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandOutgoing = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.OUTGOING_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandOutgoing));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setOutgoingRelationships(tempTopic.getOutgoingRelationships());
            }

            return getWrapperFactory().createCollection(topic.getOutgoingRelationships(), RESTTopicV1.class, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Outgoing Relationships for Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicWrapper> getTopicIncomingRelationships(int id, final Integer revision) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getIncomingRelationships() != null) {
                    return getWrapperFactory().createCollection(topic.getIncomingRelationships(), RESTTopicV1.class, revision != null);
                }
            }

            // We need to expand the incoming topic relationships in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandIncoming = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.INCOMING_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandIncoming));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setIncomingRelationships(tempTopic.getIncomingRelationships());
            }

            return getWrapperFactory().createCollection(topic.getIncomingRelationships(), RESTTopicV1.class, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Incoming Relationships for Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceUrls(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
    }

    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceUrls(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTopicV1.class, id, revision);

                if (topic.getSourceUrls_OTM() != null) {
                    return getWrapperFactory().createCollection(topic.getSourceUrls_OTM(), RESTTopicSourceUrlV1.class, revision != null,
                            parent);
                }
            }

            // We need to expand the source urls in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.SOURCE_URLS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setSourceUrls_OTM(tempTopic.getSourceUrls_OTM());
            }

            return getWrapperFactory().createCollection(topic.getSourceUrls_OTM(), RESTTopicSourceUrlV1.class, revision != null, parent);
        } catch (Exception e) {
            log.error("Failed to retrieve the Source URLs for Topic " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public TopicWrapper createTopic(final TopicWrapper topicEntity) throws Exception {
        final RESTTopicV1 topic = ((RESTTopicV1Wrapper) topicEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(topic);

        final RESTTopicV1 updatedTopic = client.createJSONTopic("", ((RESTTopicV1Wrapper) topicEntity).unwrap());
        if (updatedTopic != null) {
            entityCache.add(updatedTopic);
            return getWrapperFactory().create(updatedTopic, false);
        } else {
            return null;
        }
    }

    @Override
    public TopicWrapper updateTopic(TopicWrapper topicEntity) throws Exception {
        final RESTTopicV1 topic = ((RESTTopicV1Wrapper) topicEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(topic);

        final RESTTopicV1 updatedTopic = client.updateJSONTopic("", topic);
        if (updatedTopic != null) {
            entityCache.expire(RESTTopicV1.class, topicEntity.getId());
            entityCache.add(updatedTopic);
            return getWrapperFactory().create(updatedTopic, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteTopic(Integer id) throws Exception {
        final RESTTopicV1 topic = client.deleteJSONTopic(id, "");
        return topic != null;
    }

    @Override
    public CollectionWrapper<TopicWrapper> createTopics(CollectionWrapper<TopicWrapper> topics) throws Exception {
        final RESTTopicCollectionV1 unwrappedTopics = ((RESTTopicCollectionV1Wrapper) topics).unwrap();

        // Clean the collection to remove anything that doesn't need to be sent to the server
        cleanCollectionForSave(unwrappedTopics);

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandTopics = new ExpandDataTrunk(new ExpandDataDetails("topics"));
        expand.setBranches(CollectionUtilities.toArrayList(expandTopics));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTopicCollectionV1 updatedTopics = client.createJSONTopics(expandString, unwrappedTopics);
        if (updatedTopics != null) {
            entityCache.add(updatedTopics, false);
            return getWrapperFactory().createCollection(updatedTopics, RESTTopicV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> updateTopics(CollectionWrapper<TopicWrapper> topics) throws Exception {
        final RESTTopicCollectionV1 unwrappedTopics = ((RESTTopicCollectionV1Wrapper) topics).unwrap();

        // Clean the collection to remove anything that doesn't need to be sent to the server
        cleanCollectionForSave(unwrappedTopics);

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandTopics = new ExpandDataTrunk(new ExpandDataDetails("topics"));
        expand.setBranches(CollectionUtilities.toArrayList(expandTopics));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTopicCollectionV1 updatedTopics = client.updateJSONTopics(expandString, unwrappedTopics);
        if (updatedTopics != null) {
            // Expire the old cached data
            for (final RESTTopicV1 topic : unwrappedTopics.returnItems()) {
                entityCache.expire(RESTTopicV1.class, topic.getId());
            }
            // Add the new data to the cache
            entityCache.add(updatedTopics, false);
            return getWrapperFactory().createCollection(updatedTopics, RESTTopicV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteTopics(final List<Integer> topicIds) throws Exception {
        final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
        final PathSegment path = new PathSegmentImpl(pathString, false);
        final RESTTopicCollectionV1 topics = client.deleteJSONTopics(path, "");
        return topics != null;
    }

    @Override
    public TopicWrapper newTopic() {
        return getWrapperFactory().create(new RESTTopicV1(), false);
    }

    @Override
    public CollectionWrapper<TopicWrapper> newTopicCollection() {
        return getWrapperFactory().createCollection(new RESTTopicCollectionV1(), RESTTopicV1.class, false);
    }

}
