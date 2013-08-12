package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;

public class DBLanguageFileCollectionWrapper extends DBUpdateableCollectionWrapper<LanguageFileWrapper, LanguageFile> {
    public DBLanguageFileCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<LanguageFile> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, LanguageFileWrapper.class);
    }
}
