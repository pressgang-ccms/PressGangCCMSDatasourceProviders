package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTTranslatedCSNodeStringProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTTranslatedCSNodeStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedCSNodeStringV1> {
    private final RESTTranslatedCSNodeV1 parent;

    public RESTTranslatedCSNodeStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTTranslatedCSNodeStringV1 entity,
            boolean isRevisionEntity, final RESTTranslatedCSNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    protected RESTTranslatedCSNodeStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedCSNodeStringProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedCSNodeStringV1 translatedTopicString = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (translatedTopicString.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(translatedTopicString, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<TranslatedCSNodeStringWrapper> revisions = getProvider().getCSTranslatedNodeStringRevisions(
                            translatedTopicString.getId(), getEntityRevision(), parent);
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
