package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.wrapper.TopicWrapper;

public class RESTTopicCollectionV1Wrapper extends RESTCollectionWrapper<TopicWrapper, RESTTopicV1,
        RESTTopicCollectionV1> implements CollectionWrapper<TopicWrapper> {

    public RESTTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> entityExpandedMethods) {
        super(providerFactory, collection, isRevisionCollection, entityExpandedMethods);
    }
}
