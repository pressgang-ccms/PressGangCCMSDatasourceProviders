package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface StringConstantWrapper extends EntityWrapper<StringConstantWrapper> {
    String getName();

    void setName(String name);

    String getValue();

    void setValue(String value);
}
