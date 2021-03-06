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

import org.jboss.pressgang.ccms.provider.RESTPropertyTagInContentSpecProvider;
import org.jboss.pressgang.ccms.provider.RESTPropertyTagInTagProvider;
import org.jboss.pressgang.ccms.provider.RESTPropertyTagInTopicProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseAuditedEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;

public class RESTAssignedPropertyTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTAssignedPropertyTagV1> {

    public RESTAssignedPropertyTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTAssignedPropertyTagV1 entity,
            boolean isRevisionEntity, final RESTBaseAuditedEntityV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    @Override
    public Object internalInvoke(RESTAssignedPropertyTagV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    final RESTBaseAuditedEntityV1<?, ?, ?> parent = getParent();
                    if (parent instanceof RESTContentSpecV1) {
                        final RESTPropertyTagInContentSpecProvider provider = getProviderFactory().getProvider(
                                RESTPropertyTagInContentSpecProvider.class);
                        retValue = provider.getRESTPropertyTagInContentSpecRevisions(entity.getId(), getEntityRevision(),
                                (RESTContentSpecV1) parent);
                    } else if (parent instanceof RESTBaseTopicV1) {
                        final RESTPropertyTagInTopicProvider provider = getProviderFactory().getProvider(
                                RESTPropertyTagInTopicProvider.class);
                        retValue = provider.getRESTPropertyTagInTopicRevisions(entity.getId(), getEntityRevision(),
                                (RESTBaseTopicV1) parent);
                    } else if (parent instanceof RESTBaseTagV1) {
                        final RESTPropertyTagInTagProvider provider = getProviderFactory().getProvider(RESTPropertyTagInTagProvider.class);
                        retValue = provider.getRESTPropertyTagInTagRevisions(entity.getId(), getEntityRevision(),
                                (RESTBaseTagV1<?, ?, ?>) parent);
                    }
                    entity.setRevisions((RESTAssignedPropertyTagCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
