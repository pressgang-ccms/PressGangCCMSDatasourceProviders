package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTTranslatedCSNodeV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.items.RESTTranslatedCSNodeStringCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedCSNodeStringProvider extends RESTDataProvider implements TranslatedCSNodeStringProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedCSNodeProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTTranslatedCSNodeStringProvider(final RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    public RESTTranslatedCSNodeStringV1 getRESTTranslatedCSNodeString(int id, final Integer revision, final RESTTranslatedCSNodeV1 parent) {
        try {
            if (entityCache.containsKeyValue(RESTTranslatedCSNodeStringV1.class, id, revision)) {
                return entityCache.get(RESTTranslatedCSNodeStringV1.class, id, revision);
            } else {
                final RESTTranslatedCSNodeStringCollectionV1 translatedTopicStrings = parent.getTranslatedNodeStrings_OTM();

                final List<RESTTranslatedCSNodeStringV1> translatedTopicStringItems = translatedTopicStrings.returnItems();
                for (final RESTTranslatedCSNodeStringV1 translatedTopicString : translatedTopicStringItems) {
                    if (translatedTopicString.getId() == id && (revision == null || translatedTopicString.getRevision().equals(revision))) {
                        return translatedTopicString;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public TranslatedCSNodeStringWrapper getTranslatedCSNodeString(int id, final Integer revision, final RESTTranslatedCSNodeV1 parent) {
        return getWrapperFactory().create(getRESTTranslatedCSNodeString(id, revision, parent), revision != null, parent);
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStringRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public RESTTranslatedCSNodeStringCollectionV1 getRESTTranslatedCSNodeStringRevisions(int id, final Integer revision,
            final RESTTranslatedCSNodeV1 parent) {
        final RESTTranslatedCSNodeV1ProxyHandler proxyHandler = (RESTTranslatedCSNodeV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer translatedTopicId = parent.getId();
        final Integer translatedTopicRevision = proxyHandler.getEntityRevision();

        final RESTTranslatedCSNodeStringV1 translatedTopicString = getRESTTranslatedCSNodeString(id, revision, parent);
        if (translatedTopicString == null) {
            return null;
        } else if (translatedTopicString.getRevisions() != null) {
            return translatedTopicString.getRevisions();
        } else {
            try {
                RESTTranslatedCSNodeV1 translatedTopic = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTTranslatedCSNodeV1.class, translatedTopicId, translatedTopicRevision)) {
                    translatedTopic = entityCache.get(RESTTranslatedCSNodeV1.class, translatedTopicId, translatedTopicRevision);
                }

                /* We need to expand the all the items in the topic collection */
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandCSTranslatedNodeStrings = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTTranslatedCSNodeV1.TRANSLATED_STRING_NAME));
                final ExpandDataTrunk expandCSTranslatedNodeData = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTTranslatedCSNodeStringV1.REVISIONS_NAME));

                expandCSTranslatedNodeStrings.setBranches(CollectionUtilities.toArrayList(expandCSTranslatedNodeData));
                expand.setBranches(CollectionUtilities.toArrayList(expandCSTranslatedNodeStrings));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTTranslatedCSNodeV1 tempCSTranslatedNode;
                if (translatedTopicRevision == null) {
                    tempCSTranslatedNode = client.getJSONTranslatedContentSpecNode(translatedTopicId, expandString);
                } else {
                    tempCSTranslatedNode = client.getJSONTranslatedContentSpecNodeRevision(translatedTopicId, translatedTopicRevision,
                            expandString);
                }

                if (translatedTopic == null) {
                    translatedTopic = tempCSTranslatedNode;
                    if (translatedTopicRevision == null) {
                        entityCache.add(translatedTopic);
                    } else {
                        entityCache.add(translatedTopic, translatedTopicRevision);
                    }
                } else if (translatedTopic.getTranslatedNodeStrings_OTM() == null) {
                    translatedTopic.setTranslatedNodeStrings_OTM(tempCSTranslatedNode.getTranslatedNodeStrings_OTM());
                } else {
                    /*
                     * Iterate over the current and old language translated nodes and add any missing language translated nodes. Also add
                     * the translated node data to any language translated nodes that don't have it set.
                     */
                    final List<RESTTranslatedCSNodeStringV1> langCSTranslatedNodes = translatedTopic.getTranslatedNodeStrings_OTM()
                            .returnItems();
                    final List<RESTTranslatedCSNodeStringV1> newLangCSTranslatedNodes = tempCSTranslatedNode.getTranslatedNodeStrings_OTM
                            ().returnItems();
                    for (final RESTTranslatedCSNodeStringV1 newLangCSTranslatedNode : newLangCSTranslatedNodes) {
                        boolean found = false;

                        for (final RESTTranslatedCSNodeStringV1 langCSTranslatedNode : langCSTranslatedNodes) {
                            if (langCSTranslatedNode.getId().equals(newLangCSTranslatedNode.getId())) {
                                langCSTranslatedNode.setRevisions(newLangCSTranslatedNode.getRevisions());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            translatedTopic.getTranslatedNodeStrings_OTM().addItem(newLangCSTranslatedNode);
                        }
                    }
                }

                for (final RESTTranslatedCSNodeStringCollectionItemV1 translatedTopicItem : translatedTopic.getTranslatedNodeStrings_OTM
                        ().getItems()) {
                    final RESTTranslatedCSNodeStringV1 langCSTranslatedNode = translatedTopicItem.getItem();

                    if (langCSTranslatedNode.getId() == id && (revision == null || langCSTranslatedNode.getRevision().equals(revision))) {
                        return translatedTopicString.getRevisions();
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    public CollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStringRevisions(int id, final Integer revision,
            final RESTTranslatedCSNodeV1 parent) {
        return getWrapperFactory().createCollection(getRESTTranslatedCSNodeStringRevisions(id, revision, parent),
                RESTTranslatedCSNodeStringV1.class, revision != null, parent);
    }

    @Override
    public TranslatedCSNodeStringWrapper newTranslatedCSNodeString(final TranslatedCSNodeWrapper translatedNode) {
        return getWrapperFactory().create(new RESTTranslatedCSNodeStringV1(), false,
                translatedNode == null ? null : (RESTTranslatedCSNodeV1) translatedNode.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection(
            final TranslatedCSNodeWrapper translatedNode) {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                new RESTTranslatedCSNodeStringCollectionV1(), RESTTranslatedCSNodeStringV1.class, false,
                translatedNode == null ? null : (RESTTranslatedCSNodeV1) translatedNode.unwrap());
        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }
}
