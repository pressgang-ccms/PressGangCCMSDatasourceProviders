package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;

public class RESTCategoryCollectionV1Wrapper extends RESTCollectionWrapper<CategoryWrapper, RESTCategoryV1,
        RESTCategoryCollectionV1> implements CollectionWrapper<CategoryWrapper> {

    public RESTCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
