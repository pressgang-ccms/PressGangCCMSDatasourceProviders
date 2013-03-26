package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTagInCategoryProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTTagInCategoryV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTagInCategoryV1> {

    public RESTTagInCategoryV1ProxyHandler(RESTProviderFactory providerFactory, RESTTagInCategoryV1 entity, boolean isRevisionEntity,
            final RESTBaseCategoryV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTTagInCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTTagInCategoryProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTagInCategoryV1 tag = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (tag.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(tag, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getCategories")) {
                    retValue = getProvider().getRESTTagCategories(tag.getId(), getEntityRevision());
                } else if (methodName.equals("getProperties")) {
                    retValue = getProvider().getRESTTagProperties(tag.getId(), getEntityRevision());
                } else if (methodName.equals("getChildTags")) {
                    retValue = getProvider().getRESTTagChildTags(tag.getId(), getEntityRevision());
                } else if (methodName.equals("getParentTags")) {
                    retValue = getProvider().getRESTTagParentTags(tag.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTagInCategoryRevisions(tag.getId(), getEntityRevision(), getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }

    @Override
    protected RESTBaseCategoryV1<?, ?, ?> getParent() {
        return (RESTBaseCategoryV1<?, ?, ?>) super.getParent();
    }
}
