package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;

public class RESTCategoryCollectionV1Wrapper extends RESTCollectionWrapper<CategoryWrapper, RESTCategoryV1,
        RESTCategoryCollectionV1> implements CollectionWrapper<CategoryWrapper> {

    public RESTCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
