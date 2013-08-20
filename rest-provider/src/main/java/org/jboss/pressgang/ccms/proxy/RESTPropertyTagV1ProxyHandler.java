package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTPropertyTagProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;

public class RESTPropertyTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTPropertyTagV1> {

    public RESTPropertyTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTPropertyTagV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTPropertyTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTPropertyTagProvider.class);
    }

    @Override
    public Object internalInvoke(RESTPropertyTagV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTPropertyTagRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTPropertyTagCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
