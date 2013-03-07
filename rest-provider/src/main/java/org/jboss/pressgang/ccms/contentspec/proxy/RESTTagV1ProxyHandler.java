package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.provider.RESTTagProvider;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;

public class RESTTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTagV1> {

    public RESTTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTTagV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    protected RESTTagProvider getProvider() {
        return getProviderFactory().getProvider(RESTTagProvider.class);
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTagV1 tag = getEntity();
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
                    final CollectionWrapper<TagWrapper> revisions = getProvider().getTagRevisions(tag.getId(), getEntityRevision());
                    retValue = revisions == null ? null : revisions;
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
