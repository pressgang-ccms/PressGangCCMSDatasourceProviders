package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;

public class RESTTagCollectionV1Wrapper extends RESTCollectionWrapper<TagWrapper, RESTTagV1, RESTTagCollectionV1> {

    public RESTTagCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTTagCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
