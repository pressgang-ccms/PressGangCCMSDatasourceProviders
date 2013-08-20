package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCategoryProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;

public class RESTCategoryV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCategoryV1> {

    public RESTCategoryV1ProxyHandler(RESTProviderFactory providerFactory, RESTCategoryV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTCategoryProvider.class);
    }

    @Override
    public Object internalInvoke(RESTCategoryV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTCategoryTags(entity.getId(), getEntityRevision());
                    entity.setTags((RESTTagInCategoryCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCategoryRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTCategoryCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
