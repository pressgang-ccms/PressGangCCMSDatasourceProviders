package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.wrapper.TopicSourceURLWrapper;

public class RESTTopicSourceURLCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<TopicSourceURLWrapper, RESTTopicSourceUrlV1,
        RESTTopicSourceUrlCollectionV1> implements CollectionWrapper<TopicSourceURLWrapper> {

    public RESTTopicSourceURLCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicSourceUrlCollectionV1 collection,
            boolean isRevisionCollection, final RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
