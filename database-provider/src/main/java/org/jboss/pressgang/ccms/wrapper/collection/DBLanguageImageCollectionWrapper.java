package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;

public class DBLanguageImageCollectionWrapper extends DBCollectionWrapper<LanguageImageWrapper, LanguageImage> {
    public DBLanguageImageCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<LanguageImage> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, LanguageImageWrapper.class);
    }
}
