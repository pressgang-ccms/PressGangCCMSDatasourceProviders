package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;

public class RESTCSNodeCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CSNodeWrapper, RESTCSNodeV1,
        RESTCSNodeCollectionV1> implements UpdateableCollectionWrapper<CSNodeWrapper> {

    public RESTCSNodeCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTCSNodeCollectionV1 collection,
            boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTCSNodeCollectionV1Wrapper(RESTProviderFactory providerFactory, final RESTCSNodeCollectionV1 collection,
            boolean isRevisionCollection, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, expandedEntityMethods);
    }
}
