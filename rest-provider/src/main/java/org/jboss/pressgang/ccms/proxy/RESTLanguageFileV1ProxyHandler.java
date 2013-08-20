package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTLanguageFileProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;

public class RESTLanguageFileV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTLanguageFileV1> {

    public RESTLanguageFileV1ProxyHandler(RESTProviderFactory providerFactory, RESTLanguageFileV1 entity, boolean isRevisionEntity,
            RESTFileV1 parent) {
        super(providerFactory, entity, isRevisionEntity, parent);
    }

    protected RESTLanguageFileProvider getProvider() {
        return getProviderFactory().getProvider(RESTLanguageFileProvider.class);
    }

    @Override
    public Object internalInvoke(RESTLanguageFileV1 entity, Method method, Object[] args) throws Throwable {
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (entity.getId() != null && method.getName().startsWith("get")) {
            Object retValue = method.invoke(entity, args);
            if (retValue == null) {
                final String methodName = method.getName();

                if (methodName.equals("getFileData")) {
                    retValue = getProvider().getLanguageFileData(entity.getId(), getEntityRevision(), getParent());
                    entity.setFileData((byte[]) retValue);
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTLanguageFileRevisions(entity.getId(), getEntityRevision(), getParent());
                    entity.setRevisions((RESTLanguageFileCollectionV1) retValue);
                }
            }

            return retValue;
        }

        return super.internalInvoke(entity, method, args);
    }

    @Override
    protected RESTFileV1 getParent() {
        return (RESTFileV1) super.getParent();
    }
}
