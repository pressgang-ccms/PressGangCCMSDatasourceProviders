package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface CSInfoNodeWrapper extends EntityWrapper<CSInfoNodeWrapper> {
    String getCondition();

    void setCondition(String condition);

    Integer getTopicId();

    void setTopicId(Integer id);

    Integer getTopicRevision();

    void setTopicRevision(Integer revision);

    String getInheritedCondition();

    TopicWrapper getTopic();
}
