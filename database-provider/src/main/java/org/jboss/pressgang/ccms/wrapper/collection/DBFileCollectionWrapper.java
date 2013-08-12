package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.File;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.FileWrapper;

public class DBFileCollectionWrapper extends DBCollectionWrapper<FileWrapper, File> {
    public DBFileCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<File> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, FileWrapper.class);
    }
}
