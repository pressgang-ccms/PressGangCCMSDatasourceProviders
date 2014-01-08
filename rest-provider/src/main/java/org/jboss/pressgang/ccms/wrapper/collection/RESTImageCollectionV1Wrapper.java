package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;

public class RESTImageCollectionV1Wrapper extends RESTCollectionWrapper<ImageWrapper, RESTImageV1,
        RESTImageCollectionV1> implements CollectionWrapper<ImageWrapper> {

    public RESTImageCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTImageCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
