package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSInfoNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSInfoNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;

public class RESTCSNodeInfoV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSInfoNodeV1> {

    public RESTCSNodeInfoV1ProxyHandler(final RESTProviderFactory providerFactory, RESTCSInfoNodeV1 entity, boolean isRevisionEntity,
            final RESTCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTCSInfoNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSInfoNodeProvider.class);
    }

    @Override
    public Object internalInvoke(RESTCSInfoNodeV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCSNodeInfoRevisions(getParent());
                    entity.setRevisions((RESTCSInfoNodeCollectionV1) retValue);
                } else if (methodName.equals("getInheritedCondition")) {
                    retValue = getProvider().getRESTCSNodeInheritedCondition(getParent());
                    entity.setInheritedCondition((String) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTCSNodeV1 getParent() {
        return (RESTCSNodeV1) super.getParent();
    }
}
