package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;

public class DBContentSpecCollectionWrapper extends DBCollectionWrapper<ContentSpecWrapper, ContentSpec> {
    public DBContentSpecCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<ContentSpec> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, ContentSpecWrapper.class);
    }
}
