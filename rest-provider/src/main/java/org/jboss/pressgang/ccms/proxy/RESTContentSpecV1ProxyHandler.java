package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTContentSpecProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
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
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTContentSpecV1 contentSpec = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (contentSpec.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(contentSpec, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getChildren_OTM")) {
                    retValue = getProvider().getRESTContentSpecNodes(contentSpec.getId(), getEntityRevision());
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTContentSpecProperties(contentSpec.getId(), getEntityRevision());
                } else if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTContentSpecTags(contentSpec.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTContentSpecRevisions(contentSpec.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
