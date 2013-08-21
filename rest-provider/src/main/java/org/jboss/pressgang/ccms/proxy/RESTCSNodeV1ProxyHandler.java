package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;

public class RESTCSNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSNodeV1> {
    public RESTCSNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTCSNodeV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTCSNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSNodeProvider.class);
    }

    @Override
    public Object internalInvoke(RESTCSNodeV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRelatedFromNodes")) {
                    retValue = getProvider().getRESTCSRelatedFromNodes(entity.getId(), getEntityRevision());
                    entity.setRelatedFromNodes((RESTCSRelatedNodeCollectionV1) retValue);
                } else if (methodName.equals("getRelatedToNodes")) {
                    retValue = getProvider().getRESTCSRelatedToNodes(entity.getId(), getEntityRevision());
                    entity.setRelatedToNodes((RESTCSRelatedNodeCollectionV1) retValue);
                } else if (methodName.equals("getChildren_OTM")) {
                    retValue = getProvider().getRESTCSNodeChildren(entity.getId(), getEntityRevision());
                    entity.setChildren_OTM((RESTCSNodeCollectionV1) retValue);
                } else if (methodName.equals("getNextNode")) {
                    retValue = getProvider().getRESTCSNextNode(entity.getId(), getEntityRevision());
                    entity.setNextNode((RESTCSNodeV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTCSNodeRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTCSNodeCollectionV1) retValue);
                } else if (methodName.equals("getParent")) {
                    retValue = getProvider().getRESTCSNodeParent(entity.getId(), getEntityRevision());
                    entity.setParent((RESTCSNodeV1) retValue);
                } else if (methodName.equals("getContentSpec")) {
                    retValue = getProvider().getRESTCSNodeContentSpec(entity.getId(), getEntityRevision());
                    entity.setContentSpec((RESTContentSpecV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
