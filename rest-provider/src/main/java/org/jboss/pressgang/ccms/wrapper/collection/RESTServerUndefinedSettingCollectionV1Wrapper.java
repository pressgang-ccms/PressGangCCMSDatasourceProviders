package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTServerUndefinedSettingCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTServerUndefinedSettingV1;
import org.jboss.pressgang.ccms.wrapper.ServerUndefinedSettingWrapper;

public class RESTServerUndefinedSettingCollectionV1Wrapper extends
        RESTUpdateableCollectionWrapper<ServerUndefinedSettingWrapper, RESTServerUndefinedSettingV1, RESTServerUndefinedSettingCollectionV1> {

    public RESTServerUndefinedSettingCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTServerUndefinedSettingCollectionV1 collection) {
        super(providerFactory, collection, false);
    }
}
