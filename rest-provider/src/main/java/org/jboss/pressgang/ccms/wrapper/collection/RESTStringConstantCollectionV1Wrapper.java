package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTStringConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;

public class RESTStringConstantCollectionV1Wrapper extends RESTCollectionWrapper<StringConstantWrapper, RESTStringConstantV1,
        RESTStringConstantCollectionV1> implements CollectionWrapper<StringConstantWrapper> {

    public RESTStringConstantCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTStringConstantCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
