package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTextContentSpecProvider;
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
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTextContentSpecV1 contentSpec = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (contentSpec.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(contentSpec, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTContentSpecProperties(contentSpec.getId(), getEntityRevision());
                } else if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTTextContentSpecTags(contentSpec.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTextContentSpecRevisions(contentSpec.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
