package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTFileProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;

public class RESTFileV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTFileV1> {
    public RESTFileV1ProxyHandler(RESTProviderFactory providerFactory, RESTFileV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTFileProvider getProvider() {
        return getProviderFactory().getProvider(RESTFileProvider.class);
    }

    @Override
    public Object internalInvoke(RESTFileV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getLanguageFiles_OTM")) {
                    retValue = getProvider().getRESTFileLanguageFiles(entity.getId(), getEntityRevision());
                    entity.setLanguageFiles_OTM((RESTLanguageFileCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTFileRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTFileCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
