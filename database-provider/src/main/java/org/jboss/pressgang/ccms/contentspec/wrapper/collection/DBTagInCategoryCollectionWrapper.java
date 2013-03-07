package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.model.TagToCategory;

public class DBTagInCategoryCollectionWrapper extends DBUpdateableCollectionWrapper<TagInCategoryWrapper, TagToCategory> {
    public DBTagInCategoryCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TagToCategory> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TagInCategoryWrapper.class);
    }
}
