package org.jboss.pressgang.ccms.contentspec.provider;

import java.io.IOException;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTTopicSourceUrlCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.exceptions.InternalProcessingException;
import org.jboss.pressgang.ccms.rest.v1.exceptions.InvalidParameterException;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTopicSourceURLProvider extends RESTDataProvider implements TopicSourceURLProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTopicSourceURLProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTopicSourceURLProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        this.client = restManager.getRESTClient();
        this.entityCache = restManager.getRESTEntityCache();
    }

    public TopicSourceURLWrapper getTopicSourceUrl(int id, final Integer revision, final RESTBaseTopicV1<?, ?, ?> parent) {
        try {
            if (entityCache.containsKeyValue(RESTTopicSourceUrlV1.class, id, revision)) {
                return getWrapperFactory().create(entityCache.get(RESTTopicSourceUrlV1.class, id, revision), revision != null);
            } else {
                final RESTTopicSourceUrlCollectionV1 topicSourceURLs = parent.getSourceUrls_OTM();

                final List<RESTTopicSourceUrlV1> topicSourceURLItems = topicSourceURLs.returnItems();
                for (final RESTTopicSourceUrlV1 sourceURL : topicSourceURLItems) {
                    if (sourceURL.getId() == id && (revision == null || sourceURL.getRevision().equals(revision))) {
                        return getWrapperFactory().create(sourceURL, revision != null);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        final RESTTopicSourceUrlV1 topicSourceURL = (RESTTopicSourceUrlV1) getTopicSourceUrl(id, revision, topic).unwrap();
        if (topicSourceURL.getRevisions() != null) {
            return getWrapperFactory().createCollection(topicSourceURL.getRevisions(), RESTTopicSourceUrlV1.class, true, topic);
        } else {
            try {
                final RESTTopicSourceUrlCollectionV1 sourceUrlRevisions;
                if (topic instanceof RESTTranslatedTopicV1) {
                    sourceUrlRevisions = getTranslatedTopicSourceUrlRevisions(id, revision, topic);
                } else {
                    sourceUrlRevisions = getTopicSourceUrlRevisions(id, revision, topic);
                }

                return getWrapperFactory().createCollection(sourceUrlRevisions, RESTTopicSourceUrlV1.class, true, topic);
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
    }

    @SuppressWarnings("unchecked")
    protected RESTTopicSourceUrlCollectionV1 getTopicSourceUrlRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) throws IOException, InvalidParameterException, InternalProcessingException {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTopicV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        RESTTopicV1 topic = null;
        // Check the cache first
        if (entityCache.containsKeyValue(RESTTopicV1.class, topicId, topicRevision)) {
            topic = entityCache.get(RESTTopicV1.class, topicId, topicRevision);
        }

                /* We need to expand the all the items in the topic collection */
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandSourceURLs = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.SOURCE_URLS_NAME));
        final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicSourceUrlV1.REVISIONS_NAME));

        expandSourceURLs.setBranches(CollectionUtilities.toArrayList(expandRevisions));
        expand.setBranches(CollectionUtilities.toArrayList(expandSourceURLs));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTopicV1 tempTopic;
        if (topicRevision == null) {
            tempTopic = client.getJSONTopic(topicId, expandString);
        } else {
            tempTopic = client.getJSONTopicRevision(topicId, topicRevision, expandString);
        }

        if (topic == null) {
            topic = tempTopic;
            if (topicRevision == null) {
                entityCache.add(topic);
            } else {
                entityCache.add(topic, topicRevision);
            }
        } else if (topic.getSourceUrls_OTM() == null) {
            topic.setSourceUrls_OTM(tempTopic.getSourceUrls_OTM());
        } else {
            // Iterate over the current and old source urls and add any missing objects.
            final List<RESTTopicSourceUrlV1> sourceURLs = topic.getSourceUrls_OTM().returnItems();
            final List<RESTTopicSourceUrlV1> newSourceURLs = tempTopic.getSourceUrls_OTM().returnItems();
            for (final RESTTopicSourceUrlV1 newSourceURL : newSourceURLs) {
                boolean found = false;

                for (final RESTTopicSourceUrlV1 sourceURL : sourceURLs) {
                    if (sourceURL.getId().equals(newSourceURL.getId())) {
                        sourceURL.setRevisions(newSourceURL.getRevisions());

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    topic.getSourceUrls_OTM().addItem(newSourceURL);
                }
            }
        }

        for (final RESTTopicSourceUrlCollectionItemV1 sourceURLItem : topic.getSourceUrls_OTM().getItems()) {
            final RESTTopicSourceUrlV1 sourceURL = sourceURLItem.getItem();

            if (sourceURL.getId() == id && (revision == null || sourceURL.getRevision().equals(revision))) {
                return sourceURL.getRevisions();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    protected RESTTopicSourceUrlCollectionV1 getTranslatedTopicSourceUrlRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) throws IOException, InvalidParameterException, InternalProcessingException {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        RESTTranslatedTopicV1 topic = null;
        // Check the cache first
        if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, topicId, topicRevision)) {
            topic = entityCache.get(RESTTranslatedTopicV1.class, topicId, topicRevision);
        }

                /* We need to expand the all the items in the topic collection */
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandSourceURLs = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.SOURCE_URLS_NAME));
        final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicSourceUrlV1.REVISIONS_NAME));

        expandSourceURLs.setBranches(CollectionUtilities.toArrayList(expandRevisions));
        expand.setBranches(CollectionUtilities.toArrayList(expandSourceURLs));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTranslatedTopicV1 tempTranslatedTopic;
        if (topicRevision == null) {
            tempTranslatedTopic = client.getJSONTranslatedTopic(topicId, expandString);
        } else {
            tempTranslatedTopic = client.getJSONTranslatedTopicRevision(topicId, topicRevision, expandString);
        }

        if (topic == null) {
            topic = tempTranslatedTopic;
            if (topicRevision == null) {
                entityCache.add(topic);
            } else {
                entityCache.add(topic, topicRevision);
            }
        } else if (topic.getSourceUrls_OTM() == null) {
            topic.setSourceUrls_OTM(tempTranslatedTopic.getSourceUrls_OTM());
        } else {
            // Iterate over the current and old source urls and add any missing objects.
            final List<RESTTopicSourceUrlV1> sourceURLs = topic.getSourceUrls_OTM().returnItems();
            final List<RESTTopicSourceUrlV1> newSourceURLs = tempTranslatedTopic.getSourceUrls_OTM().returnItems();
            for (final RESTTopicSourceUrlV1 newSourceURL : newSourceURLs) {
                boolean found = false;

                for (final RESTTopicSourceUrlV1 sourceURL : sourceURLs) {
                    if (sourceURL.getId().equals(newSourceURL.getId())) {
                        sourceURL.setRevisions(newSourceURL.getRevisions());

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    topic.getSourceUrls_OTM().addItem(newSourceURL);
                }
            }
        }

        for (final RESTTopicSourceUrlCollectionItemV1 sourceURLItem : topic.getSourceUrls_OTM().getItems()) {
            final RESTTopicSourceUrlV1 sourceURL = sourceURLItem.getItem();

            if (sourceURL.getId() == id && (revision == null || sourceURL.getRevision().equals(revision))) {
                return sourceURL.getRevisions();
            }
        }

        return null;
    }

    @Override
    public TopicSourceURLWrapper newTopicSourceURL(final TopicWrapper topic) {
        return getWrapperFactory().create(new RESTTopicSourceUrlV1(), false, (RESTBaseTopicV1<?, ?, ?>) topic.unwrap());
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> newTopicSourceURLCollection(final TopicWrapper topic) {
        return getWrapperFactory().createCollection(new RESTTopicSourceUrlCollectionV1(), RESTTopicSourceUrlV1.class, false,
                (RESTBaseTopicV1<?, ?, ?>) topic.unwrap());
    }
}