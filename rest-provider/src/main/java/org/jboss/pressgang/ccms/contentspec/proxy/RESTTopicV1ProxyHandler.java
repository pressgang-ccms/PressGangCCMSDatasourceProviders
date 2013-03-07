package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.provider.RESTTopicProvider;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;

public class RESTTopicV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTopicV1> {

    private final RESTTopicProvider provider;

    public RESTTopicV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTopicV1 topic, final boolean isRevision) {
        super(providerFactory, topic, isRevision);
        provider = providerFactory.getProvider(RESTTopicProvider.class);
    }

    protected RESTTopicProvider getProvider() {
        return provider;
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTopicV1 topic = getEntity();
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (topic.getId() != null && thisMethod.getName().startsWith("get")) {
            Object retValue = thisMethod.invoke(topic, args);
            if (retValue == null) {
                final String methodName = thisMethod.getName();

                if (methodName.equals("getTags")) {
                    final CollectionWrapper<TagWrapper> tags = getProvider().getTopicTags(topic.getId(), getEntityRevision());
                    retValue = tags == null ? null : tags.unwrap();
                } else if (methodName.equals("getSourceUrls_OTM")) {
                    final CollectionWrapper<TopicSourceURLWrapper> sourceURLs = getProvider().getTopicSourceUrls(topic.getId(),
                            getEntityRevision(), getProxyEntity());
                    retValue = sourceURLs == null ? null : sourceURLs.unwrap();
                } else if (methodName.equals("getProperties")) {
                    final CollectionWrapper<PropertyTagInTopicWrapper> properties = getProvider().getTopicProperties(topic.getId(),
                            getEntityRevision());
                    retValue = properties == null ? null : properties.unwrap();
                } else if (methodName.equals("getOutgoingRelationships")) {
                    final CollectionWrapper<TopicWrapper> outgoingRelationships = getProvider().getTopicOutgoingRelationships(topic.getId(),
                            getEntityRevision());
                    retValue = outgoingRelationships == null ? null : outgoingRelationships.unwrap();
                } else if (methodName.equals("getIncomingRelationships")) {
                    final CollectionWrapper<TopicWrapper> incomingRelationships = getProvider().getTopicIncomingRelationships(topic.getId(),
                            getEntityRevision());
                    retValue = incomingRelationships == null ? null : incomingRelationships.unwrap();
                } else if (methodName.equals("getTranslatedTopics_OTM")) {
                    final CollectionWrapper<TranslatedTopicWrapper> translatedTopics = getProvider().getTopicTranslations(topic.getId(),
                            getEntityRevision());
                    retValue = translatedTopics == null ? null : translatedTopics.unwrap();
                } else if (methodName.equals("getRevisions")) {
                    final CollectionWrapper<TopicWrapper> revisions = getProvider().getTopicRevisions(topic.getId(), getEntityRevision());
                    retValue = revisions == null ? null : revisions.unwrap();
                }
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue, getEntityRevision() != null,
                        getProxyEntity());
            } else {
                return retValue;
            }
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
