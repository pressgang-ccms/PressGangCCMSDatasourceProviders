package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public abstract class RESTBaseEntityV1ProxyHandler<U extends RESTBaseEntityV1<U, ?, ?>> implements MethodHandler {

    private final RESTProviderFactory providerFactory;
    private U proxyEntity;
    private final U entity;
    private final boolean isRevision;

    public RESTBaseEntityV1ProxyHandler(final RESTProviderFactory providerFactory, final U entity, boolean isRevisionEntity) {
        this.entity = entity;
        isRevision = isRevisionEntity;
        this.providerFactory = providerFactory;
    }

    public U getEntity() {
        return entity;
    }

    protected RESTProviderFactory getProviderFactory () {
        return providerFactory;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final U object = getEntity();
        final Object retValue = thisMethod.invoke(object, args);
        if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
            return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue, isRevision);
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
}
