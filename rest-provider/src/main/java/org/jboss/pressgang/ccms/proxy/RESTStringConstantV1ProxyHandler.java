/*
  Copyright 2011-2014 Red Hat

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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTStringConstantProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTStringConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;

public class RESTStringConstantV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTStringConstantV1> {

    public RESTStringConstantV1ProxyHandler(RESTProviderFactory providerFactory, RESTStringConstantV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTStringConstantProvider getProvider() {
        return getProviderFactory().getProvider(RESTStringConstantProvider.class);
    }

    @Override
    public Object internalInvoke(RESTStringConstantV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTStringConstantRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTStringConstantCollectionV1) retValue);
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
