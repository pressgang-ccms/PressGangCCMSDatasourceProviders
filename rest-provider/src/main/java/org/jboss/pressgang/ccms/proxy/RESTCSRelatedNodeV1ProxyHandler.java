package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSRelatedNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;

public class RESTCSRelatedNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSRelatedNodeV1> {

    public RESTCSRelatedNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeV1 entity,
            boolean isRevisionEntity, final RESTCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    public RESTCSRelatedNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSRelatedNodeProvider.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTCSRelatedNodeV1 relatedNode = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (relatedNode.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(relatedNode, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCSRelatedNodeRevisions(relatedNode.getId(), getEntityRevision(), getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

    @Override
    protected RESTCSNodeV1 getParent() {
        return (RESTCSNodeV1) super.getParent();
    }
}
