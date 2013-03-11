package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCategoryInTagProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTCategoryInTagV1ProxyHandler<T extends RESTBaseCategoryV1<T, ?, ?>> extends RESTBaseEntityV1ProxyHandler<T> {
    private final RESTBaseTagV1<?, ?, ?> parent;

    public RESTCategoryInTagV1ProxyHandler(RESTProviderFactory providerFactory, T entity, boolean isRevisionEntity,
            final RESTBaseTagV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    public RESTCategoryInTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTCategoryInTagProvider.class);
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
                    final CollectionWrapper<CategoryInTagWrapper> revisions = getProvider().getCategoryInTagRevisions(category.getId(),
                            getEntityRevision(), parent);
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue, getEntityRevision() != null,
                        parent);
            } else {
                return retValue;
            }
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
