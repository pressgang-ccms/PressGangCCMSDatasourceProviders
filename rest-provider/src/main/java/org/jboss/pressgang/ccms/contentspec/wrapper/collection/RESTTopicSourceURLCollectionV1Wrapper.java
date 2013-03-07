package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TopicSourceURLWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;

public class RESTTopicSourceURLCollectionV1Wrapper extends RESTCollectionWrapper<TopicSourceURLWrapper, RESTTopicSourceUrlV1,
        RESTTopicSourceUrlCollectionV1> implements CollectionWrapper<TopicSourceURLWrapper> {

    public RESTTopicSourceURLCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicSourceUrlCollectionV1 collection,
            boolean isRevisionCollection, final RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
