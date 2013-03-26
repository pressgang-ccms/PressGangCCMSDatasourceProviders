package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTopicSourceURLProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;

public class RESTTopicSourceUrlV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTopicSourceUrlV1> {

    public RESTTopicSourceUrlV1ProxyHandler(final RESTProviderFactory providerFactory, RESTTopicSourceUrlV1 entity,
            boolean isRevisionEntity, final RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTTopicSourceURLProvider getProvider() {
        return getProviderFactory().getProvider(RESTTopicSourceURLProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTopicSourceUrlV1 topicSourceUrl = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (topicSourceUrl.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(topicSourceUrl, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTopicSourceURLRevisions(topicSourceUrl.getId(), getEntityRevision(), getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

    @Override
    protected RESTBaseTopicV1<?, ?, ?> getParent() {
        return (RESTBaseTopicV1<?, ?, ?>) super.getParent();
    }
}
