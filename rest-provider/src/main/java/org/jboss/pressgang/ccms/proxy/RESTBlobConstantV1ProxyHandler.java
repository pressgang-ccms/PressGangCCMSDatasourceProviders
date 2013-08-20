package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTBlobConstantProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTBlobConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;

public class RESTBlobConstantV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTBlobConstantV1> {

    public RESTBlobConstantV1ProxyHandler(RESTProviderFactory providerFactory, RESTBlobConstantV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTBlobConstantProvider getProvider() {
        return getProviderFactory().getProvider(RESTBlobConstantProvider.class);
    }

    @Override
    public Object internalInvoke(RESTBlobConstantV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTBlobConstantRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTBlobConstantCollectionV1) retValue);
                } else if (methodName.equals("getValue")) {
                    retValue = getProvider().getBlobConstantValue(entity.getId(), getEntityRevision());
                    entity.setValue((byte[]) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
