package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCSTranslatedNodeStringProvider extends DBDataProvider implements CSTranslatedNodeStringProvider {
    protected DBCSTranslatedNodeStringProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStringRevisions(int id, Integer revision) {
        final CSTranslatedNodeString translatedTopicString = new CSTranslatedNodeString();
        translatedTopicString.setCSTranslatedNodeStringId(id);
        final Map<Number, CSTranslatedNodeString> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(),
                translatedTopicString);

        final List<CSTranslatedNodeString> revisions = new ArrayList<CSTranslatedNodeString>();
        for (final Map.Entry<Number, CSTranslatedNodeString> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, CSTranslatedNodeString.class, revision != null);
    }

    @Override
    public CSTranslatedNodeStringWrapper newCSTranslatedNodeString() {
        return getWrapperFactory().create(new CSTranslatedNodeString(), false);
    }

    @Override
    public CSTranslatedNodeStringWrapper newCSTranslatedNodeString(CSTranslatedNodeWrapper parent) {
        return newCSTranslatedNodeString();
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> newCSTranslatedNodeStringCollection() {
        final CollectionWrapper<CSTranslatedNodeStringWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<CSTranslatedNodeString>(), CSTranslatedNodeString.class, false);

        return (UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> newCSTranslatedNodeStringCollection(CSTranslatedNodeWrapper parent) {
        return newCSTranslatedNodeStringCollection();
    }
}
