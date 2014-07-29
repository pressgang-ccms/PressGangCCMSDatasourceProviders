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
import org.jboss.pressgang.ccms.provider.RESTTranslatedTopicProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;

public class RESTTranslatedTopicV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1> {

    private final RESTTranslatedTopicProvider provider;

    public RESTTranslatedTopicV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTranslatedTopicV1 entity,
            boolean isRevision) {
        super(providerFactory, entity, isRevision);
        provider = providerFactory.getProvider(RESTTranslatedTopicProvider.class);
    }

    protected RESTTranslatedTopicProvider getProvider() {
        return provider;
    }

    @Override
    public Object internalInvoke(RESTTranslatedTopicV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTTranslatedTopicTags(entity.getId(), getEntityRevision());
                    entity.setTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getSourceUrls_OTM")) {
                    retValue = getProvider().getRESTTranslatedTopicSourceUrls(entity.getId(), getEntityRevision(), getProxyEntity());
                    entity.setSourceUrls_OTM((RESTTopicSourceUrlCollectionV1) retValue);
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTTranslatedTopicProperties(entity.getId(), getEntityRevision());
                    entity.setProperties((RESTAssignedPropertyTagCollectionV1) retValue);
                } else if (methodName.equals("getOutgoingRelationships")) {
                    retValue = getProvider().getRESTTranslatedTopicOutgoingRelationships(entity.getId(), getEntityRevision());
                    entity.setOutgoingRelationships((RESTTranslatedTopicCollectionV1) retValue);
                } else if (methodName.equals("getIncomingRelationships")) {
                    retValue = getProvider().getRESTTranslatedTopicIncomingRelationships(entity.getId(), getEntityRevision());
                    entity.setIncomingRelationships((RESTTranslatedTopicCollectionV1) retValue);
                } else if (methodName.equals("getTranslatedTopicStrings_OTM")) {
                    retValue = getProvider().getRESTTranslatedTopicStrings(entity.getId(), getEntityRevision());
                    entity.setTranslatedTopicStrings_OTM((RESTTranslatedTopicStringCollectionV1) retValue);
                } else if (methodName.equals("getTranslatedCSNode")) {
                    retValue = getProvider().getRESTTranslatedTopicTranslatedCSNode(entity.getId(), getEntityRevision());
                    entity.setTranslatedCSNode((RESTTranslatedCSNodeV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedTopicRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTTranslatedTopicCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
