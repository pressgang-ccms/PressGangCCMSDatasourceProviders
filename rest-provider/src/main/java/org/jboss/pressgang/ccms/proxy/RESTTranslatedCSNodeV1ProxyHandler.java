package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTTranslatedCSNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTTranslatedCSNodeV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedCSNodeV1> {
    public RESTTranslatedCSNodeV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeV1 entity,
            boolean isRevisionEntity) {
        super(providerFactory, entity, isRevisionEntity);
    }

    public RESTTranslatedCSNodeProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedCSNodeProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedCSNodeV1 csNode = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (csNode.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(csNode, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTranslatedNodeStrings_OTM")) {
                    final CollectionWrapper<TranslatedCSNodeStringWrapper> children = getProvider().getTranslatedCSNodeStrings(
                            csNode.getId(), getEntityRevision(), getProxyEntity());
                    retValue = children == null ? null : children.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<TranslatedCSNodeWrapper> revisions = getProvider().getTranslatedCSNodeRevisions(csNode.getId(),
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
