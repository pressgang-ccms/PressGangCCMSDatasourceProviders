package org.jboss.pressgang.ccms.contentspec.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTTranslatedTopicV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTTranslatedTopicStringCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedTopicStringProvider extends RESTDataProvider implements TranslatedTopicStringProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedTopicProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTranslatedTopicStringProvider(final RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    public TranslatedTopicStringWrapper getTranslatedTopicString(int id, final Integer revision, final RESTTranslatedTopicV1 parent) {
        try {
            if (entityCache.containsKeyValue(RESTTranslatedTopicStringV1.class, id, revision)) {
                return getWrapperFactory().create(entityCache.get(RESTTranslatedTopicStringV1.class, id, revision), revision != null,
                        parent);
            } else {
                final RESTTranslatedTopicStringCollectionV1 translatedTopicStrings = parent.getTranslatedTopicStrings_OTM();

                final List<RESTTranslatedTopicStringV1> translatedTopicStringItems = translatedTopicStrings.returnItems();
                for (final RESTTranslatedTopicStringV1 translatedTopicString : translatedTopicStringItems) {
                    if (translatedTopicString.getId() == id && (revision == null || translatedTopicString.getRevision().equals(revision))) {
                        return getWrapperFactory().create(translatedTopicString, revision != null, parent);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStringRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStringRevisions(int id, final Integer revision,
            final RESTTranslatedTopicV1 parent) {
        final RESTTranslatedTopicV1ProxyHandler proxyHandler = (RESTTranslatedTopicV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer translatedTopicId = parent.getId();
        final Integer translatedTopicRevision = proxyHandler.getEntityRevision();

        final RESTTranslatedTopicStringV1 translatedTopicString = (RESTTranslatedTopicStringV1) getTranslatedTopicString(id, revision,
                parent).unwrap();
        if (translatedTopicString.getRevisions() != null) {
            return getWrapperFactory().createCollection(translatedTopicString.getRevisions(), RESTTranslatedTopicStringV1.class,
                    revision != null, parent);
        } else {
            try {
                RESTTranslatedTopicV1 translatedTopic = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, translatedTopicId, translatedTopicRevision)) {
                    translatedTopic = entityCache.get(RESTTranslatedTopicV1.class, translatedTopicId, translatedTopicRevision);
                }

                /* We need to expand the all the items in the topic collection */
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandTranslatedTopicStrings = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTTranslatedTopicV1.TRANSLATEDTOPICSTRING_NAME));
                final ExpandDataTrunk expandTranslatedTopicData = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTTranslatedTopicStringV1.REVISIONS_NAME));

                expandTranslatedTopicStrings.setBranches(CollectionUtilities.toArrayList(expandTranslatedTopicData));
                expand.setBranches(CollectionUtilities.toArrayList(expandTranslatedTopicStrings));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTTranslatedTopicV1 tempTranslatedTopic;
                if (translatedTopicRevision == null) {
                    tempTranslatedTopic = client.getJSONTranslatedTopic(translatedTopicId, expandString);
                } else {
                    tempTranslatedTopic = client.getJSONTranslatedTopicRevision(translatedTopicId, translatedTopicRevision, expandString);
                }

                if (translatedTopic == null) {
                    translatedTopic = tempTranslatedTopic;
                    if (translatedTopicRevision == null) {
                        entityCache.add(translatedTopic);
                    } else {
                        entityCache.add(translatedTopic, translatedTopicRevision);
                    }
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
                        return getWrapperFactory().createCollection(translatedTopicString.getRevisions(), RESTTranslatedTopicStringV1.class,
                                revision != null, parent);
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    @Override
    public TranslatedTopicStringWrapper newTranslatedTopicString() {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public TranslatedTopicStringWrapper newTranslatedTopicString(final RESTTranslatedTopicV1 translatedTopic) {
        return getWrapperFactory().create(new RESTTranslatedTopicStringV1(), false, translatedTopic);
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> newTranslatedTopicStringCollection() {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public CollectionWrapper<TranslatedTopicStringWrapper> newTranslatedTopicStringCollection(final RESTTranslatedTopicV1 translatedTopic) {
        return getWrapperFactory().createCollection(new RESTTranslatedTopicStringCollectionV1(), RESTTranslatedTopicStringV1.class, false,
                translatedTopic);
    }
}
