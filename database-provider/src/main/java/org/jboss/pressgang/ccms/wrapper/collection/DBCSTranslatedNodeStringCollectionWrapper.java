package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNodeString;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCSTranslatedNodeStringCollectionWrapper extends DBCollectionWrapper<CSTranslatedNodeStringWrapper, CSTranslatedNodeString> {
    public DBCSTranslatedNodeStringCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSTranslatedNodeString> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSTranslatedNodeStringWrapper.class);
    }
}
