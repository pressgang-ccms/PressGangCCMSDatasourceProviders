package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.WrapperFactory;

public abstract class DataProvider {

    private final WrapperFactory wrapperFactory;

    protected DataProvider(final WrapperFactory wrapperFactory) {
        this.wrapperFactory = wrapperFactory;
    }

    protected WrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }
}
