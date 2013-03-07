package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTUserCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;

public class RESTUserCollectionV1Wrapper extends RESTCollectionWrapper<UserWrapper, RESTUserV1,
        RESTUserCollectionV1> implements CollectionWrapper<UserWrapper> {

    public RESTUserCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTUserCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
