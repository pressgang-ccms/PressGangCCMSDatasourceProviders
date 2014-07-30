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

package org.jboss.pressgang.ccms.rest;

import org.jboss.pressgang.ccms.rest.v1.client.PressGangCCMSProxyFactoryV1;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTCollectionCache;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;

/**
 * A class to manage components needed to interact with the REST Interface.
 */
public class RESTManager {

    private final PressGangCCMSProxyFactoryV1 proxyFactory;
    private RESTInterfaceV1 client;
    private final RESTEntityCache entityCache = new RESTEntityCache();
    private final RESTCollectionCache collectionCache = new RESTCollectionCache(entityCache);

    public RESTManager(final String serverUrl) {
        proxyFactory = PressGangCCMSProxyFactoryV1.create(serverUrl);
    }

    public RESTInterfaceV1 getRESTClient() {
        if (client == null) {
            client = proxyFactory.getRESTClient();
        }
        return client;
    }

    public RESTEntityCache getRESTEntityCache() {
        return entityCache;
    }

    public RESTCollectionCache getRESTCollectionCache() {
        return collectionCache;
    }

    public PressGangCCMSProxyFactoryV1 getProxyFactory() {
        return proxyFactory;
    }
}
