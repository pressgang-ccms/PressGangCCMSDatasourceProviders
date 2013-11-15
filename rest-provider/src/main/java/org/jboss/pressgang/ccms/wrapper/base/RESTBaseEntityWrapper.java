package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public abstract class RESTBaseEntityWrapper<T extends EntityWrapper<T>, U extends RESTBaseEntityV1<U, ?,
        ?>> extends RESTBaseWrapper<T, U> implements EntityWrapper<T> {

    private final boolean isRevision;
    private final U proxyEntity;
    private final boolean isNewEntity;

    protected RESTBaseEntityWrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        this(providerFactory, entity, isRevision, null, isNewEntity);
    }

    protected RESTBaseEntityWrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            final RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, entity);
        this.isRevision = isRevision;
        this.isNewEntity = isNewEntity;
        if (isNewEntity) {
            proxyEntity = entity;
        } else {
            proxyEntity = RESTEntityProxyFactory.createProxy(providerFactory, entity, isRevision, parent);
        }
    }

    protected U getProxyEntity() {
        return proxyEntity;
    }

    @Override
    public Integer getId() {
        return getProxyEntity().getId();
    }

    @Override
    public void setId(Integer id) {
        getEntity().setId(id);
    }

    @Override
    public Integer getRevision() {
        return getProxyEntity().getRevision();
    }

    @Override
    public boolean isRevisionEntity() {
        return isRevision;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof RESTBaseEntityWrapper && getProxyEntity() != null) {
            return o == null ? false : getEntity().equals(((RESTBaseEntityWrapper) o).getEntity());
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        if (getProxyEntity() != null) {
            return getEntity().hashCode();
        } else {
            return super.hashCode();
        }
    }

    protected boolean isNewEntity() {
        return isNewEntity;
    }
}
