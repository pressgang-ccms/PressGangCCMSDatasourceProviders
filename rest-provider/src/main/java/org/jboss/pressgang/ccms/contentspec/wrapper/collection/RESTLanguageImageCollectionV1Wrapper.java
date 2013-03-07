package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;

public class RESTLanguageImageCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<LanguageImageWrapper, RESTLanguageImageV1,
        RESTLanguageImageCollectionV1> implements UpdateableCollectionWrapper<LanguageImageWrapper> {

    public RESTLanguageImageCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageImageCollectionV1 collection,
            boolean isRevisionCollection, final RESTImageV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
