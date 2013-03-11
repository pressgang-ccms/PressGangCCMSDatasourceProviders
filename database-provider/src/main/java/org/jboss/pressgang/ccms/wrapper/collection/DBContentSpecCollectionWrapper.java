package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBContentSpecCollectionWrapper extends DBCollectionWrapper<ContentSpecWrapper, ContentSpec> {
    public DBContentSpecCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<ContentSpec> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, ContentSpecWrapper.class);
    }
}
