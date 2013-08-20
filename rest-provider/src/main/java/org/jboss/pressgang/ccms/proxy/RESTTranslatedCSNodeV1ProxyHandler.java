package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedCSNodeProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;

public class RESTTranslatedCSNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedCSNodeV1> {
    public RESTTranslatedCSNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTTranslatedCSNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedCSNodeProvider.class);
    }

    @Override
    public Object internalInvoke(RESTTranslatedCSNodeV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getTranslatedNodeStrings_OTM")) {
                    retValue = getProvider().getRESTTranslatedCSNodeStrings(entity.getId(), getEntityRevision());
                    entity.setTranslatedNodeStrings_OTM((RESTTranslatedCSNodeStringCollectionV1) retValue);
                } else if (methodName.equals("getTranslatedTopic")) {
                    retValue = getProvider().getRESTTranslatedCSNodeTranslatedTopic(entity.getId(), getEntityRevision());
                    entity.setTranslatedTopic((RESTTranslatedTopicV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedCSNodeRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTTranslatedCSNodeCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
