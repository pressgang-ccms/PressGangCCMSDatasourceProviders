package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSTranslatedNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;

public class RESTCSTranslatedNodeStringCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CSTranslatedNodeStringWrapper,
        RESTCSTranslatedNodeStringV1, RESTCSTranslatedNodeStringCollectionV1> implements
        UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> {

    public RESTCSTranslatedNodeStringCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTCSTranslatedNodeStringCollectionV1 collection, boolean isRevisionCollection, final RESTCSTranslatedNodeV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
