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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTextContentSpecProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTextContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslationDetailV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextContentSpecV1;

public class RESTTextContentSpecV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTextContentSpecV1> {
    public RESTTextContentSpecV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTextContentSpecV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTTextContentSpecProvider getProvider() {
        return getProviderFactory().getProvider(RESTTextContentSpecProvider.class);
    }

    @Override
    public Object internalInvoke(RESTTextContentSpecV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTContentSpecProperties(entity.getId(), getEntityRevision());
                    entity.setProperties((RESTAssignedPropertyTagCollectionV1) retValue);
                } else if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTTextContentSpecTags(entity.getId(), getEntityRevision());
                    entity.setTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTextContentSpecRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTTextContentSpecCollectionV1) retValue);
                } else if (methodName.equals("getTranslationDetails")) {
                    retValue = getProvider().getRESTContentSpecTranslationDetail(entity.getId(), getEntityRevision());
                    entity.setTranslationDetails((RESTCSTranslationDetailV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
