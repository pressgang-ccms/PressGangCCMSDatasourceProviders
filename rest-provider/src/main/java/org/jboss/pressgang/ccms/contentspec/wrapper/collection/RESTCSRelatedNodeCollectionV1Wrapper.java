package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;

public class RESTCSRelatedNodeCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CSRelatedNodeWrapper, RESTCSRelatedNodeV1,
        RESTCSRelatedNodeCollectionV1> implements CollectionWrapper<CSRelatedNodeWrapper> {

    public RESTCSRelatedNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
