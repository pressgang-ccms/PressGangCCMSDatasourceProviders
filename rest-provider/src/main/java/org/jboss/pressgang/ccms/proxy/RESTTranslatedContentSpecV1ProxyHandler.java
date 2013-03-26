package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedContentSpecProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;

public class RESTTranslatedContentSpecV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedContentSpecV1> {
    public RESTTranslatedContentSpecV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTranslatedContentSpecV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTTranslatedContentSpecProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedContentSpecProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedContentSpecV1 translatedContentSpec = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (translatedContentSpec.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(translatedContentSpec, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTranslatedNodes_OTM")) {
                    retValue = getProvider().getRESTTranslatedNodes(translatedContentSpec.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedContentSpecRevisions(translatedContentSpec.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
