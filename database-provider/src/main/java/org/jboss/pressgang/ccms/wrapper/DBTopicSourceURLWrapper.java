package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBTopicSourceURLWrapper extends DBBaseEntityWrapper<TopicSourceURLWrapper, TopicSourceUrl> implements TopicSourceURLWrapper {
    private final TopicSourceUrl topicSourceUrl;

    public DBTopicSourceURLWrapper(final DBProviderFactory providerFactory, final TopicSourceUrl topicSourceUrl, boolean isRevision) {
        super(providerFactory, isRevision, TopicSourceUrl.class);
        this.topicSourceUrl = topicSourceUrl;
    }

    @Override
    protected TopicSourceUrl getEntity() {
        return topicSourceUrl;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTopicSourceUrlId(id);
    }

    @Override
    public String getTitle() {
        return getEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setTitle(title);
    }

    @Override
    public String getUrl() {
        return getEntity().getSourceUrl();
    }

    @Override
    public void setUrl(String url) {
        getEntity().setSourceUrl(url);
    }

    @Override
    public String getDescription() {
        return getEntity().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getEntity().setDescription(description);
    }
}
