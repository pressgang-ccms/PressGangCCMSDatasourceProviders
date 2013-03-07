package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;

public class RESTImageCollectionV1Wrapper extends RESTCollectionWrapper<ImageWrapper, RESTImageV1,
        RESTImageCollectionV1> implements CollectionWrapper<ImageWrapper> {

    public RESTImageCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTImageCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
