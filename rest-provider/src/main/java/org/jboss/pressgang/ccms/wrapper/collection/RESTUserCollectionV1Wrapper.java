package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTUserCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;

public class RESTUserCollectionV1Wrapper extends RESTCollectionWrapper<UserWrapper, RESTUserV1,
        RESTUserCollectionV1> implements CollectionWrapper<UserWrapper> {

    public RESTUserCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTUserCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTUserCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTUserCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
