package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;

public class RESTPropertyTagCollectionV1Wrapper extends RESTCollectionWrapper<PropertyTagWrapper, RESTPropertyTagV1,
        RESTPropertyTagCollectionV1> implements CollectionWrapper<PropertyTagWrapper> {

    public RESTPropertyTagCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTPropertyTagCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
