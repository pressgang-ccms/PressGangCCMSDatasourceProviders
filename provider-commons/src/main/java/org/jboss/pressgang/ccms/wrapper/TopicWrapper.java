package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;

import org.jboss.pressgang.ccms.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface TopicWrapper extends BaseTopicWrapper<TopicWrapper> {

    String getDescription();

    void setDescription(String description);

    Date getCreated();

    void setCreated(Date created);

    Date getLastModified();

    void setLastModified(Date lastModified);

    void setXmlFormat(Integer formatId);

    CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics();

    String getEditorURL();
}
