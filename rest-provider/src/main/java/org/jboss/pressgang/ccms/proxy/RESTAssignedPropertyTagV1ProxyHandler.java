package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTPropertyTagInContentSpecProvider;
import org.jboss.pressgang.ccms.provider.RESTPropertyTagInTagProvider;
import org.jboss.pressgang.ccms.provider.RESTPropertyTagInTopicProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTAssignedPropertyTagV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTAssignedPropertyTagV1> {
    private final RESTBaseEntityV1<?, ?, ?> parent;

    public RESTAssignedPropertyTagV1ProxyHandler(RESTProviderFactory providerFactory, RESTAssignedPropertyTagV1 entity,
            boolean isRevisionEntity, final RESTBaseEntityV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTAssignedPropertyTagV1 propertyTag = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (propertyTag.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(propertyTag, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    if (parent instanceof RESTContentSpecV1) {
                        final RESTPropertyTagInContentSpecProvider provider = getProviderFactory().getProvider(
                                RESTPropertyTagInContentSpecProvider.class);
                        final CollectionWrapper<PropertyTagInContentSpecWrapper> revisions = provider.getPropertyTagInContentSpecRevisions(
                                propertyTag.getId(), getEntityRevision(), (RESTContentSpecV1) parent);
                        retValue = revisions == null ? null : revisions.unwrap();
                    } else if (parent instanceof RESTBaseTopicV1) {
                        final RESTPropertyTagInTopicProvider provider = getProviderFactory().getProvider(
                                RESTPropertyTagInTopicProvider.class);
                        final CollectionWrapper<PropertyTagInTopicWrapper> revisions = provider.getPropertyTagInTopicRevisions(
                                propertyTag.getId(), getEntityRevision(), (RESTBaseTopicV1) parent);
                        retValue = revisions == null ? null : revisions.unwrap();
                    } else if (parent instanceof RESTBaseTagV1) {
                        final RESTPropertyTagInTagProvider provider = getProviderFactory().getProvider(RESTPropertyTagInTagProvider.class);
                        final CollectionWrapper<PropertyTagInTagWrapper> revisions = provider.getPropertyTagInTagRevisions(
                                propertyTag.getId(), getEntityRevision(), (RESTBaseTagV1<?, ?, ?>) parent);
                        retValue = revisions == null ? null : revisions.unwrap();
                    }
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
