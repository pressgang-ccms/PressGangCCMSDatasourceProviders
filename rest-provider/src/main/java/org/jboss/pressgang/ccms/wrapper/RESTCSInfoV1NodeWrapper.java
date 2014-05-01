package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTCSInfoV1NodeWrapper extends RESTBaseEntityWrapper<CSInfoNodeWrapper, RESTCSInfoNodeV1> implements CSInfoNodeWrapper {

    protected RESTCSInfoV1NodeWrapper(final RESTProviderFactory providerFactory, final RESTCSInfoNodeV1 csNodeInfo, boolean isRevision,
            final RESTCSNodeV1 parent, boolean isNewEntity) {
        super(providerFactory, csNodeInfo, isRevision, parent, isNewEntity);
    }

    protected RESTCSInfoV1NodeWrapper(final RESTProviderFactory providerFactory, final RESTCSInfoNodeV1 csNodeInfo, boolean isRevision,
            final RESTCSNodeV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, csNodeInfo, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTCSNodeV1 getParentEntity() {
        return (RESTCSNodeV1) super.getParentEntity();
    }

    @Override
    public CSInfoNodeWrapper clone(boolean deepCopy) {
        return new RESTCSInfoV1NodeWrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), getParentEntity(),
                isNewEntity());
    }

    @Override
    public String getCondition() {
        return getEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().explicitSetCondition(condition);
    }

    @Override
    public Integer getTopicId() {
        return getEntity().getTopicId();
    }

    @Override
    public void setTopicId(Integer id) {
        getEntity().explicitSetTopicId(id);
    }

    @Override
    public Integer getTopicRevision() {
        return getEntity().getTopicRevision();
    }

    @Override
    public void setTopicRevision(Integer revision) {
        getEntity().explicitSetTopicRevision(revision);
    }

    @Override
    public String getInheritedCondition() {
        return getEntity().getInheritedCondition();
    }

    @Override
    public TopicWrapper getTopic() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getTopic())
                .wrapperInterface(TopicWrapper.class)
                .isRevision()
                .build();
    }
}