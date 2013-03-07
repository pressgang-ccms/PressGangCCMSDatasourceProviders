package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.model.LanguageImage;

public class DBLanguageImageCollectionWrapper extends DBCollectionWrapper<LanguageImageWrapper, LanguageImage> {
    public DBLanguageImageCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<LanguageImage> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, LanguageImageWrapper.class);
    }
}
