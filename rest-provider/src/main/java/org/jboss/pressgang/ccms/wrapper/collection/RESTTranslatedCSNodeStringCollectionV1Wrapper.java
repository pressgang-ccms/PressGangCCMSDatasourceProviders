package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;

public class RESTTranslatedCSNodeStringCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<TranslatedCSNodeStringWrapper, RESTTranslatedCSNodeStringV1, RESTTranslatedCSNodeStringCollectionV1> implements
        UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> {

    public RESTTranslatedCSNodeStringCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedCSNodeStringCollectionV1 collection, boolean isRevisionCollection, final RESTTranslatedCSNodeV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
