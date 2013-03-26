package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedTopicProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;

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
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedTopicV1 topic = getEntity();
        Object retValue = thisMethod.invoke(topic, args);
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (retValue == null && topic.getId() != null && thisMethod.getName().startsWith("get")) {
            final String methodName = thisMethod.getName();

            if (methodName.equals("getTags")) {
                retValue = getProvider().getRESTTranslatedTopicTags(topic.getId(), getEntityRevision());
            } else if (methodName.equals("getSourceUrls_OTM")) {
                retValue = getProvider().getRESTTranslatedTopicSourceUrls(topic.getId(), getEntityRevision(), getProxyEntity());
            } else if (methodName.equals("getProperties")) {
                retValue = getProvider().getRESTTranslatedTopicProperties(topic.getId(), getEntityRevision());
            } else if (methodName.equals("getOutgoingRelationships")) {
                retValue = getProvider().getRESTTranslatedTopicOutgoingRelationships(topic.getId(), getEntityRevision());
            } else if (methodName.equals("getIncomingRelationships")) {
                retValue = getProvider().getRESTTranslatedTopicIncomingRelationships(topic.getId(), getEntityRevision());
            } else if (methodName.equals("getTranslatedTopicStrings_OTM")) {
                retValue = getProvider().getRESTTranslatedTopicStrings(topic.getId(), getEntityRevision());
            } else if (methodName.equals("getTranslatedCSNode")) {
                retValue = getProvider().getRESTTranslatedTopicTranslatedCSNode(topic.getId(), getEntityRevision());
            } else if (methodName.equals("getRevisions")) {
                retValue = getProvider().getRESTTranslatedTopicRevisions(topic.getId(), getEntityRevision());
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
