package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTLanguageFileProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
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
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTLanguageFileV1 languageFile = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (languageFile.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(languageFile, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getFileData")) {
                    retValue = getProvider().getLanguageFileData(languageFile.getId(), getEntityRevision(), getParent());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTLanguageFileRevisions(languageFile.getId(), getEntityRevision(), getParent());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

    @Override
    protected RESTFileV1 getParent() {
        return (RESTFileV1) super.getParent();
    }
}
