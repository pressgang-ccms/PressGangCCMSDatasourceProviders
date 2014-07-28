/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSInfoNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSInfoNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;

public class RESTCSNodeInfoV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSInfoNodeV1> {

    public RESTCSNodeInfoV1ProxyHandler(final RESTProviderFactory providerFactory, RESTCSInfoNodeV1 entity, boolean isRevisionEntity,
            final RESTCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTCSInfoNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSInfoNodeProvider.class);
    }

    @Override
    public Object internalInvoke(RESTCSInfoNodeV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCSNodeInfoRevisions(getParent());
                    entity.setRevisions((RESTCSInfoNodeCollectionV1) retValue);
                } else if (methodName.equals("getInheritedCondition")) {
                    retValue = getProvider().getRESTCSNodeInheritedCondition(getParent());
                    entity.setInheritedCondition((String) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTCSNodeV1 getParent() {
        return (RESTCSNodeV1) super.getParent();
    }
}
