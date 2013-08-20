package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCategoryInTagProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;

public class RESTCategoryInTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCategoryInTagV1> {

    public RESTCategoryInTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTCategoryInTagV1 entity, boolean isRevisionEntity,
            final RESTBaseTagV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    public RESTCategoryInTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTCategoryInTagProvider.class);
    }

    @Override
    public Object internalInvoke(RESTCategoryInTagV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTCategoryTags(entity.getId(), getEntityRevision());
                    entity.setTags((RESTTagInCategoryCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCategoryInTagRevisions(entity.getId(), getEntityRevision(), getParent());
                    entity.setRevisions((RESTCategoryInTagCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTBaseTagV1<?, ?, ?> getParent() {
        return (RESTBaseTagV1<?, ?, ?>) super.getParent();
    }
}
