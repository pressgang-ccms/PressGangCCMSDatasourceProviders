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
import org.jboss.pressgang.ccms.provider.RESTTopicProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;

public class RESTTopicV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTopicV1> {

    private final RESTTopicProvider provider;

    public RESTTopicV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTopicV1 topic, final boolean isRevision) {
        super(providerFactory, topic, isRevision);
        provider = providerFactory.getProvider(RESTTopicProvider.class);
    }

    protected RESTTopicProvider getProvider() {
        return provider;
    }

    @Override
    public Object internalInvoke(RESTTopicV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTTopicTags(entity.getId(), getEntityRevision());
                    entity.setTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getSourceUrls_OTM")) {
                    retValue = getProvider().getRESTTopicSourceUrls(entity.getId(), getEntityRevision());
                    entity.setSourceUrls_OTM((RESTTopicSourceUrlCollectionV1) retValue);
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTTopicProperties(entity.getId(), getEntityRevision());
                    entity.setProperties((RESTAssignedPropertyTagCollectionV1) retValue);
                } else if (methodName.equals("getOutgoingRelationships")) {
                    retValue = getProvider().getRESTTopicOutgoingRelationships(entity.getId(), getEntityRevision());
                    entity.setOutgoingRelationships((RESTTopicCollectionV1) retValue);
                } else if (methodName.equals("getIncomingRelationships")) {
                    retValue = getProvider().getRESTTopicIncomingRelationships(entity.getId(), getEntityRevision());
                    entity.setIncomingRelationships((RESTTopicCollectionV1) retValue);
                } else if (methodName.equals("getTranslatedTopics_OTM")) {
                    retValue = getProvider().getRESTTopicTranslations(entity.getId(), getEntityRevision());
                    entity.setTranslatedTopics_OTM((RESTTranslatedTopicCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTopicRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTTopicCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
