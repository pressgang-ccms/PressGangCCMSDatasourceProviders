package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;

public class RESTPropertyTagInPropertyCategoryCollectionV1Wrapper extends
        RESTUpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper, RESTPropertyTagInPropertyCategoryV1,
                RESTPropertyTagInPropertyCategoryCollectionV1> implements
        UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> {

    public RESTPropertyTagInPropertyCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTPropertyTagInPropertyCategoryCollectionV1 collection, boolean isRevisionCollection,
            final RESTPropertyCategoryV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
