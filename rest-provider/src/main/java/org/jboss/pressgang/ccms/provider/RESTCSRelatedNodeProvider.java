package org.jboss.pressgang.ccms.provider;

import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.items.join.RESTCSRelatedNodeCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSRelatedNodeProvider extends RESTCSNodeProvider implements CSRelatedNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCSRelatedNodeProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTCSRelatedNodeProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedNodeRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException(
                "A parent is needed to get Content Spec Related Node revisions using V1 of the REST Interface.");
    }

    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedNodeRevisions(int id, Integer revision, final RESTCSNodeV1 parent) {
        final Integer csNodeId = parent.getId();
        final Integer csNodeRevision = ((RESTBaseEntityV1ProxyHandler<RESTCSNodeV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSNodeV1.class, csNodeId, csNodeRevision)) {
                csNode = entityCache.get(RESTCSNodeV1.class, csNodeId, csNodeRevision);
            }
    
            /*
             * We need to expand the all the related nodes in the csNode collection. This has to be done as we don't know which side of
             * the node the related node comes from (to/from side), so we need to expand and check both.
             */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandRelatedToNodes = new ExpandDataTrunk(new ExpandDataDetails(RESTCSNodeV1.RELATED_TO_NAME));
            final ExpandDataTrunk expandRelatedFromNodes = new ExpandDataTrunk(new ExpandDataDetails(RESTCSNodeV1.RELATED_FROM_NAME));
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTCSRelatedNodeV1
                    .REVISIONS_NAME));

            expandRelatedToNodes.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expandRelatedFromNodes.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            expand.setBranches(CollectionUtilities.toArrayList(expandRelatedToNodes, expandRelatedFromNodes));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTCSNodeV1 tempCSNode;
            if (csNodeRevision == null) {
                tempCSNode = client.getJSONContentSpecNode(csNodeId, expandString);
            } else {
                tempCSNode = client.getJSONContentSpecNodeRevision(csNodeId, csNodeRevision, expandString);
            }

            if (csNode == null) {
                csNode = tempCSNode;
                if (csNodeRevision == null) {
                    entityCache.add(csNode);
                } else {
                    entityCache.add(csNode, csNodeRevision);
                }
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
            for (final RESTCSRelatedNodeCollectionItemV1 categoryItem : csNode.getRelatedToNodes().getItems()) {
                final RESTCSRelatedNodeV1 category = categoryItem.getItem();

                if (category.getId() == id && (revision == null || category.getRevision().equals(revision))) {
                    return getWrapperFactory().createCollection(category.getRevisions(), RESTCSRelatedNodeV1.class, true, parent);
                }
            }

            // Next check if the related node came from the "from" side.
            for (final RESTCSRelatedNodeCollectionItemV1 categoryItem : csNode.getRelatedFromNodes().getItems()) {
                final RESTCSRelatedNodeV1 category = categoryItem.getItem();

                if (category.getId() == id && (revision == null || category.getRevision().equals(revision))) {
                    return getWrapperFactory().createCollection(category.getRevisions(), RESTCSRelatedNodeV1.class, true, parent);
                }
            }

        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for CSRelatedNode " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
        }

        return null;
    }
}
