package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSInfoNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.CSInfoNodeWrapper;

public class RESTCSInfoNodeCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<CSInfoNodeWrapper, RESTCSInfoNodeV1, RESTCSInfoNodeCollectionV1> implements CollectionWrapper<CSInfoNodeWrapper> {

    public RESTCSInfoNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSInfoNodeCollectionV1 collection,
            boolean isRevisionCollection, final RESTCSNodeV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTCSInfoNodeCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSInfoNodeCollectionV1 collection,
            boolean isRevisionCollection, final RESTCSNodeV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
