package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;

public abstract class RESTBaseWrapper<T extends BaseWrapper<T>, U> implements BaseWrapper<T> {
    private final RESTWrapperFactory wrapperFactory;
    private final RESTProviderFactory providerFactory;
    private final U entity;

    protected RESTBaseWrapper(final RESTProviderFactory providerFactory, final U entity) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
        this.entity = entity;
    }

    protected RESTProviderFactory getProviderFactory() {
        return providerFactory;
    }

    protected RESTWrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    public U unwrap() {
        return getEntity();
    }

    protected U getEntity() {
        return entity;
    }
}
