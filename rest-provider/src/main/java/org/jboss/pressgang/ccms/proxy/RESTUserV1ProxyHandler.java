package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTUserProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTUserCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;

public class RESTUserV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTUserV1> {

    public RESTUserV1ProxyHandler(RESTProviderFactory providerFactory, RESTUserV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTUserProvider getProvider() {
        return getProviderFactory().getProvider(RESTUserProvider.class);
    }

    @Override
    public Object internalInvoke(RESTUserV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTUserRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTUserCollectionV1) retValue);
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
