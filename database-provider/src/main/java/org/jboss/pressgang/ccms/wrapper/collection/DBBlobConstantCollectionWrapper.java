package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBBlobConstantCollectionWrapper extends DBCollectionWrapper<BlobConstantWrapper, BlobConstants> {
    public DBBlobConstantCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<BlobConstants> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, BlobConstantWrapper.class);
    }
}
