package org.jboss.pressgang.ccms.contentspec.provider;

import javax.ws.rs.core.PathSegment;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTTranslatedTopicV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.RESTTranslatedTopicCollectionV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedTopicProvider extends RESTDataProvider implements TranslatedTopicProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedTopicProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTranslatedTopicProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        this.client = restManager.getRESTClient();
        this.entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic(int id) {
        return getTranslatedTopic(id, null);
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic(int id, final Integer revision) {
        try {
            final RESTTranslatedTopicV1 topic;
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);
            } else {
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.TOPIC_NAME));

                expand.setBranches(CollectionUtilities.toArrayList(expandTags));

                final String expandString = mapper.writeValueAsString(expand);

                if (revision == null) {
                    topic = client.getJSONTranslatedTopic(id, expandString);
                    entityCache.add(topic);
                } else {
                    topic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
                    entityCache.add(topic, revision);
                }
            }
            return getWrapperFactory().create(topic, revision != null);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TagWrapper> getTranslatedTopicTags(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getTags() != null) {
                    return getWrapperFactory().createCollection(topic.getTags(), RESTTagV1.class, revision != null);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.TAGS_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
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
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<PropertyTagInTopicWrapper> getTranslatedTopicProperties(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getProperties() != null) {
                    return getWrapperFactory().createCollection(topic.getProperties(), RESTAssignedPropertyTagV1.class, revision != null,
                            PropertyTagInTopicWrapper.class);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.PROPERTIES_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
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

            return getWrapperFactory().createCollection(topic.getProperties(), RESTAssignedPropertyTagV1.class, revision != null,
                    PropertyTagInTopicWrapper.class);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicOutgoingRelationships(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getOutgoingRelationships() != null) {
                    return getWrapperFactory().createCollection(topic.getOutgoingRelationships(), RESTTranslatedTopicV1.class,
                            revision != null);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandOutgoing = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.OUTGOING_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandOutgoing));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
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

            return getWrapperFactory().createCollection(topic.getOutgoingRelationships(), RESTTranslatedTopicV1.class, revision != null);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicIncomingRelationships(int id, Integer revision) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getIncomingRelationships() != null) {
                    return getWrapperFactory().createCollection(topic.getIncomingRelationships(), RESTTranslatedTopicV1.class,
                            revision != null);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandIncoming = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.INCOMING_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandIncoming));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
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

            return getWrapperFactory().createCollection(topic.getIncomingRelationships(), RESTTranslatedTopicV1.class, revision != null);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTranslatedTopicSourceUrls(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
    }

    public CollectionWrapper<TopicSourceURLWrapper> getTranslatedTopicSourceUrls(int id, Integer revision,
            RESTBaseTopicV1<?, ?, ?> parent) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getSourceUrls_OTM() != null) {
                    return getWrapperFactory().createCollection(topic.getSourceUrls_OTM(), RESTTopicSourceUrlV1.class, revision != null,
                            parent);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.SOURCE_URLS_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
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
            log.error("", e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings(int id, Integer revision,
            RESTTranslatedTopicV1 parent) {
        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                topic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (topic.getTranslatedTopicStrings_OTM() != null) {
                    final CollectionWrapper<TranslatedTopicStringWrapper> collection = getWrapperFactory().createCollection(
                            topic.getTranslatedTopicStrings_OTM(), RESTTranslatedTopicStringV1.class, revision != null, parent);
                    return (UpdateableCollectionWrapper<TranslatedTopicStringWrapper>) collection;
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.TRANSLATEDTOPICSTRING_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTags));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTopic;
            if (revision == null) {
                tempTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                if (revision == null) {
                    entityCache.add(topic);
                } else {
                    entityCache.add(topic, revision);
                }
            } else {
                topic.setTranslatedTopicStrings_OTM(tempTopic.getTranslatedTopicStrings_OTM());
            }

            final CollectionWrapper<TranslatedTopicStringWrapper> collection = getWrapperFactory().createCollection(
                    topic.getTranslatedTopicStrings_OTM(), RESTTranslatedTopicStringV1.class, revision != null, parent);
            return (UpdateableCollectionWrapper<TranslatedTopicStringWrapper>) collection;
        } catch (Exception e) {
            log.error("Unable to retrieve the Translated Topic Strings for Translated Topic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopicRevisions(int id, final Integer revision) {
        try {
            RESTTranslatedTopicV1 translatedTopic = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id, revision)) {
                translatedTopic = entityCache.get(RESTTranslatedTopicV1.class, id, revision);

                if (translatedTopic.getRevisions() != null) {
                    return getWrapperFactory().createCollection(translatedTopic.getRevisions(), RESTTranslatedTopicV1.class, true);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTranslatedTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.REVISIONS_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandTranslatedTopics));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTTranslatedTopicV1 tempTranslatedTopic;
            if (revision == null) {
                tempTranslatedTopic = client.getJSONTranslatedTopic(id, expandString);
            } else {
                tempTranslatedTopic = client.getJSONTranslatedTopicRevision(id, revision, expandString);
            }

            if (translatedTopic == null) {
                translatedTopic = tempTranslatedTopic;
                if (revision == null) {
                    entityCache.add(translatedTopic);
                } else {
                    entityCache.add(translatedTopic, revision);
                }
            } else {
                translatedTopic.setRevisions(tempTranslatedTopic.getRevisions());
            }

            return getWrapperFactory().createCollection(translatedTopic.getRevisions(), RESTTranslatedTopicV1.class, true);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public TranslatedTopicWrapper createTranslatedTopic(final TranslatedTopicWrapper topic) throws Exception {
        final RESTTranslatedTopicV1 updatedTopic = client.createJSONTranslatedTopic("", ((RESTTranslatedTopicV1Wrapper) topic).unwrap());
        if (updatedTopic != null) {
            entityCache.add(updatedTopic);
            return getWrapperFactory().create(updatedTopic, false);
        } else {
            return null;
        }
    }

    @Override
    public TranslatedTopicWrapper updateTranslatedTopic(TranslatedTopicWrapper topic) throws Exception {
        final RESTTranslatedTopicV1 updatedTopic = client.updateJSONTranslatedTopic("", ((RESTTranslatedTopicV1Wrapper) topic).unwrap());
        if (updatedTopic != null) {
            entityCache.expire(RESTTranslatedTopicV1.class, topic.getId());
            entityCache.add(updatedTopic);
            return getWrapperFactory().create(updatedTopic, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteTranslatedTopic(Integer id) throws Exception {
        final RESTTranslatedTopicV1 topic = client.deleteJSONTranslatedTopic(id, "");
        return topic != null;
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> createTranslatedTopics(
            CollectionWrapper<TranslatedTopicWrapper> topics) throws Exception {
        final RESTTranslatedTopicCollectionV1 unwrappedTopics = ((RESTTranslatedTopicCollectionV1Wrapper) topics).unwrap();

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTv1Constants.TRANSLATEDTOPICS_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandTopics));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTranslatedTopicCollectionV1 updatedTopics = client.createJSONTranslatedTopics(expandString, unwrappedTopics);
        if (updatedTopics != null) {
            entityCache.add(updatedTopics, false);
            return getWrapperFactory().createCollection(updatedTopics, RESTTranslatedTopicV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> updateTranslatedTopics(
            CollectionWrapper<TranslatedTopicWrapper> topics) throws Exception {
        final RESTTranslatedTopicCollectionV1 unwrappedTopics = ((RESTTranslatedTopicCollectionV1Wrapper) topics).unwrap();

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTv1Constants.TRANSLATEDTOPICS_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandTopics));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTranslatedTopicCollectionV1 updatedTopics = client.updateJSONTranslatedTopics(expandString, unwrappedTopics);
        if (updatedTopics != null) {
            // Expire the old cached data
            for (final RESTTranslatedTopicV1 topic : unwrappedTopics.returnItems()) {
                entityCache.expire(RESTTranslatedTopicV1.class, topic.getId());
            }
            // Add the new data to the cache
            entityCache.add(updatedTopics, false);
            return getWrapperFactory().createCollection(updatedTopics, RESTTranslatedTopicV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteTranslatedTopics(final List<Integer> topicIds) throws Exception {
        final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
        final PathSegment path = new PathSegmentImpl(pathString, false);
        final RESTTranslatedTopicCollectionV1 topics = client.deleteJSONTranslatedTopics(path, "");
        return topics != null;
    }

    @Override
    public TranslatedTopicWrapper newTranslatedTopic() {
        return getWrapperFactory().create(new RESTTopicV1(), false);
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> newTranslatedTopicCollection() {
        return getWrapperFactory().createCollection(new RESTTranslatedTopicCollectionV1(), RESTTranslatedTopicV1.class, false);
    }
}
