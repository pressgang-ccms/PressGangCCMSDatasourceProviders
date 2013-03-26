package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTagProvider;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;

public class RESTTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTagV1> {

    public RESTTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTTagV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTTagProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTagV1 tag = getEntity();
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
                    retValue = getProvider().getRESTTagRevisions(tag.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
