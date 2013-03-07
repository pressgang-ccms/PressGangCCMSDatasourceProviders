package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNodeString;

public class DBCSTranslatedNodeStringCollectionWrapper extends DBCollectionWrapper<CSTranslatedNodeStringWrapper, CSTranslatedNodeString> {
    public DBCSTranslatedNodeStringCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSTranslatedNodeString> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSTranslatedNodeStringWrapper.class);
    }
}
