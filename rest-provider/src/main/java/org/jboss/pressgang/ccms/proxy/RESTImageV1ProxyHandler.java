package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTImageProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;

public class RESTImageV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTImageV1> {
    public RESTImageV1ProxyHandler(RESTProviderFactory providerFactory, RESTImageV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTImageProvider getProvider() {
        return getProviderFactory().getProvider(RESTImageProvider.class);
    }

    @Override
    public Object internalInvoke(RESTImageV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getLanguageImages_OTM")) {
                    retValue = getProvider().getRESTImageLanguageImages(entity.getId(), getEntityRevision());
                    entity.setLanguageImages_OTM((RESTLanguageImageCollectionV1) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTImageRevisions(entity.getId(), getEntityRevision());
                    entity.setRevisions((RESTImageCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }
}
