package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBTopicSourceURLProvider extends DBDataProvider implements TopicSourceURLProvider {

    protected DBTopicSourceURLProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getTopicSourceURLRevisions(int id, Integer revision) {
        final TopicSourceUrl topicSourceUrl = new TopicSourceUrl();
        topicSourceUrl.getTopicSourceUrlId();
        final Map<Number, TopicSourceUrl> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), topicSourceUrl);

        final List<TopicSourceUrl> revisions = new ArrayList<TopicSourceUrl>();
        for (final Map.Entry<Number, TopicSourceUrl> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

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
