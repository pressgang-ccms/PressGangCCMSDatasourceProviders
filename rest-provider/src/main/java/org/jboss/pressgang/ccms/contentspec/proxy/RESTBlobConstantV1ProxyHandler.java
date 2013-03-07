package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTBlobConstantProvider;
import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;

public class RESTBlobConstantV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTBlobConstantV1> {

    public RESTBlobConstantV1ProxyHandler(RESTProviderFactory providerFactory, RESTBlobConstantV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTBlobConstantProvider getProvider() {
        return getProviderFactory().getProvider(RESTBlobConstantProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTBlobConstantV1 blobConstant = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (blobConstant.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(blobConstant, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<BlobConstantWrapper> revisions = getProvider().getBlobConstantRevisions(blobConstant.getId(),
                            getEntityRevision());
                    retValue = revisions == null ? null : revisions.unwrap();
                } else if (methodName.equals("getValue")) {
                    return getProvider().getBlobConstantValue(blobConstant.getId(), getEntityRevision());
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
