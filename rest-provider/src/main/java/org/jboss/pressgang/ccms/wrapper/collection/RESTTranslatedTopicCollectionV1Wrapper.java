package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;

public class RESTTranslatedTopicCollectionV1Wrapper extends RESTCollectionWrapper<TranslatedTopicWrapper, RESTTranslatedTopicV1,
        RESTTranslatedTopicCollectionV1> implements CollectionWrapper<TranslatedTopicWrapper> {

    public RESTTranslatedTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedTopicCollectionV1 collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }

    public RESTTranslatedTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedTopicCollectionV1 collection, boolean isRevisionCollection, final Collection<String> entityIgnoreMethods) {
        super(providerFactory, collection, isRevisionCollection, entityIgnoreMethods);
    }
}
