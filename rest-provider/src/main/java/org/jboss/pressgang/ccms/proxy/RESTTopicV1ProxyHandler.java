package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTopicProvider;
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
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTopicV1 topic = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (topic.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(topic, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTTopicTags(topic.getId(), getEntityRevision());
                } else if (methodName.equals("getSourceUrls_OTM")) {
                    retValue = getProvider().getRESTTopicSourceUrls(topic.getId(), getEntityRevision());
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTTopicProperties(topic.getId(), getEntityRevision());
                } else if (methodName.equals("getOutgoingRelationships")) {
                    retValue = getProvider().getRESTTopicOutgoingRelationships(topic.getId(), getEntityRevision());
                } else if (methodName.equals("getIncomingRelationships")) {
                    retValue = getProvider().getRESTTopicIncomingRelationships(topic.getId(), getEntityRevision());
                } else if (methodName.equals("getTranslatedTopics_OTM")) {
                    retValue = getProvider().getRESTTopicTranslations(topic.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTopicRevisions(topic.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
