package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;

public class RESTPropertyTagInContentSpecCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<PropertyTagInContentSpecWrapper,
        RESTAssignedPropertyTagV1, RESTAssignedPropertyTagCollectionV1> implements
        UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> {

    public RESTPropertyTagInContentSpecCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTAssignedPropertyTagCollectionV1 collection, boolean isRevisionCollection, final RESTContentSpecV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent, PropertyTagInContentSpecWrapper.class);
    }

    public RESTPropertyTagInContentSpecCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTAssignedPropertyTagCollectionV1 collection, boolean isRevisionCollection, final RESTContentSpecV1 parent,
            final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, PropertyTagInContentSpecWrapper.class, expandedEntityMethods);
    }
}
