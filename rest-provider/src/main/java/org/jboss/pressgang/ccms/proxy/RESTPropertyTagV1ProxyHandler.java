package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTPropertyTagProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTPropertyTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTPropertyTagV1> {

    public RESTPropertyTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTPropertyTagV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTPropertyTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTPropertyTagProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTPropertyTagV1 propertyTag = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (propertyTag.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(propertyTag, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<PropertyTagWrapper> revisions = getProvider().getPropertyTagRevisions(propertyTag.getId(),
                            getEntityRevision());
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

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
