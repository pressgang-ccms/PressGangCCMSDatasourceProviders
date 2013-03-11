package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageImageCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;

public class RESTLanguageImageCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<LanguageImageWrapper, RESTLanguageImageV1,
        RESTLanguageImageCollectionV1> implements UpdateableCollectionWrapper<LanguageImageWrapper> {

    public RESTLanguageImageCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageImageCollectionV1 collection,
            boolean isRevisionCollection, final RESTImageV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
