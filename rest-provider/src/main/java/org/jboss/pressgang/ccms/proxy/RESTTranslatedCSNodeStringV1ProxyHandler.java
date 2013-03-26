package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedCSNodeStringProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;

public class RESTTranslatedCSNodeStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedCSNodeStringV1> {

    public RESTTranslatedCSNodeStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTTranslatedCSNodeStringV1 entity,
            boolean isRevisionEntity, final RESTTranslatedCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTTranslatedCSNodeStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedCSNodeStringProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedCSNodeStringV1 translatedTopicString = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (translatedTopicString.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(translatedTopicString, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedCSNodeStringRevisions(translatedTopicString.getId(), getEntityRevision(),
                            getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

    @Override
    protected RESTTranslatedCSNodeV1 getParent() {
        return (RESTTranslatedCSNodeV1) super.getParent();
    }
}
