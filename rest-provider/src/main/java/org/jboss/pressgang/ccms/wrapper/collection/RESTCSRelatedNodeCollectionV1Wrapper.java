package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;

public class RESTCSRelatedNodeCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CSRelatedNodeWrapper, RESTCSRelatedNodeV1,
        RESTCSRelatedNodeCollectionV1> implements CollectionWrapper<CSRelatedNodeWrapper> {

    public RESTCSRelatedNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
