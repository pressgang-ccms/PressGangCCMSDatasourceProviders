package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface TopicSourceURLWrapper extends EntityWrapper<TopicSourceURLWrapper> {
    String getTitle();

    void setTitle(String title);

    String getUrl();

    void setUrl(String url);

    String getDescription();

    void setDescription(String description);
}
