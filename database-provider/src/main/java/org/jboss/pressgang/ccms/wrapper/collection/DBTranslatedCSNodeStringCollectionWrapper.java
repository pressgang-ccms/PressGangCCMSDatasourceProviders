package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBTranslatedCSNodeStringCollectionWrapper extends DBUpdateableCollectionWrapper<TranslatedCSNodeStringWrapper,
        TranslatedCSNodeString> {
    public DBTranslatedCSNodeStringCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TranslatedCSNodeString> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TranslatedCSNodeStringWrapper.class);
    }
}
