package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;

public class RESTTranslatedTopicStringCollectionV1Wrapper extends RESTUpdateableCollectionWrapper<TranslatedTopicStringWrapper,
        RESTTranslatedTopicStringV1, RESTTranslatedTopicStringCollectionV1> implements
        UpdateableCollectionWrapper<TranslatedTopicStringWrapper> {

    public RESTTranslatedTopicStringCollectionV1Wrapper(final RESTProviderFactory providerFactory,
            final RESTTranslatedTopicStringCollectionV1 collection, boolean isRevisionCollection, final RESTTranslatedTopicV1 parent) {
        super(providerFactory, collection, isRevisionCollection, parent);
    }
}
