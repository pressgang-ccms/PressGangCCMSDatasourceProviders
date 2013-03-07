package org.jboss.pressgang.ccms.contentspec.provider;

import javax.persistence.EntityManager;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;

public class DBDataProvider extends DataProvider {
    private final EntityManager entityManager;

    protected DBDataProvider(final EntityManager entityManager, final DBWrapperFactory wrapperFactory) {
        super(wrapperFactory);
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected DBWrapperFactory getWrapperFactory() {
        return (DBWrapperFactory) super.getWrapperFactory();
    }
}
