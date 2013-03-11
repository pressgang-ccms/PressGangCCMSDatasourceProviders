package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNode;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBCSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCSTranslatedNodeProvider extends DBDataProvider implements CSTranslatedNodeProvider {
    protected DBCSTranslatedNodeProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CSTranslatedNodeWrapper getCSTranslatedNode(int id) {
        final CSTranslatedNode csNode = getEntityManager().find(CSTranslatedNode.class, id);
        return getWrapperFactory().create(csNode, false);
    }

    @Override
    public CSTranslatedNodeWrapper getCSTranslatedNode(int id, Integer revision) {
        if (revision == null) {
            return getCSTranslatedNode(id);
        } else {
            final CSTranslatedNode dummyCSTranslatedNode = new CSTranslatedNode();
            dummyCSTranslatedNode.setCSTranslatedNodeId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyCSTranslatedNode, revision), true);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStrings(int id, Integer revision) {
        final DBCSTranslatedNodeWrapper translatedNode = (DBCSTranslatedNodeWrapper) getCSTranslatedNode(id, revision);
        if (translatedNode == null) {
            return null;
        } else {
            final CollectionWrapper<CSTranslatedNodeStringWrapper> collection = getWrapperFactory().createCollection(
                    translatedNode.unwrap().getCSTranslatedNodeStrings(), CSTranslatedNodeString.class, revision != null);
            return (UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeWrapper> getCSTranslatedNodeRevisions(int id, Integer revision) {
        final CSTranslatedNode csNode = new CSTranslatedNode();
        csNode.setCSTranslatedNodeId(id);
        final Map<Number, CSTranslatedNode> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), csNode);

        final List<CSTranslatedNode> revisions = new ArrayList<CSTranslatedNode>();
        for (final Map.Entry<Number, CSTranslatedNode> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, CSTranslatedNode.class, revision != null);
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeWrapper> createCSTranslatedNodes(
            CollectionWrapper<CSTranslatedNodeWrapper> nodes) throws Exception {
        for (final CSTranslatedNodeWrapper topic : nodes.getItems()) {
            getEntityManager().persist(topic.unwrap());
        }

        // Flush the changes to the database
        getEntityManager().flush();

        return nodes;
    }

    @Override
    public CSTranslatedNodeWrapper newCSTranslatedNode() {
        return getWrapperFactory().create(new CSTranslatedNode(), false, CSTranslatedNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeWrapper> newCSTranslatedNodeCollection() {
        final CollectionWrapper<CSTranslatedNodeWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<CSTranslatedNode>(), CSTranslatedNode.class, false, CSTranslatedNodeWrapper.class);
        return (UpdateableCollectionWrapper<CSTranslatedNodeWrapper>) collection;
    }
}
