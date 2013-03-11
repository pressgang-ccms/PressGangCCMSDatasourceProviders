package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCategoryInTagCollectionWrapper extends DBUpdateableCollectionWrapper<CategoryInTagWrapper, TagToCategory> {
    public DBCategoryInTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TagToCategory> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CategoryInTagWrapper.class);
    }
}
