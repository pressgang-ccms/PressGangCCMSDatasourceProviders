package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public interface ServerUndefinedEntityWrapper extends BaseWrapper<ServerUndefinedEntityWrapper> {
    String getKey();
    void setKey(String key);
    Integer getValue();
    void setValue(Integer value);
}
