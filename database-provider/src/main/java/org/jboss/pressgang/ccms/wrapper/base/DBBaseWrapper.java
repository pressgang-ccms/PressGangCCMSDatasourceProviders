package org.jboss.pressgang.ccms.wrapper.base;

import javax.persistence.EntityManager;

import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public abstract class DBBaseWrapper<T extends BaseWrapper<T>, U> implements BaseWrapper<T> {
    private final DBProviderFactory providerFactory;
    private final DBWrapperFactory wrapperFactory;

    protected DBBaseWrapper(final DBProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
    }

    protected DBProviderFactory getDatabaseProvider() {
        return providerFactory;
    }

    protected DBWrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    protected EntityManager getEntityManager() {
        return getDatabaseProvider().getEntityManager();
    }

    protected abstract U getEntity();

    @Override
    public U unwrap() {
        return getEntity();
    }
}
