package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTopicSourceURLProvider extends DBDataProvider implements TopicSourceURLProvider {

    protected DBTopicSourceURLProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, Integer revision) {
        final List<TopicSourceUrl> revisions = getRevisionList(TopicSourceUrl.class, id);
        return getWrapperFactory().createCollection(revisions, TopicSourceUrl.class, revision != null);
    }

    @Override
    public TopicSourceURLWrapper newTopicSourceURL(TopicWrapper parent) {
        return getWrapperFactory().create(new TopicSourceUrl(), false);
    }

    @Override
    public UpdateableCollectionWrapper<TopicSourceURLWrapper> newTopicSourceURLCollection(TopicWrapper parent) {
        final CollectionWrapper<TopicSourceURLWrapper> collection = getWrapperFactory().createCollection(new ArrayList<TopicSourceUrl>(),
                TopicSourceUrl.class, false);
        return (UpdateableCollectionWrapper<TopicSourceURLWrapper>) collection;
    }
}
