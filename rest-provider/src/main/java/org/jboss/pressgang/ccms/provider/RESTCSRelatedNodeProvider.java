package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.items.join.RESTCSRelatedNodeCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSRelatedNodeProvider extends RESTCSNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCSRelatedNodeProvider.class);

    protected RESTCSRelatedNodeProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    public RESTCSRelatedNodeCollectionV1 getRESTCSRelatedNodeRevisions(int id, Integer revision, final RESTCSNodeV1 parent) {
        final Integer csNodeId = parent.getId();
        final Integer csNodeRevision = ((RESTBaseEntityV1ProxyHandler<RESTCSNodeV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, csNodeId, csNodeRevision)) {
                csNode = getRESTEntityCache().get(RESTCSNodeV1.class, csNodeId, csNodeRevision);
            }

            // We need to expand the relationships and their revisions
            final Map<String, Collection<String>> expandMap = new HashMap<String, Collection<String>>();
            expandMap.put(RESTCSNodeV1.RELATED_TO_NAME, Arrays.asList(RESTCSRelatedNodeV1.REVISIONS_NAME));
            expandMap.put(RESTCSNodeV1.RELATED_FROM_NAME, Arrays.asList(RESTCSRelatedNodeV1.REVISIONS_NAME));
            final String expandString = getExpansionString(expandMap);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempCSNode = loadCSNode(csNodeId, csNodeRevision, expandString);

            if (csNode == null) {
                csNode = tempCSNode;
                getRESTEntityCache().add(csNode, csNodeRevision);
            } else {
                if (csNode.getRelatedToNodes() == null) {
                    csNode.setRelatedToNodes(tempCSNode.getRelatedToNodes());
                } else {
                    // Iterate over the current and old source urls and add any missing objects.
                    final List<RESTCSRelatedNodeV1> relatedNodes = csNode.getRelatedToNodes().returnItems();
                    final List<RESTCSRelatedNodeV1> newRelatedNodes = tempCSNode.getRelatedToNodes().returnItems();
                    for (final RESTCSRelatedNodeV1 newRelatedNode : newRelatedNodes) {
                        boolean found = false;

                        for (final RESTCSRelatedNodeV1 relatedNode : relatedNodes) {
                            if (relatedNode.getId().equals(newRelatedNode.getId())) {
                                relatedNode.setRevisions(newRelatedNode.getRevisions());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            csNode.getRelatedToNodes().addItem(newRelatedNode);
                        }
                    }
                }

                if (csNode.getRelatedFromNodes() == null) {
                    csNode.setRelatedFromNodes(tempCSNode.getRelatedFromNodes());
                } else {
                    // Iterate over the current and old related nodes and add any missing objects.
                    final List<RESTCSRelatedNodeV1> relatedNodes = csNode.getRelatedFromNodes().returnItems();
                    final List<RESTCSRelatedNodeV1> newRelatedNodes = tempCSNode.getRelatedFromNodes().returnItems();
                    for (final RESTCSRelatedNodeV1 newRelatedNode : newRelatedNodes) {
                        boolean found = false;

                        for (final RESTCSRelatedNodeV1 relatedNode : relatedNodes) {
                            if (relatedNode.getId().equals(newRelatedNode.getId())) {
                                relatedNode.setRevisions(newRelatedNode.getRevisions());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            csNode.getRelatedFromNodes().addItem(newRelatedNode);
                        }
                    }
                }
            }

            // Check if the related node came from the "to" side first.
            for (final RESTCSRelatedNodeCollectionItemV1 relatedNodeItem : csNode.getRelatedToNodes().getItems()) {
                final RESTCSRelatedNodeV1 relatedNode = relatedNodeItem.getItem();

                if (relatedNode.getId() == id && (revision == null || relatedNode.getRevision().equals(revision))) {
                    return relatedNode.getRevisions();
                }
            }

            // Next check if the related node came from the "from" side.
            for (final RESTCSRelatedNodeCollectionItemV1 relatedNodeItem : csNode.getRelatedFromNodes().getItems()) {
                final RESTCSRelatedNodeV1 relatedNode = relatedNodeItem.getItem();

                if (relatedNode.getId() == id && (revision == null || relatedNode.getRevision().equals(revision))) {
                    return relatedNode.getRevisions();
                }
            }

            throw new NotFoundException();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Revisions for CSRelatedNode " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedNodeRevisions(int id, Integer revision, final RESTCSNodeV1 parent) {
        return getWrapperFactory().createCollection(getRESTCSRelatedNodeRevisions(id, revision, parent), RESTCSRelatedNodeV1.class, true,
                parent);
    }
}
