package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedTopicStringProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;

public class RESTTranslatedTopicStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicStringV1> {

    public RESTTranslatedTopicStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTTranslatedTopicStringV1 entity,
            boolean isRevisionEntity, final RESTTranslatedTopicV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTTranslatedTopicStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedTopicStringProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedTopicStringV1 translatedTopicString = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (translatedTopicString.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(translatedTopicString, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedTopicStringRevisions(translatedTopicString.getId(), getEntityRevision(),
                            getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

    @Override
    protected RESTTranslatedTopicV1 getParent() {
        return (RESTTranslatedTopicV1) super.getParent();
    }
}
