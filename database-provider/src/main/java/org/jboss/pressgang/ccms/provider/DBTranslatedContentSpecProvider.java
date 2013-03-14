package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.DBTranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedContentSpecProvider extends DBDataProvider implements TranslatedContentSpecProvider {
    protected DBTranslatedContentSpecProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec(int id) {
        final TranslatedContentSpec csNode = getEntityManager().find(TranslatedContentSpec.class, id);
        return getWrapperFactory().create(csNode, false);
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec(int id, Integer revision) {
        if (revision == null) {
            return getTranslatedContentSpec(id);
        } else {
            final TranslatedContentSpec dummyTranslatedContentSpec = new TranslatedContentSpec();
            dummyTranslatedContentSpec.setTranslatedContentSpecId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyTranslatedContentSpec, revision), true);
        }
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes(int id, Integer revision) {
        final DBTranslatedContentSpecWrapper translatedContentSpec = (DBTranslatedContentSpecWrapper) getTranslatedContentSpec(id, revision);
        if (translatedContentSpec == null) {
            return null;
        } else {
            final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                    translatedContentSpec.unwrap().getTranslatedCSNodes(), TranslatedCSNode.class, revision != null);
            return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecRevisions(int id, Integer revision) {
        final TranslatedContentSpec csNode = new TranslatedContentSpec();
        csNode.setTranslatedContentSpecId(id);
        final Map<Number, TranslatedContentSpec> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), csNode);

        final List<TranslatedContentSpec> revisions = new ArrayList<TranslatedContentSpec>();
        for (final Map.Entry<Number, TranslatedContentSpec> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, TranslatedContentSpec.class, revision != null);
    }

    @Override
    public TranslatedContentSpecWrapper createTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec) throws Exception {
        getEntityManager().persist(translatedContentSpec.unwrap());

        // Flush the changes to the database
        getEntityManager().flush();

        return translatedContentSpec;
    }

    @Override
    public TranslatedContentSpecWrapper updateTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec) throws Exception {
        getEntityManager().persist(translatedContentSpec.unwrap());

        // Flush the changes to the database
        getEntityManager().flush();

        return translatedContentSpec;
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> createTranslatedContentSpecs(
            CollectionWrapper<TranslatedContentSpecWrapper> nodes) throws Exception {
        for (final TranslatedContentSpecWrapper topic : nodes.getItems()) {
            getEntityManager().persist(topic.unwrap());
        }

        // Flush the changes to the database
        getEntityManager().flush();

        return nodes;
    }

    @Override
    public TranslatedContentSpecWrapper newTranslatedContentSpec() {
        return getWrapperFactory().create(new TranslatedContentSpec(), false, TranslatedContentSpecWrapper.class);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedContentSpecWrapper> newTranslatedContentSpecCollection() {
        final CollectionWrapper<TranslatedContentSpecWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TranslatedContentSpec>(), TranslatedContentSpec.class, false, TranslatedContentSpecWrapper.class);
        return (UpdateableCollectionWrapper<TranslatedContentSpecWrapper>) collection;
    }
}
