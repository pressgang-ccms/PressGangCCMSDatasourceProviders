package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTLanguageImageProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
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
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTLanguageImageV1 languageImage = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (languageImage.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(languageImage, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getImageData")) {
                    retValue = getProvider().getLanguageImageData(languageImage.getId(), getEntityRevision(), getParent());
                } else if (methodName.equals("getImageDataBase64")) {
                    retValue = getProvider().getLanguageImageDataBase64(languageImage.getId(), getEntityRevision(), getParent());
                } else if (methodName.equals("getThumbnail")) {
                    retValue = getProvider().getLanguageImageThumbnail(languageImage.getId(), getEntityRevision(), getParent());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTLanguageImageRevisions(languageImage.getId(), getEntityRevision(), getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

    @Override
    protected RESTImageV1 getParent() {
        return (RESTImageV1) super.getParent();
    }
}
