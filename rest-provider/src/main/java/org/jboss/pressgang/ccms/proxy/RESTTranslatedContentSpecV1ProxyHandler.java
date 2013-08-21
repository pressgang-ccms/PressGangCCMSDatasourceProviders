package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedContentSpecProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;

public class RESTTranslatedContentSpecV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedContentSpecV1> {
    public RESTTranslatedContentSpecV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTranslatedContentSpecV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTTranslatedContentSpecProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedContentSpecProvider.class);
    }

    @Override
    public Object internalInvoke(RESTTranslatedContentSpecV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getTranslatedNodes_OTM")) {
                    retValue = getProvider().getRESTTranslatedNodes(entity.getId(), getEntityRevision());
                    entity.setTranslatedNodes_OTM((RESTTranslatedCSNodeCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedContentSpecRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTTranslatedContentSpecCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
