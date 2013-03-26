package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTPropertyTagInPropertyCategoryProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
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
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTPropertyTagInPropertyCategoryV1 propertyTag = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (propertyTag.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(propertyTag, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTPropertyTagInPropertyCategoryRevisions(propertyTag.getId(), getEntityRevision(),
                            getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }

    @Override
    protected RESTPropertyCategoryV1 getParent() {
        return (RESTPropertyCategoryV1) super.getParent();
    }
}
