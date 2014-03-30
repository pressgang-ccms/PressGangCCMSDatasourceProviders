package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBCSInfoNodeWrapper extends DBBaseEntityWrapper<CSInfoNodeWrapper, CSInfoNode> implements CSInfoNodeWrapper {
    private final CSInfoNode csInfoNode;

    public DBCSInfoNodeWrapper(final DBProviderFactory providerFactory, final CSInfoNode csInfoNode, boolean isRevision) {
        super(providerFactory, isRevision, CSInfoNode.class);
        this.csInfoNode = csInfoNode;
    }

    @Override
    protected CSInfoNode getEntity() {
        return csInfoNode;
    }

    @Override
    public String getCondition() {
        return getEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(condition);
    }

    @Override
    public Integer getTopicId() {
        return getEntity().getTopicId();
    }

    @Override
    public void setTopicId(Integer id) {
        getEntity().setTopicId(id);
    }

    @Override
    public Integer getTopicRevision() {
        return getEntity().getTopicRevision();
    }

    @Override
    public void setTopicRevision(Integer revision) {
        getEntity().setTopicRevision(revision);
    }

    @Override
    public String getInheritedCondition() {
        return getEntity().getInheritedCondition();
    }

    @Override
    public TopicWrapper getTopic() {
        return getWrapperFactory().create(getEntity().getTopic(getEntityManager()), isRevisionEntity(), TopicWrapper.class);
    }

    @Override
    public void setId(Integer id) {
        getEntity().setCSNodeInfoId(id);
    }
}
