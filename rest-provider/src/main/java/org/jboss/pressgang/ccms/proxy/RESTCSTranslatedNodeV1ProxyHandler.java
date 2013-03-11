package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSTranslatedNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTCSTranslatedNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSTranslatedNodeV1> {
    public RESTCSTranslatedNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTCSTranslatedNodeV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTCSTranslatedNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSTranslatedNodeProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTCSTranslatedNodeV1 csNode = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (csNode.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(csNode, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTranslatedNodeStrings_OTM")) {
                    final CollectionWrapper<CSTranslatedNodeStringWrapper> children = getProvider().getCSTranslatedNodeStrings(
                            csNode.getId(), getEntityRevision(), getProxyEntity());
                    retValue = children == null ? null : children.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<CSTranslatedNodeWrapper> revisions = getProvider().getCSTranslatedNodeRevisions(csNode.getId(),
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
