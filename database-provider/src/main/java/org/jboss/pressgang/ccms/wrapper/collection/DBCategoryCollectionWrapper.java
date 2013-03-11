package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCategoryCollectionWrapper extends DBCollectionWrapper<CategoryWrapper, Category> {
    public DBCategoryCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<Category> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CategoryWrapper.class);
    }
}
