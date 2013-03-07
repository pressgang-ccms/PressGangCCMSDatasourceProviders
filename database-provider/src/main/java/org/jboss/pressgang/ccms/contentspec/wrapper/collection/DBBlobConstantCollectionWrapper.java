package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.BlobConstants;

public class DBBlobConstantCollectionWrapper extends DBCollectionWrapper<BlobConstantWrapper, BlobConstants> {
    public DBBlobConstantCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<BlobConstants> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, BlobConstantWrapper.class);
    }
}
