package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTagProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;

public class RESTTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTagV1> {

    public RESTTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTTagV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTTagProvider.class);
    }

    @Override
    public Object internalInvoke(RESTTagV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getCategories")) {
                    retValue = getProvider().getRESTTagCategories(entity.getId(), getEntityRevision());
                    entity.setCategories((RESTCategoryInTagCollectionV1) retValue);
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTTagProperties(entity.getId(), getEntityRevision());
                    entity.setProperties((RESTAssignedPropertyTagCollectionV1) retValue);
                } else if (methodName.equals("getChildTags")) {
                    retValue = getProvider().getRESTTagChildTags(entity.getId(), getEntityRevision());
                    entity.setChildTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getParentTags")) {
                    retValue = getProvider().getRESTTagParentTags(entity.getId(), getEntityRevision());
                    entity.setParentTags((RESTTagCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTagRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTTagCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
