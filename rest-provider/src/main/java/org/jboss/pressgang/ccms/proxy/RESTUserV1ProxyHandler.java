package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTUserProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTUserV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTUserV1> {

    public RESTUserV1ProxyHandler(RESTProviderFactory providerFactory, RESTUserV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTUserProvider getProvider() {
        return getProviderFactory().getProvider(RESTUserProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTUserV1 user = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (user.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(user, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTUserRevisions(user.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
