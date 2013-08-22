package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTTopicSourceURLV1Wrapper extends RESTBaseWrapper<TopicSourceURLWrapper,
        RESTTopicSourceUrlV1> implements TopicSourceURLWrapper {
    private final RESTBaseTopicV1<?, ?, ?> parent;

    protected RESTTopicSourceURLV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicSourceUrlV1 topicSourceUrl,
            boolean isRevision, final RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, topicSourceUrl, isRevision, parent);
        this.parent = parent;
    }

    @Override
    public TopicSourceURLWrapper clone(boolean deepCopy) {
        return new RESTTopicSourceURLV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent);
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getProxyEntity().explicitSetTitle(title);
    }

    @Override
    public String getUrl() {
        return getProxyEntity().getUrl();
    }

    @Override
    public void setUrl(String url) {
        getProxyEntity().explicitSetUrl(url);
    }

    @Override
    public String getDescription() {
        return getProxyEntity().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getProxyEntity().explicitSetDescription(description);
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTopicSourceUrlV1.class, true, parent);
    }
}
