package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public interface ServerUndefinedSettingWrapper extends BaseWrapper<ServerUndefinedSettingWrapper> {
    String getKey();
    void setKey(String key);
    String getValue();
    void setValue(String value);
}
