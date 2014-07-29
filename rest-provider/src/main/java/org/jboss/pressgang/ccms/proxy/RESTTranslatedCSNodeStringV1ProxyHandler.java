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
import org.jboss.pressgang.ccms.provider.RESTTranslatedCSNodeStringProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;

public class RESTTranslatedCSNodeStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedCSNodeStringV1> {

    public RESTTranslatedCSNodeStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTTranslatedCSNodeStringV1 entity,
            boolean isRevisionEntity, final RESTTranslatedCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTTranslatedCSNodeStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedCSNodeStringProvider.class);
    }

    @Override
    public Object internalInvoke(RESTTranslatedCSNodeStringV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedCSNodeStringRevisions(entity.getId(), getEntityRevision(),
                            getParent());
                    entity.setRevisions((RESTTranslatedCSNodeStringCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTTranslatedCSNodeV1 getParent() {
        return (RESTTranslatedCSNodeV1) super.getParent();
    }
}
