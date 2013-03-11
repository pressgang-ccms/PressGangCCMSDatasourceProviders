package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CategoryProvider {
    CategoryWrapper getCategory(int id);

    CategoryWrapper getCategory(int id, Integer revision);

    UpdateableCollectionWrapper<TagInCategoryWrapper> getCategoryTags(int id, Integer revision);

    CollectionWrapper<CategoryWrapper> getCategoryRevisions(int id, Integer revision);
}
