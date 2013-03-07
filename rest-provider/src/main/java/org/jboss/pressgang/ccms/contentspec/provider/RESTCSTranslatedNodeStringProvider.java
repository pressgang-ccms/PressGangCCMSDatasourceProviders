package org.jboss.pressgang.ccms.contentspec.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTCSTranslatedNodeV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSTranslatedNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.items.RESTCSTranslatedNodeStringCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSTranslatedNodeStringProvider extends RESTDataProvider implements CSTranslatedNodeStringProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCSTranslatedNodeProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTCSTranslatedNodeStringProvider(final RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    public CSTranslatedNodeStringWrapper getCSTranslatedNodeString(int id, final Integer revision, final RESTCSTranslatedNodeV1 parent) {
        try {
            if (entityCache.containsKeyValue(RESTCSTranslatedNodeStringV1.class, id, revision)) {
                return getWrapperFactory().create(entityCache.get(RESTCSTranslatedNodeStringV1.class, id, revision), revision != null,
                        parent);
            } else {
                final RESTCSTranslatedNodeStringCollectionV1 translatedTopicStrings = parent.getTranslatedNodeStrings_OTM();

                final List<RESTCSTranslatedNodeStringV1> translatedTopicStringItems = translatedTopicStrings.returnItems();
                for (final RESTCSTranslatedNodeStringV1 translatedTopicString : translatedTopicStringItems) {
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
    public CollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStringRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    public CollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStringRevisions(int id, final Integer revision,
            final RESTCSTranslatedNodeV1 parent) {
        final RESTCSTranslatedNodeV1ProxyHandler proxyHandler = (RESTCSTranslatedNodeV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer translatedTopicId = parent.getId();
        final Integer translatedTopicRevision = proxyHandler.getEntityRevision();

        final RESTCSTranslatedNodeStringV1 translatedTopicString = (RESTCSTranslatedNodeStringV1) getCSTranslatedNodeString(id, revision,
                parent).unwrap();
        if (translatedTopicString.getRevisions() != null) {
            return getWrapperFactory().createCollection(translatedTopicString.getRevisions(), RESTCSTranslatedNodeStringV1.class,
                    revision != null, parent);
        } else {
            try {
                RESTCSTranslatedNodeV1 translatedTopic = null;
                // Check the cache first
                if (entityCache.containsKeyValue(RESTCSTranslatedNodeV1.class, translatedTopicId, translatedTopicRevision)) {
                    translatedTopic = entityCache.get(RESTCSTranslatedNodeV1.class, translatedTopicId, translatedTopicRevision);
                }

                /* We need to expand the all the items in the topic collection */
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                final ExpandDataTrunk expandCSTranslatedNodeStrings = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTCSTranslatedNodeV1.TRANSLATED_STRING_NAME));
                final ExpandDataTrunk expandCSTranslatedNodeData = new ExpandDataTrunk(
                        new ExpandDataDetails(RESTCSTranslatedNodeStringV1.REVISIONS_NAME));

                expandCSTranslatedNodeStrings.setBranches(CollectionUtilities.toArrayList(expandCSTranslatedNodeData));
                expand.setBranches(CollectionUtilities.toArrayList(expandCSTranslatedNodeStrings));

                final String expandString = mapper.writeValueAsString(expand);

                final RESTCSTranslatedNodeV1 tempCSTranslatedNode;
                if (translatedTopicRevision == null) {
                    tempCSTranslatedNode = client.getJSONContentSpecTranslatedNode(translatedTopicId, expandString);
                } else {
                    tempCSTranslatedNode = client.getJSONContentSpecTranslatedNodeRevision(translatedTopicId, translatedTopicRevision,
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
                    final List<RESTCSTranslatedNodeStringV1> langCSTranslatedNodes = translatedTopic.getTranslatedNodeStrings_OTM()
                            .returnItems();
                    final List<RESTCSTranslatedNodeStringV1> newLangCSTranslatedNodes = tempCSTranslatedNode.getTranslatedNodeStrings_OTM
                            ().returnItems();
                    for (final RESTCSTranslatedNodeStringV1 newLangCSTranslatedNode : newLangCSTranslatedNodes) {
                        boolean found = false;

                        for (final RESTCSTranslatedNodeStringV1 langCSTranslatedNode : langCSTranslatedNodes) {
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

                for (final RESTCSTranslatedNodeStringCollectionItemV1 translatedTopicItem : translatedTopic.getTranslatedNodeStrings_OTM
                        ().getItems()) {
                    final RESTCSTranslatedNodeStringV1 langCSTranslatedNode = translatedTopicItem.getItem();

                    if (langCSTranslatedNode.getId() == id && (revision == null || langCSTranslatedNode.getRevision().equals(revision))) {
                        return getWrapperFactory().createCollection(translatedTopicString.getRevisions(),
                                RESTCSTranslatedNodeStringV1.class, revision != null, parent);
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    @Override
    public CSTranslatedNodeStringWrapper newCSTranslatedNodeString() {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    @Override
    public CSTranslatedNodeStringWrapper newCSTranslatedNodeString(final CSTranslatedNodeWrapper translatedNode) {
        return getWrapperFactory().create(new RESTCSTranslatedNodeStringV1(), false,
                translatedNode == null ? null : (RESTCSTranslatedNodeV1) translatedNode.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> newCSTranslatedNodeStringCollection() {
        throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> newCSTranslatedNodeStringCollection(
            final CSTranslatedNodeWrapper translatedNode) {
        final CollectionWrapper<CSTranslatedNodeStringWrapper> collection = getWrapperFactory().createCollection(
                new RESTCSTranslatedNodeStringCollectionV1(), RESTCSTranslatedNodeStringV1.class, false,
                translatedNode == null ? null : (RESTCSTranslatedNodeV1) translatedNode.unwrap());
        return (UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper>) collection;
    }
}
