package org.jboss.pressgang.ccms.contentspec.provider;

import javax.ws.rs.core.PathSegment;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTCSNodeV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.RESTCSNodeCollectionV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTCSNodeProvider extends RESTDataProvider implements CSNodeProvider {
    private static Logger log = LoggerFactory.getLogger(RESTCSNodeProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTCSNodeProvider(RESTManager restManager, RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CSNodeWrapper getCSNode(int id) {
        return getCSNode(id, null);
    }

    @Override
    public CSNodeWrapper getCSNode(int id, Integer revision) {
        try {
            final RESTCSNodeV1 node;
            if (entityCache.containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                node = entityCache.get(RESTCSNodeV1.class, id, revision);
            } else {
                if (revision == null) {
                    node = client.getJSONContentSpecNode(id, "");
                    entityCache.add(node);
                } else {
                    node = client.getJSONContentSpecNodeRevision(id, revision, "");
                    entityCache.add(node, revision);
                }
            }
            return getWrapperFactory().create(node, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve Content Spec Node " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedToNodes(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) entityCache.get(RESTCSNodeV1.class, id, revision);

                if (csNode.getRelatedToNodes() != null) {
                    return getWrapperFactory().createCollection(csNode.getRelatedToNodes(), RESTCSRelatedNodeV1.class, revision != null);
                }
            }

            // We need to expand the tags in the content spec node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTCSNodeV1.RELATED_TO_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode;
            if (revision == null) {
                tempNode = client.getJSONContentSpecNode(id, expandString);
            } else {
                tempNode = client.getJSONContentSpecNodeRevision(id, revision, expandString);
            }

            if (csNode == null) {
                csNode = tempNode;
                if (revision == null) {
                    entityCache.add(csNode);
                } else {
                    entityCache.add(csNode, revision);
                }
            } else {
                csNode.setRelatedToNodes(tempNode.getRelatedToNodes());
            }

            return getWrapperFactory().createCollection(csNode.getRelatedToNodes(), RESTCSRelatedNodeV1.class, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Related To Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedFromNodes(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) entityCache.get(RESTCSNodeV1.class, id, revision);

                if (csNode.getRelatedFromNodes() != null) {
                    return getWrapperFactory().createCollection(csNode.getRelatedFromNodes(), RESTCSRelatedNodeV1.class, revision != null);
                }
            }

            // We need to expand the tags in the content spec node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTCSNodeV1.RELATED_FROM_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode;
            if (revision == null) {
                tempNode = client.getJSONContentSpecNode(id, expandString);
            } else {
                tempNode = client.getJSONContentSpecNodeRevision(id, revision, expandString);
            }

            if (csNode == null) {
                csNode = tempNode;
                if (revision == null) {
                    entityCache.add(csNode);
                } else {
                    entityCache.add(csNode, revision);
                }
            } else {
                csNode.setRelatedFromNodes(tempNode.getRelatedFromNodes());
            }

            return getWrapperFactory().createCollection(csNode.getRelatedFromNodes(), RESTCSRelatedNodeV1.class, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Related From Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getCSNodeChildren(int id, Integer revision) {
        try {
            RESTCSNodeV1 csNode = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                csNode = (RESTCSNodeV1) entityCache.get(RESTCSNodeV1.class, id, revision);

                if (csNode.getChildren_OTM() != null) {
                    final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(csNode.getChildren_OTM(),
                            RESTCSNodeV1.class, revision != null);
                    return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
                }
            }

            // We need to expand the tags in the content spec node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTCSNodeV1.CHILDREN_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempNode;
            if (revision == null) {
                tempNode = client.getJSONContentSpecNode(id, expandString);
            } else {
                tempNode = client.getJSONContentSpecNodeRevision(id, revision, expandString);
            }

            if (csNode == null) {
                csNode = tempNode;
                if (revision == null) {
                    entityCache.add(csNode);
                } else {
                    entityCache.add(csNode, revision);
                }
            } else {
                csNode.setChildren_OTM(tempNode.getChildren_OTM());
            }

            final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(csNode.getChildren_OTM(),
                    RESTCSNodeV1.class, revision != null);
            return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
        } catch (Exception e) {
            log.error("Failed to retrieve the Children Nodes for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getCSNodeRevisions(int id, Integer revision) {
        try {
            RESTCSNodeV1 contentSpec = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTCSNodeV1.class, id, revision)) {
                contentSpec = entityCache.get(RESTCSNodeV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTCSNodeV1.class, true);
                }
            }

            // We need to expand the revisions in the content spec node collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandCategories = new ExpandDataTrunk(new ExpandDataDetails(RESTCSNodeV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandCategories));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the content spec node from the REST Interface
            final RESTCSNodeV1 tempCSNode;
            if (revision == null) {
                tempCSNode = client.getJSONContentSpecNode(id, expandString);
            } else {
                tempCSNode = client.getJSONContentSpecNodeRevision(id, revision, expandString);
            }

            if (contentSpec == null) {
                contentSpec = tempCSNode;
                if (revision == null) {
                    entityCache.add(contentSpec);
                } else {
                    entityCache.add(contentSpec, revision);
                }
            } else {
                contentSpec.setRevisions(tempCSNode.getRevisions());
            }

            return getWrapperFactory().createCollection(contentSpec.getRevisions(), RESTCSNodeV1.class, true);
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Content Spec Node " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CSNodeWrapper createCSNode(final CSNodeWrapper nodeEntity) throws Exception {
        final RESTCSNodeV1 node = ((RESTCSNodeV1Wrapper) nodeEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(node);

        final RESTCSNodeV1 updatedCSNode = client.createJSONContentSpecNode("", node);
        if (updatedCSNode != null) {
            entityCache.add(updatedCSNode);
            return getWrapperFactory().create(updatedCSNode, false);
        } else {
            return null;
        }
    }

    @Override
    public CSNodeWrapper updateCSNode(CSNodeWrapper nodeEntity) throws Exception {
        final RESTCSNodeV1 node = ((RESTCSNodeV1Wrapper) nodeEntity).unwrap();

        // Clean the entity to remove anything that doesn't need to be sent to the server
        cleanEntityForSave(node);

        final RESTCSNodeV1 updatedCSNode = client.updateJSONContentSpecNode("", node);
        if (updatedCSNode != null) {
            entityCache.expire(RESTCSNodeV1.class, nodeEntity.getId());
            entityCache.add(updatedCSNode);
            return getWrapperFactory().create(updatedCSNode, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCSNode(Integer id) throws Exception {
        final RESTCSNodeV1 topic = client.deleteJSONContentSpecNode(id, "");
        return topic != null;
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> createCSNodes(CollectionWrapper<CSNodeWrapper> topics) throws Exception {
        final RESTCSNodeCollectionV1 unwrappedCSNodes = ((RESTCSNodeCollectionV1Wrapper) topics).unwrap();

        // Clean the collection to remove anything that doesn't need to be sent to the server
        cleanCollectionForSave(unwrappedCSNodes);

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandCSNodes = new ExpandDataTrunk(new ExpandDataDetails(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandCSNodes));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTCSNodeCollectionV1 updatedCSNodes = client.createJSONContentSpecNodes(expandString, unwrappedCSNodes);
        if (updatedCSNodes != null) {
            entityCache.add(updatedCSNodes, false);
            return getWrapperFactory().createCollection(updatedCSNodes, RESTCSNodeV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> updateCSNodes(CollectionWrapper<CSNodeWrapper> topics) throws Exception {
        final RESTCSNodeCollectionV1 unwrappedCSNodes = ((RESTCSNodeCollectionV1Wrapper) topics).unwrap();

        // Clean the collection to remove anything that doesn't need to be sent to the server
        cleanCollectionForSave(unwrappedCSNodes);

        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandCSNodes = new ExpandDataTrunk(new ExpandDataDetails(RESTv1Constants.CONTENT_SPEC_NODE_EXPANSION_NAME));
        expand.setBranches(CollectionUtilities.toArrayList(expandCSNodes));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTCSNodeCollectionV1 updatedCSNodes = client.updateJSONContentSpecNodes(expandString, unwrappedCSNodes);
        if (updatedCSNodes != null) {
            // Expire the old cached data
            for (final RESTCSNodeV1 topic : unwrappedCSNodes.returnItems()) {
                entityCache.expire(RESTCSNodeV1.class, topic.getId());
            }
            // Add the new data to the cache
            entityCache.add(updatedCSNodes, false);
            return getWrapperFactory().createCollection(updatedCSNodes, RESTCSNodeV1.class, false);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCSNodes(final List<Integer> topicIds) throws Exception {
        final String pathString = "ids;" + CollectionUtilities.toSeperatedString(topicIds, ";");
        final PathSegment path = new PathSegmentImpl(pathString, false);
        final RESTCSNodeCollectionV1 topics = client.deleteJSONContentSpecNodes(path, "");
        return topics != null;
    }

    @Override
    public CSNodeWrapper newCSNode() {
        return getWrapperFactory().create(new RESTCSNodeV1(), false, CSNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> newCSNodeCollection() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(new RESTCSNodeCollectionV1(),
                RESTCSNodeV1.class, false);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode() {
        return getWrapperFactory().create(new RESTCSRelatedNodeV1(), false, CSRelatedNodeWrapper.class);
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode(final CSNodeWrapper node) {
        final RESTCSNodeV1 csNode = node == null ? null : (RESTCSNodeV1) node.unwrap();
        return getWrapperFactory().create(new RESTCSRelatedNodeV1(csNode), false, CSRelatedNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> newCSRelatedNodeCollection() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(new RESTCSRelatedNodeCollectionV1(),
                RESTCSRelatedNodeV1.class, false);
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void cleanEntityForSave(final RESTBaseEntityV1<?, ?, ?> entity) throws InvocationTargetException, IllegalAccessException {
        if (entity instanceof RESTCSNodeV1) {
            /*
             * If the entity is a CSNode then replace entities, that could cause recursive serialization issues, with a dummy entity.
             */
            final RESTCSNodeV1 node = (RESTCSNodeV1) entity;

            if (node.getParent() != null) {
                final RESTCSNodeV1 dummyParent = new RESTCSNodeV1();
                dummyParent.setId(node.getParent().getId());

                node.setParent(dummyParent);
            }

            if (node.getContentSpec() != null) {
                final RESTContentSpecV1 dummyContentSpec = new RESTContentSpecV1();
                dummyContentSpec.setId(node.getContentSpec().getId());

                node.setContentSpec(dummyContentSpec);
            }
        }

        super.cleanEntityForSave(entity);
    }
}
