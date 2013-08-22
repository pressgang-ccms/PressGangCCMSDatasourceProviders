package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTTranslatedTopicV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTTranslatedTopicStringCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedTopicStringProvider extends RESTDataProvider implements TranslatedTopicStringProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedTopicProvider.class);

    protected RESTTranslatedTopicStringProvider(final RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTTranslatedTopicV1 loadTranslatedTopic(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTranslatedTopic(id, expandString);
        } else {
            return getRESTClient().getJSONTranslatedTopicRevision(id, revision, expandString);
        }
    }

    public RESTTranslatedTopicStringV1 getRESTTranslatedTopicString(int id, final Integer revision, final RESTTranslatedTopicV1 parent) {
        try {
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicStringV1.class, id, revision)) {
                return getRESTEntityCache().get(RESTTranslatedTopicStringV1.class, id, revision);
            } else {
                final RESTTranslatedTopicStringCollectionV1 translatedTopicStrings = parent.getTranslatedTopicStrings_OTM();

                final List<RESTTranslatedTopicStringV1> translatedTopicStringItems = translatedTopicStrings.returnItems();
                for (final RESTTranslatedTopicStringV1 translatedTopicString : translatedTopicStringItems) {
                    if (translatedTopicString.getId().equals(id) && (revision == null || translatedTopicString.getRevision().equals(
                            revision))) {
                        return translatedTopicString;
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve Translated Topic String " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public TranslatedTopicStringWrapper getTranslatedTopicString(int id, final Integer revision, final RESTTranslatedTopicV1 parent) {
        return getWrapperFactory().create(getRESTTranslatedTopicString(id, revision, parent), revision != null, parent);
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStringRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public RESTTranslatedTopicStringCollectionV1 getRESTTranslatedTopicStringRevisions(int id, final Integer revision,
            final RESTTranslatedTopicV1 parent) {
        try {
            final RESTTranslatedTopicV1ProxyHandler proxyHandler = (RESTTranslatedTopicV1ProxyHandler) ((ProxyObject) parent).getHandler();
            final Integer translatedTopicId = parent.getId();
            final Integer translatedTopicRevision = proxyHandler.getEntityRevision();

            final RESTTranslatedTopicStringV1 translatedTopicString = getRESTTranslatedTopicString(id, revision, parent);
            if (translatedTopicString == null) {
                throw new NotFoundException();
            } else if (translatedTopicString.getRevisions() != null) {
                return translatedTopicString.getRevisions();
            } else {
                RESTTranslatedTopicV1 translatedTopic = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, translatedTopicId, translatedTopicRevision)) {
                    translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, translatedTopicId, translatedTopicRevision);
                }

                // We need to expand the all the translated topic string revisions in the translated topic
                final String expandString = getExpansionString(RESTTranslatedTopicV1.TRANSLATEDTOPICSTRING_NAME,
                        RESTTranslatedTopicStringV1.REVISIONS_NAME);

                // Load the translated topic from the REST Interface
                final RESTTranslatedTopicV1 tempTranslatedTopic = loadTranslatedTopic(translatedTopicId, translatedTopicRevision,
                        expandString);

                if (translatedTopic == null) {
                    translatedTopic = tempTranslatedTopic;
                    getRESTEntityCache().add(translatedTopic, translatedTopicRevision);
                } else if (translatedTopic.getTranslatedTopicStrings_OTM() == null) {
                    translatedTopic.setTranslatedTopicStrings_OTM(tempTranslatedTopic.getTranslatedTopicStrings_OTM());
                } else {
                    /*
                     * Iterate over the current and old language translatedTopics and add any missing language translatedTopics. Also add
                      * the translatedTopic
                     * data to any language translatedTopics that don't have it set.
                     */
                    final List<RESTTranslatedTopicStringV1> langTranslatedTopics = translatedTopic.getTranslatedTopicStrings_OTM()
                            .returnItems();
                    final List<RESTTranslatedTopicStringV1> newLangTranslatedTopics = tempTranslatedTopic.getTranslatedTopicStrings_OTM()
                            .returnItems();
                    for (final RESTTranslatedTopicStringV1 newLangTranslatedTopic : newLangTranslatedTopics) {
                        boolean found = false;

                        for (final RESTTranslatedTopicStringV1 langTranslatedTopic : langTranslatedTopics) {
                            if (langTranslatedTopic.getId().equals(newLangTranslatedTopic.getId())) {
                                langTranslatedTopic.setRevisions(newLangTranslatedTopic.getRevisions());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            translatedTopic.getTranslatedTopicStrings_OTM().addItem(newLangTranslatedTopic);
                        }
                    }
                }

                for (final RESTTranslatedTopicStringCollectionItemV1 translatedTopicItem : translatedTopic.getTranslatedTopicStrings_OTM
                        ().getItems()) {
                    final RESTTranslatedTopicStringV1 langTranslatedTopic = translatedTopicItem.getItem();

                    if (langTranslatedTopic.getId() == id && (revision == null || langTranslatedTopic.getRevision().equals(revision))) {
                        return translatedTopicString.getRevisions();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Translated Topic String " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStringRevisions(int id, final Integer revision,
            final RESTTranslatedTopicV1 parent) {
        return getWrapperFactory().createCollection(getRESTTranslatedTopicStringRevisions(id, revision, parent),
                RESTTranslatedTopicStringV1.class, revision != null, parent);
    }

    @Override
    public TranslatedTopicStringWrapper newTranslatedTopicString(final TranslatedTopicWrapper translatedTopic) {
        return getWrapperFactory().create(new RESTTranslatedTopicStringV1(), false,
                translatedTopic == null ? null : (RESTTranslatedTopicV1) translatedTopic.unwrap(), true);
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> newTranslatedTopicStringCollection(
            final TranslatedTopicWrapper translatedTopic) {
        return getWrapperFactory().createCollection(new RESTTranslatedTopicStringCollectionV1(), RESTTranslatedTopicStringV1.class, false,
                translatedTopic == null ? null : (RESTTranslatedTopicV1) translatedTopic.unwrap());
    }
}
