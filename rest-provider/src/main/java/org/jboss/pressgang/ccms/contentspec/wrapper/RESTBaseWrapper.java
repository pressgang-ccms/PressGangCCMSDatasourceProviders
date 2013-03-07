package org.jboss.pressgang.ccms.contentspec.wrapper;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public abstract class RESTBaseWrapper<T extends EntityWrapper<T>, U extends RESTBaseEntityV1<U, ?, ?>> implements EntityWrapper<T> {

    private final RESTWrapperFactory wrapperFactory;
    private final RESTProviderFactory providerFactory;
    private final boolean isRevision;

    protected RESTBaseWrapper(final RESTProviderFactory providerFactory, boolean isRevision) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
        this.isRevision = isRevision;
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

    protected abstract U getProxyEntity();

    @SuppressWarnings("unchecked")
    protected U getEntity() {
        return ((RESTBaseEntityV1ProxyHandler<U>) ((ProxyObject) getProxyEntity()).getHandler()).getEntity();
    }

    @Override
    public Integer getId() {
        return getProxyEntity().getId();
    }

    @Override
    public void setId(Integer id) {
        getProxyEntity().setId(id);
    }

    @Override
    public Integer getRevision() {
        return getProxyEntity().getRevision();
    }

    @Override
    public boolean isRevisionEntity() {
        return isRevision;
    }
}
