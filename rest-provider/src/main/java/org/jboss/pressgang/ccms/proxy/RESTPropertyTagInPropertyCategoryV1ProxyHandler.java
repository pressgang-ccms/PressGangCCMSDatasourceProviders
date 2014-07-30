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

import org.jboss.pressgang.ccms.provider.RESTPropertyTagInPropertyCategoryProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;

public class RESTPropertyTagInPropertyCategoryV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTPropertyTagInPropertyCategoryV1> {

    public RESTPropertyTagInPropertyCategoryV1ProxyHandler(RESTProviderFactory providerFactory, RESTPropertyTagInPropertyCategoryV1 entity,
            boolean isRevisionEntity, final RESTPropertyCategoryV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    public RESTPropertyTagInPropertyCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTPropertyTagInPropertyCategoryProvider.class);
    }

    @Override
    public Object internalInvoke(RESTPropertyTagInPropertyCategoryV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTPropertyTagInPropertyCategoryRevisions(entity.getId(), getEntityRevision(),
                            getParent());
                    entity.setRevisions((RESTPropertyTagInPropertyCategoryCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTPropertyCategoryV1 getParent() {
        return (RESTPropertyCategoryV1) super.getParent();
    }
}
