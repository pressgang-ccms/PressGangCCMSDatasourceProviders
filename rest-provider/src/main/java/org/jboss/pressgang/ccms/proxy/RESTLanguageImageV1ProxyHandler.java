package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTLanguageImageProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;

public class RESTLanguageImageV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTLanguageImageV1> {

    public RESTLanguageImageV1ProxyHandler(RESTProviderFactory providerFactory, RESTLanguageImageV1 entity, boolean isRevisionEntity,
            RESTImageV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTLanguageImageProvider getProvider() {
        return getProviderFactory().getProvider(RESTLanguageImageProvider.class);
    }

    @Override
    public Object internalInvoke(RESTLanguageImageV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && entity.getId() >= 0 && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getImageData")) {
                    retValue = getProvider().getLanguageImageData(entity.getId(), getEntityRevision(), getParent());
                    entity.setImageData((byte[]) retValue);
                } else if (methodName.equals("getImageDataBase64")) {
                    retValue = getProvider().getLanguageImageDataBase64(entity.getId(), getEntityRevision(), getParent());
                    entity.setImageDataBase64((byte[]) retValue);
                } else if (methodName.equals("getThumbnail")) {
                    retValue = getProvider().getLanguageImageThumbnail(entity.getId(), getEntityRevision(), getParent());
                    entity.setThumbnail((byte[]) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTLanguageImageRevisions(entity.getId(), getEntityRevision(), getParent());
                    entity.setRevisions((RESTLanguageImageCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTImageV1 getParent() {
        return (RESTImageV1) super.getParent();
    }
}
