package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;

public class RESTTagInCategoryCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<TagInCategoryWrapper, RESTTagInCategoryV1,
        RESTTagInCategoryCollectionV1> {

    public RESTTagInCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryCollectionV1 collection,
            boolean isRevisionCollection, final RESTCategoryV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTTagInCategoryCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagInCategoryCollectionV1 collection,
            boolean isRevisionCollection, final RESTCategoryV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
