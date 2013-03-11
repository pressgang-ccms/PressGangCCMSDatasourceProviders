package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BasePropertyTagWrapper;

public interface PropertyTagInTagWrapper extends BasePropertyTagWrapper<PropertyTagInTagWrapper> {
    String getValue();

    void setValue(String value);

    Integer getRelationshipId();

    Boolean isValid();
}
