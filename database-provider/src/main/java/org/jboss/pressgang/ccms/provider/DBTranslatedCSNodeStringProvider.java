package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedCSNodeStringProvider extends DBDataProvider implements TranslatedCSNodeStringProvider {
    protected DBTranslatedCSNodeStringProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStringRevisions(int id, Integer revision) {
        final List<TranslatedCSNodeString> revisions = getRevisionList(TranslatedCSNodeString.class, id);
        return getWrapperFactory().createCollection(revisions, TranslatedCSNodeString.class, revision != null);
    }

    @Override
    public TranslatedCSNodeStringWrapper newTranslatedCSNodeString(TranslatedCSNodeWrapper parent) {
        return getWrapperFactory().create(new TranslatedCSNodeString(), false);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection(TranslatedCSNodeWrapper parent) {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TranslatedCSNodeString>(), TranslatedCSNodeString.class, false);

        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }
}
