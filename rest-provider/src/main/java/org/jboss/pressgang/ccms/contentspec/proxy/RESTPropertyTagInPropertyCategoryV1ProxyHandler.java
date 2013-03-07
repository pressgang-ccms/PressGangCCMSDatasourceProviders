package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTPropertyTagInPropertyCategoryProvider;
import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;

public class RESTPropertyTagInPropertyCategoryV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTPropertyTagInPropertyCategoryV1> {
    private final RESTPropertyCategoryV1 parent;

    public RESTPropertyTagInPropertyCategoryV1ProxyHandler(RESTProviderFactory providerFactory, RESTPropertyTagInPropertyCategoryV1 entity,
            boolean isRevisionEntity, final RESTPropertyCategoryV1 parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    public RESTPropertyTagInPropertyCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTPropertyTagInPropertyCategoryProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTPropertyTagInPropertyCategoryV1 propertyTag = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (propertyTag.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(propertyTag, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<PropertyTagInPropertyCategoryWrapper> revisions = getProvider()
                            .getPropertyTagInPropertyCategoryRevisions(propertyTag.getId(), getEntityRevision(), parent);
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue,
                        getEntityRevision() != null, parent);
            } else {
                return retValue;
            }
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
