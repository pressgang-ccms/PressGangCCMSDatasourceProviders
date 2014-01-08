package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTTopicSourceURLV1Wrapper extends RESTBaseEntityWrapper<TopicSourceURLWrapper,
        RESTTopicSourceUrlV1> implements TopicSourceURLWrapper {

    protected RESTTopicSourceURLV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicSourceUrlV1 topicSourceUrl,
            boolean isRevision, final RESTBaseTopicV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, topicSourceUrl, isRevision, parent, isNewEntity);
    }

    protected RESTTopicSourceURLV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicSourceUrlV1 topicSourceUrl,
            boolean isRevision, final RESTBaseTopicV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, topicSourceUrl, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTBaseTopicV1<?, ?, ?> getParentEntity() {
        return (RESTBaseTopicV1<?, ?, ?>) super.getParentEntity();
    }

    @Override
    public TopicSourceURLWrapper clone(boolean deepCopy) {
        return new RESTTopicSourceURLV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), getParentEntity(),
                isNewEntity());
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
}
