package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTPropertyTagInPropertyCategoryProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;

public class RESTPropertyTagInPropertyCategoryV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTPropertyTagInPropertyCategoryV1> {

    public RESTPropertyTagInPropertyCategoryV1ProxyHandler(RESTProviderFactory providerFactory, RESTPropertyTagInPropertyCategoryV1 entity,
            boolean isRevisionEntity, final RESTPropertyCategoryV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    public RESTPropertyTagInPropertyCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTPropertyTagInPropertyCategoryProvider.class);
    }

    @Override
    public Object internalInvoke(RESTPropertyTagInPropertyCategoryV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTPropertyTagInPropertyCategoryRevisions(entity.getId(), getEntityRevision(),
                            getParent());
                    entity.setRevisions((RESTPropertyTagInPropertyCategoryCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTPropertyCategoryV1 getParent() {
        return (RESTPropertyCategoryV1) super.getParent();
    }
}
