package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.provider.RESTTopicSourceURLProvider;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;

public class RESTTopicSourceUrlV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTopicSourceUrlV1> {

    private final RESTBaseTopicV1<?, ?, ?> parent;

    public RESTTopicSourceUrlV1ProxyHandler(final RESTProviderFactory providerFactory, RESTTopicSourceUrlV1 entity,
            boolean isRevisionEntity, final RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevisionEntity);
        this.parent = parent;
    }

    protected RESTTopicSourceURLProvider getProvider() {
        return getProviderFactory().getProvider(RESTTopicSourceURLProvider.class);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTopicSourceUrlV1 topicSourceUrl = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (topicSourceUrl.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(topicSourceUrl, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<TopicSourceURLWrapper> revisions = getProvider().getTopicSourceURLRevisions(topicSourceUrl.getId(),
                            getEntityRevision(), parent);
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue,
                        getEntityRevision() != null, parent);
            } else {
                return retValue;
            }
        }

        return super.invoke(self, thisMethod, proceed, args);
    }

}
