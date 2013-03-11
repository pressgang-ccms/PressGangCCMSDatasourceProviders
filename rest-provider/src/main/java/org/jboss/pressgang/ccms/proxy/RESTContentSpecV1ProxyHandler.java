package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTContentSpecProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTContentSpecV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTContentSpecV1> {
    public RESTContentSpecV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTContentSpecV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTContentSpecProvider getProvider() {
        return getProviderFactory().getProvider(RESTContentSpecProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTContentSpecV1 contentSpec = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (contentSpec.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(contentSpec, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getChildren_OTM")) {
                    final CollectionWrapper<CSNodeWrapper> nodes = getProvider().getContentSpecNodes(contentSpec.getId(),
                            getEntityRevision());
                    retValue = nodes == null ? null : nodes.unwrap();
                } else if (methodName.equals("getProperties")) {
                    final CollectionWrapper<PropertyTagInContentSpecWrapper> properties = getProvider().getContentSpecProperties(
                            contentSpec.getId(), getEntityRevision());
                    retValue = properties == null ? null : properties.unwrap();
                } else if (methodName.equals("getTags")) {
                    final CollectionWrapper<TagWrapper> tags = getProvider().getContentSpecTags(contentSpec.getId(), getEntityRevision());
                    retValue = tags == null ? null : tags.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<ContentSpecWrapper> revisions = getProvider().getContentSpecRevisions(contentSpec.getId(),
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

        return super.invoke(self, thisMethod, proceed, args);
    }
}
