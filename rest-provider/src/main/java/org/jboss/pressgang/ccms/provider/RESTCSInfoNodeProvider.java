package org.jboss.pressgang.ccms.provider;

import java.lang.reflect.InvocationTargetException;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSInfoNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.elements.base.RESTBaseElementV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSInfoNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSInfoNodeProvider extends RESTDataProvider implements CSInfoNodeProvider {
    private static final Logger log = LoggerFactory.getLogger(RESTCSInfoNodeProvider.class);

    protected RESTCSInfoNodeProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTCSInfoNodeV1 loadCSNodeInfo(Integer id, Integer revision, RESTCSNodeV1 parent) {
        try {
            if (getRESTEntityCache().containsKeyValue(RESTCSInfoNodeV1.class, id, revision)) {
                return getRESTEntityCache().get(RESTCSInfoNodeV1.class, id, revision);
            } else {
                return parent.getInfoTopicNode();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve Content Spec Node Info " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTCSInfoNodeV1 getRESTCSNodeInfo(int id, RESTCSNodeV1 parent) {
        return getRESTCSNodeInfo(id, null, parent);
    }

    public RESTCSInfoNodeV1 getRESTCSNodeInfo(int id, Integer revision, final RESTCSNodeV1 parent) {
        try {
            final RESTCSInfoNodeV1 node;
            if (getRESTEntityCache().containsKeyValue(RESTCSInfoNodeV1.class, id, revision)) {
                node = getRESTEntityCache().get(RESTCSInfoNodeV1.class, id, revision);
            } else {
                node = loadCSNodeInfo(id, revision, parent);
                getRESTEntityCache().add(node, revision);
            }
            return node;
        } catch (Exception e) {
            log.debug("Failed to retrieve Content Spec Node " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTCSInfoNodeCollectionV1 getRESTCSNodeInfoRevisions(final RESTCSNodeV1 parent) {
        final Integer csNodeId = parent.getId();
        final Integer csNodeRevision = ((RESTBaseEntityV1ProxyHandler<RESTCSNodeV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTCSNodeV1 node = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, csNodeId, csNodeRevision)) {
                node = getRESTEntityCache().get(RESTCSNodeV1.class, csNodeId, csNodeRevision);
            }

            // We need to expand the all the revisions in the info node
            final String expandString = getExpansionString(RESTCSNodeV1.INFO_TOPIC_NODE_NAME, RESTCSInfoNodeV1.REVISIONS_NAME);

            // Load the node from the REST Interface
            final RESTCSNodeV1 tempNode;
            if (csNodeRevision == null) {
                tempNode = getRESTClient().getJSONContentSpecNode(csNodeId, expandString);
            } else {
                tempNode = getRESTClient().getJSONContentSpecNodeRevision(csNodeId, csNodeRevision, expandString);
            }

            if (node == null) {
                node = tempNode;
                getRESTEntityCache().add(node, csNodeRevision);
            } else {
                node.setInfoTopicNode(tempNode.getInfoTopicNode());
            }

            return node.getInfoTopicNode().getRevisions();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Content Spec Info Node for Content Spec Node" + csNodeId + (csNodeRevision == null ? "" :
                    (", Revision " + csNodeRevision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<CSInfoNodeWrapper> getCSNodeInfoRevisions(int id, Integer revision, final CSNodeWrapper parent) {
        final RESTCSNodeV1 parentEle = parent == null ? null : (RESTCSNodeV1) parent.unwrap();
        return RESTCollectionWrapperBuilder.<CSInfoNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTCSNodeInfoRevisions(parentEle))
                .parent(parentEle)
                .isRevisionCollection()
                .build();
    }

    public String getRESTCSNodeInheritedCondition(RESTCSNodeV1 parent) {
        final Integer csNodeId = parent.getId();
        final Integer csNodeRevision = ((RESTBaseEntityV1ProxyHandler<RESTCSNodeV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        try {
            RESTCSNodeV1 node = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTCSNodeV1.class, csNodeId, csNodeRevision)) {
                node = getRESTEntityCache().get(RESTCSNodeV1.class, csNodeId, csNodeRevision);
            }

            // We need to expand the all the inherited condition in the info node
            final String expandString = getExpansionString(RESTCSNodeV1.INFO_TOPIC_NODE_NAME, RESTCSInfoNodeV1.INHERITED_CONDITION_NAME);

            // Load the node from the REST Interface
            final RESTCSNodeV1 tempNode;
            if (csNodeRevision == null) {
                tempNode = getRESTClient().getJSONContentSpecNode(csNodeId, expandString);
            } else {
                tempNode = getRESTClient().getJSONContentSpecNodeRevision(csNodeId, csNodeRevision, expandString);
            }

            if (node == null) {
                node = tempNode;
                getRESTEntityCache().add(node, csNodeRevision);
            } else {
                node.setInheritedCondition(tempNode.getInheritedCondition());
            }

            return node.getInfoTopicNode().getInheritedCondition();
        } catch (Exception e) {
            log.debug("Unable to retrieve the Content Spec Info Node for Content Spec Node" + csNodeId + (csNodeRevision == null ? "" :
                    (", Revision " + csNodeRevision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CSInfoNodeWrapper newCSNodeInfo(final CSNodeWrapper parent) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTCSInfoNodeV1())
                .parent(parent == null ? null : (RESTCSNodeV1) parent.unwrap())
                .newEntity()
                .build();
    }

    @Override
    public void cleanEntityForSave(final RESTBaseElementV1<?> entity) throws InvocationTargetException, IllegalAccessException {
        if (entity instanceof RESTCSInfoNodeV1) {
            /*
             * If the entity is a CSInfoNode then replace entities, that could cause recursive serialization issues, with a dummy entity.
             */
            final RESTCSInfoNodeV1 node = (RESTCSInfoNodeV1) entity;

            if (node.getCSNode() != null) {
                final RESTCSNodeV1 dummyNode = new RESTCSNodeV1();
                dummyNode.setId(node.getCSNode().getId());
                node.setCSNode(dummyNode);
            }
        }

        super.cleanEntityForSave(entity);
    }
}
