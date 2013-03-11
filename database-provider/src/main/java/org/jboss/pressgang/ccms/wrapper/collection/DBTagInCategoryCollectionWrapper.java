package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;

public class DBTagInCategoryCollectionWrapper extends DBUpdateableCollectionWrapper<TagInCategoryWrapper, TagToCategory> {
    public DBTagInCategoryCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TagToCategory> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TagInCategoryWrapper.class);
    }
}
