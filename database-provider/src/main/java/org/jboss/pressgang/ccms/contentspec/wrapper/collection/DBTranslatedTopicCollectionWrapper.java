package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TranslatedTopicWrapper;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;

public class DBTranslatedTopicCollectionWrapper extends DBCollectionWrapper<TranslatedTopicWrapper, TranslatedTopicData> {
    public DBTranslatedTopicCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TranslatedTopicData> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TranslatedTopicWrapper.class);
    }
}
