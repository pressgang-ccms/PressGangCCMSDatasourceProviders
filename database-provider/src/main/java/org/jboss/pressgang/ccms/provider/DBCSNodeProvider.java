package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCSNodeProvider extends DBDataProvider implements CSNodeProvider {
    protected DBCSNodeProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CSNodeWrapper getCSNode(int id) {
        return getWrapperFactory().create(getEntity(CSNode.class, id), false);
    }

    @Override
    public CSNodeWrapper getCSNode(int id, Integer revision) {
        if (revision == null) {
            return getCSNode(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(CSNode.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedToNodes(int id, Integer revision) {
        final DBCSNodeWrapper contentSpec = (DBCSNodeWrapper) getCSNode(id, revision);
        if (contentSpec == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(contentSpec.unwrap().getRelatedToNodes(), CSNodeToCSNode.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedFromNodes(int id, Integer revision) {
        final DBCSNodeWrapper contentSpec = (DBCSNodeWrapper) getCSNode(id, revision);
        if (contentSpec == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(contentSpec.unwrap().getRelatedFromNodes(), CSNodeToCSNode.class, revision != null);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getCSNodeChildren(int id, Integer revision) {
        final DBCSNodeWrapper contentSpec = (DBCSNodeWrapper) getCSNode(id, revision);
        if (contentSpec == null) {
            return null;
        } else {
            final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(contentSpec.unwrap().getChildren(),
                    CSNode.class, revision != null);
            return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<CSNodeWrapper> getCSNodeRevisions(int id, Integer revision) {
        final List<CSNode> revisions = getRevisionList(CSNode.class, id);
        return getWrapperFactory().createCollection(revisions, CSNode.class, revision != null);
    }

//    @Override
//    public CSNodeWrapper createCSNode(CSNodeWrapper node) throws Exception {
//        getEntityManager().persist(node.unwrap());
//
//        // Flush the changes to the database
//        getEntityManager().flush();
//
//        return node;
//    }
//
//    @Override
//    public CSNodeWrapper updateCSNode(CSNodeWrapper node) throws Exception {
//        getEntityManager().persist(node.unwrap());
//
//        // Flush the changes to the database
//        getEntityManager().flush();
//
//        return node;
//    }
//
//    @Override
//    public boolean deleteCSNode(Integer id) throws Exception {
//        final CSNode node = getEntityManager().find(CSNode.class, id);
//        getEntityManager().remove(node);
//
//        // Flush the changes to the database
//        getEntityManager().flush();
//
//        return true;
//    }
//
//    @Override
//    public CollectionWrapper<CSNodeWrapper> createCSNodes(CollectionWrapper<CSNodeWrapper> nodes) throws Exception {
//        for (final CSNodeWrapper topic : nodes.getItems()) {
//            getEntityManager().persist(topic.unwrap());
//        }
//
//        // Flush the changes to the database
//        getEntityManager().flush();
//
//        return nodes;
//    }
//
//    @Override
//    public CollectionWrapper<CSNodeWrapper> updateCSNodes(CollectionWrapper<CSNodeWrapper> nodes) throws Exception {
//        for (final CSNodeWrapper topic : nodes.getItems()) {
//            getEntityManager().persist(topic.unwrap());
//        }
//
//        // Flush the changes to the database
//        getEntityManager().flush();
//
//        return nodes;
//    }
//
//    @Override
//    public boolean deleteCSNodes(List<Integer> nodeIds) throws Exception {
//        for (final Integer id : nodeIds) {
//            final CSNode node = getEntityManager().find(CSNode.class, id);
//            getEntityManager().remove(node);
//        }
//
//        // Flush the changes to the database
//        getEntityManager().flush();
//
//        return true;
//    }

    @Override
    public CSNodeWrapper newCSNode() {
        return getWrapperFactory().create(new CSNode(), false, CSNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> newCSNodeCollection() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(new ArrayList<CSNode>(), CSNode.class,
                false, CSNodeWrapper.class);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode() {
        return getWrapperFactory().create(new CSNodeToCSNode(), false, CSRelatedNodeWrapper.class);
    }

    @Override
    public CSRelatedNodeWrapper newCSRelatedNode(CSNodeWrapper node) {
        final CSNodeToCSNode joiner = new CSNodeToCSNode();
        joiner.setRelatedNode((CSNode) node.unwrap());

        return getWrapperFactory().create(joiner, false, CSRelatedNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> newCSRelatedNodeCollection() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(new ArrayList<CSNodeToCSNode>(),
                CSNodeToCSNode.class, false, CSRelatedNodeWrapper.class);
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }
}
