package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTCSNodeProvider;
import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;

public class RESTCSNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSNodeV1> {
    public RESTCSNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTCSNodeV1 entity, boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTCSNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSNodeProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTCSNodeV1 contentSpec = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (contentSpec.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(contentSpec, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRelatedFromNodes")) {
                    final CollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes = getProvider().getCSRelatedFromNodes(
                            contentSpec.getId(), getEntityRevision());
                    retValue = relatedFromNodes == null ? null : relatedFromNodes.unwrap();
                } else if (methodName.equals("getRelatedToNodes")) {
                    final CollectionWrapper<CSRelatedNodeWrapper> relatedToNodes = getProvider().getCSRelatedToNodes(contentSpec.getId(),
                            getEntityRevision());
                    retValue = relatedToNodes == null ? null : relatedToNodes.unwrap();
                } else if (methodName.equals("getChildren")) {
                    final CollectionWrapper<CSNodeWrapper> children = getProvider().getCSNodeChildren(contentSpec.getId(),
                            getEntityRevision());
                    retValue = children == null ? null : children.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<CSNodeWrapper> revisions = getProvider().getCSNodeRevisions(contentSpec.getId(),
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
