package org.jboss.pressgang.ccms.contentspec.proxy;

import java.lang.reflect.Method;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.provider.RESTTranslatedTopicProvider;
import org.jboss.pressgang.ccms.contentspec.proxy.collection.RESTCollectionProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;

public class RESTTranslatedTopicV1ProxyHandler extends RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1> {

    private final RESTTranslatedTopicProvider provider;

    public RESTTranslatedTopicV1ProxyHandler(final RESTProviderFactory providerFactory, final RESTTranslatedTopicV1 entity,
            boolean isRevision) {
        super(providerFactory, entity, isRevision);
        provider = providerFactory.getProvider(RESTTranslatedTopicProvider.class);
    }

    protected RESTTranslatedTopicProvider getProvider() {
        return provider;
    }

    @Override
    public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        final RESTTranslatedTopicV1 topic = getEntity();
        Object retValue = thisMethod.invoke(topic, args);
        // Check that there is an id defined and the method called is a getter otherwise we can't proxy the object
        if (retValue == null && topic.getId() != null && thisMethod.getName().startsWith("get")) {
            final String methodName = thisMethod.getName();

            if (methodName.equals("getTags")) {
                final CollectionWrapper<TagWrapper> tags = getProvider().getTranslatedTopicTags(topic.getId(), getEntityRevision());
                retValue = tags == null ? null : tags.unwrap();
            } else if (methodName.equals("getSourceUrls_OTM")) {
                final CollectionWrapper<TopicSourceURLWrapper> sourceURLs = getProvider().getTranslatedTopicSourceUrls(topic.getId(),
                        getEntityRevision(), getProxyEntity());
                retValue = sourceURLs == null ? null : sourceURLs.unwrap();
            } else if (methodName.equals("getProperties")) {
                final CollectionWrapper<PropertyTagInTopicWrapper> properties = getProvider().getTranslatedTopicProperties(topic.getId(),
                        getEntityRevision());
                retValue = properties == null ? null : properties.unwrap();
            } else if (methodName.equals("getOutgoingRelationships")) {
                final CollectionWrapper<TranslatedTopicWrapper> outgoingRelationships = getProvider()
                        .getTranslatedTopicOutgoingRelationships(topic.getId(), getEntityRevision());
                retValue = outgoingRelationships == null ? null : outgoingRelationships.unwrap();
            } else if (methodName.equals("getIncomingRelationships")) {
                final CollectionWrapper<TranslatedTopicWrapper> incomingRelationships = getProvider()
                        .getTranslatedTopicIncomingRelationships(topic.getId(), getEntityRevision());
                retValue = incomingRelationships == null ? null : incomingRelationships.unwrap();
            } else if (methodName.equals("getTranslatedTopicStrings_OTM")) {
                final CollectionWrapper<TranslatedTopicStringWrapper> translatedTopicStrings = getProvider()
                        .getTranslatedTopicStrings(topic.getId(), getEntityRevision(), getProxyEntity());
                retValue = translatedTopicStrings == null ? null : translatedTopicStrings.unwrap();
            } else if (methodName.equals("getRevisions")) {
                final CollectionWrapper<TranslatedTopicWrapper> revisions = getProvider().getTranslatedTopicRevisions(topic.getId(),
                        getEntityRevision());
                retValue = revisions == null ? null : revisions.unwrap();
            }

            // Check if the returned object is a collection instance, if so proxy the collections items.
            if (retValue != null && retValue instanceof RESTBaseCollectionV1) {
                return RESTCollectionProxyFactory.create(getProviderFactory(), (RESTBaseCollectionV1) retValue,
                        getEntityRevision() != null, getProxyEntity());
            } else {
                return retValue;
            }
        }

        return super.invoke(proxy, thisMethod, proceed, args);
    }
}
