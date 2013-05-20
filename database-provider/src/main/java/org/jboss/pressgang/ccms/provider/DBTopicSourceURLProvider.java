package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTopicSourceURLProvider extends DBDataProvider implements TopicSourceURLProvider {

    protected DBTopicSourceURLProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
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
    public CollectionWrapper<TopicSourceURLWrapper> newTopicSourceURLCollection(TopicWrapper parent) {
        return getWrapperFactory().createCollection(new ArrayList<TopicSourceUrl>(), TopicSourceUrl.class, false);
    }
}
