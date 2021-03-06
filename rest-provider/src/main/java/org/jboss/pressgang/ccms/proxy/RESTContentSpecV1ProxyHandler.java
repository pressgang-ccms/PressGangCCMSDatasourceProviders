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

import org.jboss.pressgang.ccms.provider.RESTContentSpecProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslationDetailV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;

public class RESTContentSpecV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTContentSpecV1> {
    public RESTContentSpecV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTContentSpecV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTContentSpecProvider getProvider() {
        return getProviderFactory().getProvider(RESTContentSpecProvider.class);
    }

    @Override
    public Object internalInvoke(RESTContentSpecV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getChildren_OTM")) {
                    retValue = getProvider().getRESTContentSpecNodes(entity.getId(), getEntityRevision());
                    entity.setChildren_OTM((RESTCSNodeCollectionV1) retValue);
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTContentSpecProperties(entity.getId(), getEntityRevision());
                    entity.setProperties((RESTAssignedPropertyTagCollectionV1) retValue);
                } else if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTContentSpecTags(entity.getId(), getEntityRevision());
                    entity.setTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getBookTags")) {
                    retValue = getProvider().getRESTContentSpecBookTags(entity.getId(), getEntityRevision());
                    entity.setBookTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTContentSpecRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTContentSpecCollectionV1) retValue);
                } else if (methodName.equals("getTranslatedContentSpecs")) {
                    retValue = getProvider().getRESTContentSpecTranslations(entity.getId(), getEntityRevision());
                    entity.setTranslatedContentSpecs((RESTTranslatedContentSpecCollectionV1) retValue);
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
