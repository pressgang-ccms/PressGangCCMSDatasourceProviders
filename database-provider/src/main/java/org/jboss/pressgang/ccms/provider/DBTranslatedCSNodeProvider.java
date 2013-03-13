package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.DBTranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedCSNodeProvider extends DBDataProvider implements TranslatedCSNodeProvider {
    protected DBTranslatedCSNodeProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public TranslatedCSNodeWrapper getCSTranslatedNode(int id) {
        final TranslatedCSNode csNode = getEntityManager().find(TranslatedCSNode.class, id);
        return getWrapperFactory().create(csNode, false);
    }

    @Override
    public TranslatedCSNodeWrapper getCSTranslatedNode(int id, Integer revision) {
        if (revision == null) {
            return getCSTranslatedNode(id);
        } else {
            final TranslatedCSNode dummyTranslatedCSNode = new TranslatedCSNode();
            dummyTranslatedCSNode.setTranslatedCSNodeId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyTranslatedCSNode, revision), true);
        }
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getCSTranslatedNodeStrings(int id, Integer revision) {
        final DBTranslatedCSNodeWrapper translatedNode = (DBTranslatedCSNodeWrapper) getCSTranslatedNode(id, revision);
        if (translatedNode == null) {
            return null;
        } else {
            final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                    translatedNode.unwrap().getTranslatedCSNodeStrings(), TranslatedCSNodeString.class, revision != null);
            return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getCSTranslatedNodeRevisions(int id, Integer revision) {
        final TranslatedCSNode csNode = new TranslatedCSNode();
        csNode.setTranslatedCSNodeId(id);
        final Map<Number, TranslatedCSNode> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), csNode);

        final List<TranslatedCSNode> revisions = new ArrayList<TranslatedCSNode>();
        for (final Map.Entry<Number, TranslatedCSNode> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, TranslatedCSNode.class, revision != null);
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> createCSTranslatedNodes(
            CollectionWrapper<TranslatedCSNodeWrapper> nodes) throws Exception {
        for (final TranslatedCSNodeWrapper topic : nodes.getItems()) {
            getEntityManager().persist(topic.unwrap());
        }

        // Flush the changes to the database
        getEntityManager().flush();

        return nodes;
    }

    @Override
    public TranslatedCSNodeWrapper newCSTranslatedNode() {
        return getWrapperFactory().create(new TranslatedCSNode(), false, TranslatedCSNodeWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> newCSTranslatedNodeCollection() {
        final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TranslatedCSNode>(), TranslatedCSNode.class, false, TranslatedCSNodeWrapper.class);
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
    }
}
