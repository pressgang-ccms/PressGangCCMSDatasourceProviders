package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.model.TranslatedTopicString;

public class DBTranslatedTopicStringCollectionWrapper extends DBCollectionWrapper<TranslatedTopicStringWrapper, TranslatedTopicString> {
    public DBTranslatedTopicStringCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TranslatedTopicString> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TranslatedTopicStringWrapper.class);
    }
}
