package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBTranslatedTopicStringProvider extends DBDataProvider implements TranslatedTopicStringProvider {
    protected DBTranslatedTopicStringProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStringRevisions(int id, Integer revision) {
        final TranslatedTopicString translatedTopicString = new TranslatedTopicString();
        translatedTopicString.setTranslatedTopicStringID(id);
        final Map<Number, TranslatedTopicString> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(),
                translatedTopicString);

        final List<TranslatedTopicString> revisions = new ArrayList<TranslatedTopicString>();
        for (final Map.Entry<Number, TranslatedTopicString> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, TranslatedTopicString.class, revision != null);
    }

    @Override
    public TranslatedTopicStringWrapper newTranslatedTopicString() {
        return getWrapperFactory().create(new TranslatedTopicString(), false);
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> newTranslatedTopicStringCollection() {
        return getWrapperFactory().createCollection(new ArrayList<TranslatedTopicString>(), TranslatedTopicString.class,
                false);
    }
}
