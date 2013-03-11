package org.jboss.pressgang.ccms.wrapper.collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;

public class RESTPropertyTagInTagCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<PropertyTagInTagWrapper,
        RESTAssignedPropertyTagV1, RESTAssignedPropertyTagCollectionV1> implements UpdateableCollectionWrapper<PropertyTagInTagWrapper> {

    public RESTPropertyTagInTagCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTAssignedPropertyTagCollectionV1 collection, boolean isRevisionCollection, final RESTBaseTagV1<?, ?, ?> parent) {
        super(providerFactory, collection, isRevisionCollection, parent, PropertyTagInTagWrapper.class);
    }
}
