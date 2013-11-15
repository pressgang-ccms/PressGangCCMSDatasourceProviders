package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTServerUndefinedEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTServerUndefinedEntityV1;
import org.jboss.pressgang.ccms.wrapper.ServerUndefinedEntityWrapper;

public class RESTServerUndefinedEntityCollectionV1Wrapper extends
        RESTUpdateableCollectionWrapper<ServerUndefinedEntityWrapper, RESTServerUndefinedEntityV1, RESTServerUndefinedEntityCollectionV1> {

    public RESTServerUndefinedEntityCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTServerUndefinedEntityCollectionV1 collection, final boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
