package org.jboss.pressgang.ccms.wrapper.base;

public interface BaseWrapper<T extends BaseWrapper<T>> {
    /**
     * Get the underlying Entity instance.
     *
     * @return
     */
    Object unwrap();
}
