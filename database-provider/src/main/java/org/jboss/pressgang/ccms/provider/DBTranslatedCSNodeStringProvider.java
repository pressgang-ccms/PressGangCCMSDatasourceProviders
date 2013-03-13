package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedCSNodeStringProvider extends DBDataProvider implements TranslatedCSNodeStringProvider {
    protected DBTranslatedCSNodeStringProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeStringWrapper> getCSTranslatedNodeStringRevisions(int id, Integer revision) {
        final TranslatedCSNodeString translatedTopicString = new TranslatedCSNodeString();
        translatedTopicString.setTranslatedCSNodeStringId(id);
        final Map<Number, TranslatedCSNodeString> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(),
                translatedTopicString);

        final List<TranslatedCSNodeString> revisions = new ArrayList<TranslatedCSNodeString>();
        for (final Map.Entry<Number, TranslatedCSNodeString> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, TranslatedCSNodeString.class, revision != null);
    }

    @Override
    public TranslatedCSNodeStringWrapper newCSTranslatedNodeString() {
        return getWrapperFactory().create(new TranslatedCSNodeString(), false);
    }

    @Override
    public TranslatedCSNodeStringWrapper newCSTranslatedNodeString(TranslatedCSNodeWrapper parent) {
        return newCSTranslatedNodeString();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection() {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TranslatedCSNodeString>(), TranslatedCSNodeString.class, false);

        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection(TranslatedCSNodeWrapper parent) {
        return newCSTranslatedNodeStringCollection();
    }
}
