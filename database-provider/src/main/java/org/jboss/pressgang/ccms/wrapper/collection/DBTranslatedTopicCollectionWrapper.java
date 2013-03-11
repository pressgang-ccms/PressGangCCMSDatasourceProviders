package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TranslatedTopicWrapper;

public class DBTranslatedTopicCollectionWrapper extends DBCollectionWrapper<TranslatedTopicWrapper, TranslatedTopicData> {
    public DBTranslatedTopicCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TranslatedTopicData> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TranslatedTopicWrapper.class);
    }
}
