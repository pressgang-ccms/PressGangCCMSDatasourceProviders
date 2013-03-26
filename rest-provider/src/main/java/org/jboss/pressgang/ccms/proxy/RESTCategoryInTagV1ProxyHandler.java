package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCategoryInTagProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;

public class RESTCategoryInTagV1ProxyHandler<T extends RESTBaseCategoryV1<T, ?, ?>> extends RESTBaseEntityV1ProxyHandler<T> {

    public RESTCategoryInTagV1ProxyHandler(RESTProviderFactory providerFactory, T entity, boolean isRevisionEntity,
            final RESTBaseTagV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    public RESTCategoryInTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTCategoryInTagProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTBaseCategoryV1<?, ?, ?> category = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (category.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(category, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTags")) {
                    retValue = getProvider().getRESTCategoryTags(category.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCategoryInTagRevisions(category.getId(), getEntityRevision(), getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }

    @Override
    protected RESTBaseTagV1<?, ?, ?> getParent() {
        return (RESTBaseTagV1<?, ?, ?>) super.getParent();
    }
}
