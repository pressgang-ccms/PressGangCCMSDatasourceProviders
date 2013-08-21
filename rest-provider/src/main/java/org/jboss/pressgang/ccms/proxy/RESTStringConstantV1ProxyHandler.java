package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTStringConstantProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTStringConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;

public class RESTStringConstantV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTStringConstantV1> {

    public RESTStringConstantV1ProxyHandler(RESTProviderFactory providerFactory, RESTStringConstantV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTStringConstantProvider getProvider() {
        return getProviderFactory().getProvider(RESTStringConstantProvider.class);
    }

    @Override
    public Object internalInvoke(RESTStringConstantV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTStringConstantRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTStringConstantCollectionV1) retValue);
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
