package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSRelatedNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTCSRelatedNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSRelatedNodeV1> {
    private final RESTCSNodeV1 parent;

    public RESTCSRelatedNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeV1 entity,
            boolean isRevisionEntity, final RESTCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    public RESTCSRelatedNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSRelatedNodeProvider.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTCSRelatedNodeV1 relatedNode = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (relatedNode.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(relatedNode, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<CSRelatedNodeWrapper> revisions = getProvider().getCSRelatedNodeRevisions(relatedNode.getId(),
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

        return super.invoke(self, thisMethod, proceed, args);
    }
}
