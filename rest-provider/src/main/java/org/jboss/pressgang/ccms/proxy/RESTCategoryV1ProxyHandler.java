package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCategoryProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTCategoryV1ProxyHandler<T extends RESTBaseCategoryV1<T, ?, ?>> extends RESTBaseEntityV1ProxyHandler<T> {

    public RESTCategoryV1ProxyHandler(RESTProviderFactory providerFactory, T entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTCategoryProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTBaseCategoryV1<?, ?, ?> category = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (category.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(category, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTags")) {
                    final UpdateableCollectionWrapper<TagInCategoryWrapper> tags = getProvider().getCategoryTags(category.getId(),
                            getEntityRevision());
                    retValue = tags == null ? null : tags.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<CategoryWrapper> revisions = getProvider().getCategoryRevisions(category.getId(),
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
