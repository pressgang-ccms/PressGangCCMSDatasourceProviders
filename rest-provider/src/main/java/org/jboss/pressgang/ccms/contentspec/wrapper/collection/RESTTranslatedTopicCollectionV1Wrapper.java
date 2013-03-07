package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;

public class RESTTranslatedTopicCollectionV1Wrapper extends RESTCollectionWrapper<TranslatedTopicWrapper, RESTTranslatedTopicV1,
        RESTTranslatedTopicCollectionV1> implements CollectionWrapper<TranslatedTopicWrapper> {

    public RESTTranslatedTopicCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedTopicCollectionV1 collection, boolean isRevisionCollection) {
        super(providerFactory, collection, isRevisionCollection);
    }
}
