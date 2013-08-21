package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedTopicStringProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;

public class RESTTranslatedTopicStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicStringV1> {

    public RESTTranslatedTopicStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTTranslatedTopicStringV1 entity,
            boolean isRevisionEntity, final RESTTranslatedTopicV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTTranslatedTopicStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedTopicStringProvider.class);
    }

    @Override
    public Object internalInvoke(RESTTranslatedTopicStringV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTTranslatedTopicStringRevisions(entity.getId(), getEntityRevision(),
                            getParent());
                    entity.setRevisions((RESTTranslatedTopicStringCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTTranslatedTopicV1 getParent() {
        return (RESTTranslatedTopicV1) super.getParent();
    }
}
