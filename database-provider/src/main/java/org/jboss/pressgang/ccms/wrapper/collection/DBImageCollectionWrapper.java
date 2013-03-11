package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;

public class DBImageCollectionWrapper extends DBCollectionWrapper<ImageWrapper, ImageFile> {
    public DBImageCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<ImageFile> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, ImageWrapper.class);
    }
}
