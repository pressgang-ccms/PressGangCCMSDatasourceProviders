package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTagInCategoryProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTTagInCategoryV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTagInCategoryV1> {
    private final RESTBaseCategoryV1<?, ?, ?> parent;

    public RESTTagInCategoryV1ProxyHandler(RESTProviderFactory providerFactory, RESTTagInCategoryV1 entity, boolean isRevisionEntity,
            final RESTBaseCategoryV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    protected RESTTagInCategoryProvider getProvider() {
        return getProviderFactory().getProvider(RESTTagInCategoryProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTagInCategoryV1 tag = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (tag.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(tag, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getCategories")) {
                    final CollectionWrapper<CategoryInTagWrapper> categories = getProvider().getTagCategories(tag.getId(),
                            getEntityRevision());
                    retValue = categories == null ? null : categories.unwrap();
                } else if (methodName.equals("getProperties")) {
                    final CollectionWrapper<PropertyTagInTagWrapper> properties = getProvider().getTagProperties(tag.getId(),
                            getEntityRevision());
                    retValue = properties == null ? null : properties.unwrap();
                } else if (methodName.equals("getChildTags")) {
                    final CollectionWrapper<TagWrapper> childTags = getProvider().getTagChildTags(tag.getId(), getEntityRevision());
                    retValue = childTags == null ? null : childTags.unwrap();
                } else if (methodName.equals("getParentTags")) {
                    final CollectionWrapper<TagWrapper> parentTags = getProvider().getTagParentTags(tag.getId(), getEntityRevision());
                    retValue = parentTags == null ? null : parentTags.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<TagInCategoryWrapper> revisions = getProvider().getTagInCategoryRevisions(tag.getId(),
                            getEntityRevision(), parent);
                    retValue = revisions == null ? null : revisions;
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
