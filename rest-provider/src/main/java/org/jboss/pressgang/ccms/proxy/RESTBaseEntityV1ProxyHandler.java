package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public abstract class RESTBaseEntityV1ProxyHandler<U extends RESTBaseEntityV1<U, ?, ?>> implements MethodHandler {

    private final RESTProviderFactory providerFactory;
    private U proxyEntity;
    private final RESTBaseEntityV1<?, ?, ?> parent;
    private final U entity;
    private final boolean isRevision;

    protected RESTBaseEntityV1ProxyHandler(final RESTProviderFactory providerFactory, final U entity, boolean isRevisionEntity) {
        this.entity = entity;
        isRevision = isRevisionEntity;
        this.providerFactory = providerFactory;
        parent = null;
    }

    protected RESTBaseEntityV1ProxyHandler(final RESTProviderFactory providerFactory, final U entity, boolean isRevisionEntity,
            final RESTBaseEntityV1<?, ?, ?> parent) {
        this.entity = entity;
        isRevision = isRevisionEntity;
        this.providerFactory = providerFactory;
        this.parent = parent;
    }

    public U getEntity() {
        return entity;
    }

    protected RESTProviderFactory getProviderFactory() {
        return providerFactory;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final U object = getEntity();
        final Object retValue = thisMethod.invoke(object, args);
        return checkAndProxyReturnValue(retValue);
    }

    /**
     * Checks the return value and creates a proxy for the content if required.
     *
     * @param retValue The value to be returned.
     * @return The return value with proxied content.
     */
    protected Object checkAndProxyReturnValue(Object retValue) {
        if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
            // The parent will either be a user defined parent, or the entity itself.
            RESTBaseEntityV1 parent = this.parent == null ? getProxyEntity() : this.parent;
            return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue, isRevision, parent);
        } else {
            return retValue;
        }
    }

    public Integer getEntityRevision() {
        return isRevision ? getEntity().getRevision() : null;
    }

    public U getProxyEntity() {
        return proxyEntity;
    }

    public void setProxyEntity(U proxyEntity) {
        this.proxyEntity = proxyEntity;
    }

    protected RESTBaseEntityV1<?, ?, ?> getParent() {
        return parent;
    }
}
