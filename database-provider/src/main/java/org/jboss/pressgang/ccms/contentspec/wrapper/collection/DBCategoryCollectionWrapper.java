package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.Category;

public class DBCategoryCollectionWrapper extends DBCollectionWrapper<CategoryWrapper, Category> {
    public DBCategoryCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<Category> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CategoryWrapper.class);
    }
}
