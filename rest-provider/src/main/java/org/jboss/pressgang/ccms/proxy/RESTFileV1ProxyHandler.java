package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTFileProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;

public class RESTFileV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTFileV1> {
    public RESTFileV1ProxyHandler(RESTProviderFactory providerFactory, RESTFileV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTFileProvider getProvider() {
        return getProviderFactory().getProvider(RESTFileProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTFileV1 file = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (file.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(file, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getLanguageFiles_OTM")) {
                    retValue = getProvider().getRESTFileLanguageFiles(file.getId(), getEntityRevision());
                } else if (methodName.equals("getRevisions")) {
                    retValue = getProvider().getRESTFileRevisions(file.getId(), getEntityRevision());
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            return checkAndProxyReturnValue(retValue);
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
