package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTCSTranslatedNodeStringProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTCSTranslatedNodeStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTCSTranslatedNodeStringV1> {
    private final RESTCSTranslatedNodeV1 parent;

    public RESTCSTranslatedNodeStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTCSTranslatedNodeStringV1 entity,
            boolean isRevisionEntity, final RESTCSTranslatedNodeV1 parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    protected RESTCSTranslatedNodeStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTCSTranslatedNodeStringProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTCSTranslatedNodeStringV1 translatedTopicString = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (translatedTopicString.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(translatedTopicString, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<CSTranslatedNodeStringWrapper> revisions = getProvider().getCSTranslatedNodeStringRevisions(
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
