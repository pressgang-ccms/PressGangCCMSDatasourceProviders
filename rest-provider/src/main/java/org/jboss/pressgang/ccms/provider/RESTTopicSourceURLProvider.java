package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTTopicSourceUrlCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTopicSourceURLProvider extends RESTDataProvider implements TopicSourceURLProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTopicSourceURLProvider.class);

    protected RESTTopicSourceURLProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    public RESTTopicSourceUrlV1 getRESTTopicSourceUrl(int id, final Integer revision, final RESTBaseTopicV1<?, ?, ?> parent) {
        try {
            if (getRESTEntityCache().containsKeyValue(RESTTopicSourceUrlV1.class, id, revision)) {
                return getRESTEntityCache().get(RESTTopicSourceUrlV1.class, id, revision);
            } else {
                final RESTTopicSourceUrlCollectionV1 topicSourceURLs = parent.getSourceUrls_OTM();

                final List<RESTTopicSourceUrlV1> topicSourceURLItems = topicSourceURLs.returnItems();
                for (final RESTTopicSourceUrlV1 sourceURL : topicSourceURLItems) {
                    if (sourceURL.getId().equals(id) && (revision == null || sourceURL.getRevision().equals(revision))) {
                        return sourceURL;
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve Topic Source URL " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public TopicSourceURLWrapper getTopicSourceUrl(int id, final Integer revision, final RESTBaseTopicV1<?, ?, ?> parent) {
        return getWrapperFactory().create(getRESTTopicSourceUrl(id, revision, parent), revision != null);
    }

    public RESTTopicSourceUrlCollectionV1 getRESTTopicSourceURLRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        final RESTTopicSourceUrlV1 topicSourceURL = getRESTTopicSourceUrl(id, revision, topic);
        if (topicSourceURL == null) {
            throw new NotFoundException();
        } else if (topicSourceURL.getRevisions() != null) {
            return topicSourceURL.getRevisions();
        } else {
            final RESTTopicSourceUrlCollectionV1 sourceUrlRevisions;
            if (topic instanceof RESTTranslatedTopicV1) {
                sourceUrlRevisions = getRESTTranslatedTopicSourceUrlRevisions(id, revision, topic);
            } else {
                sourceUrlRevisions = getRESTTopicSourceUrlRevisions(id, revision, topic);
            }

            return sourceUrlRevisions;
        }
    }

    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        return getWrapperFactory().createCollection(getRESTTopicSourceURLRevisions(id, revision, topic), RESTTopicSourceUrlV1.class, true,
                topic);
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
    }

    @SuppressWarnings("unchecked")
    protected RESTTopicSourceUrlCollectionV1 getRESTTopicSourceUrlRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTopicV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, topicId, topicRevision)) {
                topic = getRESTEntityCache().get(RESTTopicV1.class, topicId, topicRevision);
            }

            // We need to expand the all the topic source url revisions in the topic
            final String expandString = getExpansionString(RESTTopicV1.SOURCE_URLS_NAME, RESTTopicSourceUrlV1.REVISIONS_NAME);

            // Load the topic from the REST Interface
            final RESTTopicV1 tempTopic;
            if (topicRevision == null) {
                tempTopic = getRESTClient().getJSONTopic(topicId, expandString);
            } else {
                tempTopic = getRESTClient().getJSONTopicRevision(topicId, topicRevision, expandString);
            }

            if (topic == null) {
                topic = tempTopic;
                getRESTEntityCache().add(topic, topicRevision);
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

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve Topic Source URLs for Topic " + topicId + (topicRevision == null ? "" : (", " +
                    "Revision " + topicRevision)), e);
            throw handleException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected RESTTopicSourceUrlCollectionV1 getRESTTranslatedTopicSourceUrlRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTTranslatedTopicV1 topic = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, topicId, topicRevision)) {
                topic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, topicId, topicRevision);
            }

            // We need to expand the all the source url revisions in the translated topic
            final String expandString = getExpansionString(RESTTranslatedTopicV1.SOURCE_URLS_NAME, RESTTopicSourceUrlV1.REVISIONS_NAME);

            // Load the translated topic from the REST Interface
            final RESTTranslatedTopicV1 tempTranslatedTopic;
            if (topicRevision == null) {
                tempTranslatedTopic = getRESTClient().getJSONTranslatedTopic(topicId, expandString);
            } else {
                tempTranslatedTopic = getRESTClient().getJSONTranslatedTopicRevision(topicId, topicRevision, expandString);
            }

            if (topic == null) {
                topic = tempTranslatedTopic;
                getRESTEntityCache().add(topic, topicRevision);
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

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve Topic Source URLs for Topic " + topicId + (topicRevision == null ? "" : (", " +
                    "Revision " + topicRevision)), e);
            throw handleException(e);
        }
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
