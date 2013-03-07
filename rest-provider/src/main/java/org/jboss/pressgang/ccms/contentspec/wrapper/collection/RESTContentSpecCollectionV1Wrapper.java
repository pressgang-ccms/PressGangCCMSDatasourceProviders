package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;

public class RESTContentSpecCollectionV1Wrapper extends RESTCollectionWrapper<ContentSpecWrapper, RESTContentSpecV1,
        RESTContentSpecCollectionV1> implements CollectionWrapper<ContentSpecWrapper> {

    public RESTContentSpecCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTContentSpecCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
