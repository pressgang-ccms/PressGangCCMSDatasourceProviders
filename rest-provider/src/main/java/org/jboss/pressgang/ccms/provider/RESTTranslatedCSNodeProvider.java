package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTranslatedCSNodeCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedCSNodeProvider extends RESTDataProvider implements TranslatedCSNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedCSNodeProvider.class);

    protected RESTTranslatedCSNodeProvider(RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTTranslatedCSNodeV1 loadTranslatedCSNode(int id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTranslatedContentSpecNode(id, expandString);
        } else {
            return getRESTClient().getJSONTranslatedContentSpecNodeRevision(id, revision, expandString);
        }
    }

    public RESTTranslatedCSNodeV1 getRESTTranslatedCSNode(int id) {
        return getRESTTranslatedCSNode(id, null);
    }

    @Override
    public TranslatedCSNodeWrapper getTranslatedCSNode(int id) {
        return getTranslatedCSNode(id, null);
    }

    public RESTTranslatedCSNodeV1 getRESTTranslatedCSNode(int id, Integer revision) {
        try {
            final RESTTranslatedCSNodeV1 node;
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                node = getRESTEntityCache().get(RESTTranslatedCSNodeV1.class, id, revision);
            } else {
                node = loadTranslatedCSNode(id, revision, "");
                getRESTEntityCache().add(node, revision);
            }
            return node;
        } catch (Exception e) {
            log.debug("Failed to retrieve Translated Content Spec Node " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TranslatedCSNodeWrapper getTranslatedCSNode(int id, Integer revision) {
        return getWrapperFactory().create(getRESTTranslatedCSNode(id, revision), revision != null);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStrings(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Translated Node Strings using V1 of the REST Interface.");
    }

    public RESTTranslatedCSNodeStringCollectionV1 getRESTTranslatedCSNodeStrings(int id, Integer revision) {
        try {
            RESTTranslatedCSNodeV1 translatedCSNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                translatedCSNode = getRESTEntityCache().get(RESTTranslatedCSNodeV1.class, id, revision);

                if (translatedCSNode.getTranslatedNodeStrings_OTM() != null) {
                    return translatedCSNode.getTranslatedNodeStrings_OTM();
                }
            }

            // We need to expand the all the translated strings in the translated content spec node
            final String expandString = getExpansionString(RESTTranslatedCSNodeV1.TRANSLATED_STRING_NAME);

            // Load the content spec translated node from the REST Interface
            final RESTTranslatedCSNodeV1 tempTranslatedCSNode = loadTranslatedCSNode(id, revision, expandString);

            if (translatedCSNode == null) {
                translatedCSNode = tempTranslatedCSNode;
                getRESTEntityCache().add(translatedCSNode, revision);
            } else {
                translatedCSNode.setTranslatedNodeStrings_OTM(tempTranslatedCSNode.getTranslatedNodeStrings_OTM());
            }

            return translatedCSNode.getTranslatedNodeStrings_OTM();
        } catch (Exception e) {
            log.debug(
                    "Unable to retrieve the Translated Node Strings for Translated ContentSpec Node " + id + (revision == null ? "" : ("," +
                            " Revision" + revision)), e);
            throw handleException(e);
        }
    }

    public RESTTranslatedCSNodeCollectionV1 getRESTTranslatedCSNodeRevisions(int id, Integer revision) {
        try {
            RESTTranslatedCSNodeV1 translatedCSNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                translatedCSNode = getRESTEntityCache().get(RESTTranslatedCSNodeV1.class, id, revision);

                if (translatedCSNode.getRevisions() != null) {
                    return translatedCSNode.getRevisions();
                }
            }

            // We need to expand the revisions in the translated content spec node
            final String expandString = getExpansionString(RESTTranslatedCSNodeV1.REVISIONS_NAME);

            // Load the translated content spec node from the REST Interface
            final RESTTranslatedCSNodeV1 tempCSTranslatedNode = loadTranslatedCSNode(id, revision, expandString);

            if (translatedCSNode == null) {
                translatedCSNode = tempCSTranslatedNode;
                getRESTEntityCache().add(translatedCSNode, revision);
            } else {
                translatedCSNode.setRevisions(tempCSTranslatedNode.getRevisions());
            }

            return translatedCSNode.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Translated Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedCSNodeRevisions(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTTranslatedCSNodeRevisions(id, revision), RESTTranslatedCSNodeV1.class, true);
    }

    public RESTTranslatedTopicCollectionV1 getRESTTranslatedCSNodeTranslatedTopics(int id, Integer revision) {
        try {
            RESTTranslatedCSNodeV1 translatedCSNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedCSNodeV1.class, id, revision)) {
                translatedCSNode = getRESTEntityCache().get(RESTTranslatedCSNodeV1.class, id, revision);

                if (translatedCSNode.getTranslatedTopics_OTM() != null) {
                    return translatedCSNode.getTranslatedTopics_OTM();
                }
            }

            // We need to expand the translated topic in the translated cs node
            final String expandString = getExpansionString(RESTTranslatedCSNodeV1.TRANSLATED_TOPICS_NAME, RESTTranslatedTopicV1.TOPIC_NAME);

            // Load the translated content spec node from the REST Interface
            final RESTTranslatedCSNodeV1 tempTranslatedCSNode = loadTranslatedCSNode(id, revision, expandString);

            if (translatedCSNode == null) {
                translatedCSNode = tempTranslatedCSNode;
                getRESTEntityCache().add(translatedCSNode, revision);
            } else {
                translatedCSNode.setTranslatedTopics_OTM(tempTranslatedCSNode.getTranslatedTopics_OTM());
            }

            return translatedCSNode.getTranslatedTopics_OTM();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Translated Topics for Translated ContentSpec Node " + id + (revision == null ? "" : ("," +
                    " Revision" + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> createTranslatedCSNodes(CollectionWrapper<TranslatedCSNodeWrapper> translatedNodes) {
        try {
            final RESTTranslatedCSNodeCollectionV1 unwrappedNodes = ((RESTTranslatedCSNodeCollectionV1Wrapper) translatedNodes).unwrap();

            final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_TRANSLATED_NODE_EXPANSION_NAME);
            final RESTTranslatedCSNodeCollectionV1 createdNodes = getRESTClient().createJSONTranslatedContentSpecNodes(expandString,
                    unwrappedNodes);
            if (createdNodes != null) {
                getRESTEntityCache().add(createdNodes, false);
                return getWrapperFactory().createCollection(createdNodes, RESTTranslatedCSNodeV1.class, false);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public TranslatedCSNodeWrapper newTranslatedCSNode() {
        return getWrapperFactory().create(new RESTTranslatedCSNodeV1(), false, TranslatedCSNodeWrapper.class, true);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> newTranslatedCSNodeCollection() {
        final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                new RESTTranslatedCSNodeCollectionV1(), RESTTranslatedCSNodeV1.class, false);
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
    }
}
