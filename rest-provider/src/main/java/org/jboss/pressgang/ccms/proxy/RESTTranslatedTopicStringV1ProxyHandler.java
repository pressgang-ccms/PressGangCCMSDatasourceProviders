package org.jboss.pressgang.ccms.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.provider.RESTTranslatedTopicStringProvider;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionProxyFactory;

public class RESTTranslatedTopicStringV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicStringV1> {
    private final RESTTranslatedTopicV1 parent;

    public RESTTranslatedTopicStringV1ProxyHandler(RESTProviderFactory providerFactory, RESTTranslatedTopicStringV1 entity,
            boolean isRevisionEntity, final RESTTranslatedTopicV1 parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    protected RESTTranslatedTopicStringProvider getProvider() {
        return getProviderFactory().getProvider(RESTTranslatedTopicStringProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedTopicStringV1 translatedTopicString = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (translatedTopicString.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(translatedTopicString, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<TranslatedTopicStringWrapper> revisions = getProvider().getTranslatedTopicStringRevisions(
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
