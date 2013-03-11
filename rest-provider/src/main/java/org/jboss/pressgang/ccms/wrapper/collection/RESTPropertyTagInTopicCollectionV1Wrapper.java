package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;

public class RESTPropertyTagInTopicCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<PropertyTagInTopicWrapper,
        RESTAssignedPropertyTagV1, RESTAssignedPropertyTagCollectionV1> implements UpdateableCollectionWrapper<PropertyTagInTopicWrapper> {

    public RESTPropertyTagInTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTAssignedPropertyTagCollectionV1 collection, boolean isRevisionCollection, RESTBaseTopicV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent, PropertyTagInTopicWrapper.class);
    }
}
