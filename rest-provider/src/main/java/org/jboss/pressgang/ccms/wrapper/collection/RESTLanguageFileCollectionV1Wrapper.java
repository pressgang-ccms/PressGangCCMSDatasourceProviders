package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;

public class RESTLanguageFileCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<LanguageFileWrapper, RESTLanguageFileV1,
        RESTLanguageFileCollectionV1> implements UpdateableCollectionWrapper<LanguageFileWrapper> {

    public RESTLanguageFileCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileCollectionV1 collection,
            boolean isRevisionCollection, final RESTFileV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }

    public RESTLanguageFileCollectionV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileCollectionV1 collection,
            boolean isRevisionCollection, final RESTFileV1 parent, final Collection<String> expandedEntityMethods) {
        super(providerFactory, collection, isRevisionCollection, parent, expandedEntityMethods);
    }
}
