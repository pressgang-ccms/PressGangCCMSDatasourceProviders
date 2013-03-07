package org.jboss.pressgang.ccms.contentspec.wrapper;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.EntityWrapper;

public abstract class DBBaseWrapper<T extends EntityWrapper<T>> implements EntityWrapper<T> {

    private final DBProviderFactory providerFactory;
    private final DBWrapperFactory wrapperFactory;
    private final boolean isRevision;
    private final List<String> tempVariables = new ArrayList<String>();

    public DBBaseWrapper(final DBProviderFactory providerFactory, boolean isRevision) {
        this.providerFactory = providerFactory;
        this.isRevision = isRevision;
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
    
    @Override
    public T clone(boolean deepCopy) {
        throw new UnsupportedOperationException("The clone method has no implementation.");
    }

    @Override
    public boolean isRevisionEntity() {
        return isRevision;
    }

    public List<String> getTempVariables() {
        return tempVariables;
    }
}
