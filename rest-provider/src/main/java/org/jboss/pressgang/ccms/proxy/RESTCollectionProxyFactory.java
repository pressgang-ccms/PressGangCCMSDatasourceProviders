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

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTCollectionProxyFactory {
    private static final MethodFilter FINALIZE_FILTER = new MethodFilter() {
        public boolean isHandled(Method m) {
            // skip finalize methods
            return !(m.getParameterTypes().length == 0 && m.getName().equals("finalize"));
        }
    };

    public static <T extends RESTBaseEntityV1<T, U, V>, U extends RESTBaseEntityCollectionV1<T, U, V>, V extends RESTBaseEntityCollectionItemV1<T, U,
                V>> U create(
            final RESTProviderFactory providerFactory, final U collection, boolean isRevision) {
        return create(providerFactory, collection, isRevision, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends RESTBaseEntityV1<T, U, V>, U extends RESTBaseEntityCollectionV1<T, U, V>, V extends RESTBaseEntityCollectionItemV1<T, U,
                V>> U create(
            final RESTProviderFactory providerFactory, final U collection, boolean isRevision, RESTBaseEntityV1<?, ?, ?> parent) {
        final Class<?> clazz = collection.getClass();

        final ProxyFactory factory = new ProxyFactory();
        factory.setFilter(FINALIZE_FILTER);
        factory.setSuperclass(clazz);

        Class<?> cl = factory.createClass();
        U proxy = null;
        try {
            proxy = (U) cl.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        final RESTCollectionV1ProxyHandler proxyHandler = findProxyHandler(providerFactory, collection, isRevision, parent);
        ((ProxyObject) proxy).setHandler(proxyHandler);
        proxyHandler.setProxyCollection(proxy);

        return proxy;
    }

    private static <T extends RESTBaseEntityV1<T, U, V>, U extends RESTBaseEntityCollectionV1<T, U, V>, V extends RESTBaseEntityCollectionItemV1<T,
                U, V>> RESTCollectionV1ProxyHandler<T, U, V> findProxyHandler(
            final RESTProviderFactory providerFactory, final U collection, boolean isRevision, final RESTBaseEntityV1<?, ?, ?> parent) {

        return new RESTCollectionV1ProxyHandler<T, U, V>(providerFactory, collection, isRevision, parent);
    }
}
