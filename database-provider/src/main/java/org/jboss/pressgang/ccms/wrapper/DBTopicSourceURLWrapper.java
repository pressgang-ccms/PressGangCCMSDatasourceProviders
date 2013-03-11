package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTopicSourceURLWrapper extends DBBaseWrapper<TopicSourceURLWrapper> implements TopicSourceURLWrapper {

    private final TopicSourceUrl topicSourceUrl;

    public DBTopicSourceURLWrapper(final DBProviderFactory providerFactory, final TopicSourceUrl topicSourceUrl, boolean isRevision) {
        super(providerFactory, isRevision);
        this.topicSourceUrl = topicSourceUrl;
    }

    public TopicSourceUrl getTopicSourceUrl() {
        return topicSourceUrl;
    }

    @Override
    public Integer getId() {
        return getTopicSourceUrl().getId();
    }

    @Override
    public void setId(Integer id) {
        getTopicSourceUrl().setTopicSourceUrlId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getTopicSourceUrl().getRevision();
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getTopicSourceUrl()),
                TopicSourceUrl.class, true);
    }

    @Override
    public TopicSourceUrl unwrap() {
        return topicSourceUrl;
    }

    @Override
    public String getTitle() {
        return getTopicSourceUrl().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getTopicSourceUrl().setTitle(title);
    }

    @Override
    public String getUrl() {
        return getTopicSourceUrl().getSourceUrl();
    }

    @Override
    public void setUrl(String url) {
        getTopicSourceUrl().setSourceUrl(url);
    }

    @Override
    public String getDescription() {
        return getTopicSourceUrl().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getTopicSourceUrl().setDescription(description);
    }

}
