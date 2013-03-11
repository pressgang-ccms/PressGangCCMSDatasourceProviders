package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTStringConstantProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTStringConstantV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTStringConstantV1> {

    public RESTStringConstantV1ProxyHandler(RESTProviderFactory providerFactory, RESTStringConstantV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTStringConstantProvider getProvider() {
        return getProviderFactory().getProvider(RESTStringConstantProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTStringConstantV1 stringConstant = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (stringConstant.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(stringConstant, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<StringConstantWrapper> revisions = getProvider().getStringConstantRevisions(
                            stringConstant.getId(), getEntityRevision());
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue,
                        getEntityRevision() != null);
            } else {
                return retValue;
            }
        }

        return super.invoke(self, thisMethod, proceed, args);
    }
}
