package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;

public class DBTranslatedTopicStringCollectionWrapper extends DBCollectionWrapper<TranslatedTopicStringWrapper, TranslatedTopicString> {
    public DBTranslatedTopicStringCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TranslatedTopicString> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TranslatedTopicStringWrapper.class);
    }
}
