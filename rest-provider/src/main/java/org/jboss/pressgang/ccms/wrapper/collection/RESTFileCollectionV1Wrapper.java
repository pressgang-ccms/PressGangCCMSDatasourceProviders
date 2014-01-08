package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.wrapper.FileWrapper;

public class RESTFileCollectionV1Wrapper extends RESTCollectionWrapper<FileWrapper, RESTFileV1,
        RESTFileCollectionV1> implements CollectionWrapper<FileWrapper> {

    public RESTFileCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTFileCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTFileCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTFileCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
