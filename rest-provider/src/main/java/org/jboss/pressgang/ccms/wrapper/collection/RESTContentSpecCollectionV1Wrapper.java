package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;

public class RESTContentSpecCollectionV1Wrapper extends RESTCollectionWrapper<ContentSpecWrapper, RESTContentSpecV1,
        RESTContentSpecCollectionV1> implements CollectionWrapper<ContentSpecWrapper> {

    public RESTContentSpecCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTContentSpecCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTContentSpecCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTContentSpecCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
