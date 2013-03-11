package org.jboss.pressgang.ccms.wrapper.base;

public interface BasePropertyTagWrapper<T extends BasePropertyTagWrapper<T>> extends EntityWrapper<T> {
    String getName();

    void setName(String name);
}
