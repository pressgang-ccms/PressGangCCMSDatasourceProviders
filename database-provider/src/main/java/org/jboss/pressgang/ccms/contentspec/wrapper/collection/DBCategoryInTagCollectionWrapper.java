package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.TagToCategory;

public class DBCategoryInTagCollectionWrapper extends DBUpdateableCollectionWrapper<CategoryInTagWrapper, TagToCategory> {
    public DBCategoryInTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TagToCategory> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CategoryInTagWrapper.class);
    }
}
