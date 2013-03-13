package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;

public class RESTTranslatedContentSpecCollectionV1Wrapper extends RESTCollectionWrapper<TranslatedContentSpecWrapper,
        RESTTranslatedContentSpecV1, RESTTranslatedContentSpecCollectionV1> implements CollectionWrapper<TranslatedContentSpecWrapper> {

    public RESTTranslatedContentSpecCollectionV1Wrapper(RESTProviderFactory providerFactory,
            final RESTTranslatedContentSpecCollectionV1 collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
