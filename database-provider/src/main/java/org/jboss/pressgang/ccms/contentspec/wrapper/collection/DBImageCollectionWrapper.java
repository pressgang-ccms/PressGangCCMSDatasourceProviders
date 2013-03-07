package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.model.ImageFile;

public class DBImageCollectionWrapper extends DBCollectionWrapper<ImageWrapper, ImageFile> {
    public DBImageCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<ImageFile> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, ImageWrapper.class);
    }
}
