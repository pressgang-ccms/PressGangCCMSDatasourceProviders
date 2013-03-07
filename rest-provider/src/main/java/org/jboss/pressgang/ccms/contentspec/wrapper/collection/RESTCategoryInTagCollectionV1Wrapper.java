package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;

public class RESTCategoryInTagCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CategoryInTagWrapper, RESTCategoryInTagV1,
        RESTCategoryInTagCollectionV1> implements UpdateableCollectionWrapper<CategoryInTagWrapper> {

    public RESTCategoryInTagCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCategoryInTagCollectionV1 collection,
            boolean isRevisionCollection, final RESTTagV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
