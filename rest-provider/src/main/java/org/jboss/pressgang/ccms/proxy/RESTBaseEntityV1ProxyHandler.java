/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseAuditedEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

@SuppressWarnings("unchecked")
public abstract class RESTBaseEntityV1ProxyHandler<U extends RESTBaseEntityV1<U>> implements MethodHandler {

    private final RESTProviderFactory providerFactory;
    private U proxyEntity;
    private final RESTBaseAuditedEntityV1<?, ?, ?> parent;
    private final U entity;
    private final boolean isRevision;

    private final Set<String> processedMethodNames = new HashSet<String>();

    protected RESTBaseEntityV1ProxyHandler(final RESTProviderFactory providerFactory, final U entity, boolean isRevisionEntity) {
        this.entity = entity;
        isRevision = isRevisionEntity;
        this.providerFactory = providerFactory;
        parent = null;
    }

    protected RESTBaseEntityV1ProxyHandler(final RESTProviderFactory providerFactory, final U entity, boolean isRevisionEntity,
            final RESTBaseAuditedEntityV1<?, ?, ?> parent) {
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
    public final Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final U object = getEntity();
        final String methodName = thisMethod.getName();
        if (methodName.equals("equals")) {
            return thisMethod.invoke(object, unproxyArgs(args));
        } else {
            final Object retValue;
            if (!processedMethodNames.contains(methodName)) {
                processedMethodNames.add(methodName);
                retValue = internalInvoke(object, thisMethod, args);
            } else {
                retValue = thisMethod.invoke(object, args);
            }
            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }
    }

    protected Object internalInvoke(U entity, Method method, Object[] args) throws Throwable {
        return method.invoke(entity, args);
    }

    /**
     * Checks the return value and creates a proxy for the content if required.
     *
     * @param retValue The value to be returned.
     * @return The return value with proxied content.
     */
    private Object checkAndProxyReturnValue(Object retValue) {
        if (retValue != null && retValue instanceof RESTBaseEntityCollectionV1) {
            // The parent will either be a user defined parent, or the entity itself.
            final RESTBaseEntityV1<?> parent = this.parent == null ? getProxyEntity() : this.parent;
            return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseEntityCollectionV1) retValue, isRevision, parent);
        } else {
            return retValue;
        }
    }

    public Integer getEntityRevision() {
        if (getEntity() instanceof RESTBaseAuditedEntityV1) {
            return isRevision ? ((RESTBaseAuditedEntityV1) getEntity()).getRevision() : null;
        } else {
            return null;
        }
    }

    public U getProxyEntity() {
        return proxyEntity;
    }

    public void setProxyEntity(U proxyEntity) {
        this.proxyEntity = proxyEntity;
    }

    protected RESTBaseAuditedEntityV1<?, ?, ?> getParent() {
        return parent;
    }

    public Set<String> getProcessedMethodNames() {
        return processedMethodNames;
    }

    protected Object[] unproxyArgs(Object[] args) {
        if (args == null) return null;

        for (int i = 0; i < args.length; i++) {
            final Object o = args[i];
            if (o instanceof ProxyObject) {
                final ProxyObject proxy = (ProxyObject) o;
                if (proxy.getHandler() instanceof RESTCollectionV1ProxyHandler) {
                    args[i] = ((RESTCollectionV1ProxyHandler) proxy.getHandler()).getCollection();
                } else if (proxy.getHandler() instanceof RESTBaseEntityV1ProxyHandler) {
                    args[i] = ((RESTBaseEntityV1ProxyHandler) proxy.getHandler()).getEntity();
                }
            }
        }

        return args;
    }
}
