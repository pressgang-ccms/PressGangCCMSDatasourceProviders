package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSTranslatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;

public class RESTCSTranslatedNodeCollectionV1Wrapper extends RESTCollectionWrapper<CSTranslatedNodeWrapper, RESTCSTranslatedNodeV1,
        RESTCSTranslatedNodeCollectionV1> implements CollectionWrapper<CSTranslatedNodeWrapper> {

    public RESTCSTranslatedNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTCSTranslatedNodeCollectionV1 collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
