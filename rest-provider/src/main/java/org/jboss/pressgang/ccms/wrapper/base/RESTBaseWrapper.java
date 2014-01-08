package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;

public abstract class RESTBaseWrapper<T extends BaseWrapper<T>, U> implements BaseWrapper<T> {
    private final RESTProviderFactory providerFactory;
    private final U entity;

    protected RESTBaseWrapper(final RESTProviderFactory providerFactory, final U entity) {
        this.providerFactory = providerFactory;
        this.entity = entity;
    }

    protected RESTProviderFactory getProviderFactory() {
        return providerFactory;
    }

    public U unwrap() {
        return getEntity();
    }

    protected U getEntity() {
        return entity;
    }
}
