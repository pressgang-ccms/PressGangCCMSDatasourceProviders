package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTBlobConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;

public class RESTBlobConstantCollectionV1Wrapper extends RESTCollectionWrapper<BlobConstantWrapper, RESTBlobConstantV1,
        RESTBlobConstantCollectionV1> implements CollectionWrapper<BlobConstantWrapper> {

    public RESTBlobConstantCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTBlobConstantCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
