package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;

public class RESTTopicCollectionV1Wrapper extends RESTCollectionWrapper<TopicWrapper, RESTTopicV1,
        RESTTopicCollectionV1> implements CollectionWrapper<TopicWrapper> {

    public RESTTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
