package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTextContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.TextContentSpecWrapper;

public class RESTTextContentSpecCollectionV1Wrapper extends RESTCollectionWrapper<TextContentSpecWrapper, RESTTextContentSpecV1,
        RESTTextContentSpecCollectionV1> implements CollectionWrapper<TextContentSpecWrapper> {

    public RESTTextContentSpecCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTTextContentSpecCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTTextContentSpecCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTTextContentSpecCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
