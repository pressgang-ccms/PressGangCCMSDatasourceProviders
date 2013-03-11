package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface BlobConstantWrapper extends EntityWrapper<BlobConstantWrapper> {
    String getName();

    void setName(String name);

    byte[] getValue();

    void setValue(byte[] value);
}
