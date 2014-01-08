package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TagProvider {
    TagWrapper getTag(final int id);

    TagWrapper getTag(final int id, Integer revision);

    TagWrapper getTagByName(final String name);

    CollectionWrapper<TagWrapper> getTagChildTags(int id, Integer revision);

    CollectionWrapper<TagWrapper> getTagParentTags(int id, Integer revision);

    UpdateableCollectionWrapper<PropertyTagInTagWrapper> getTagProperties(int id, Integer revision);

    CollectionWrapper<TagWrapper> getTagRevisions(int id, Integer revision);

    TagWrapper newTag();

    TagInCategoryWrapper newTagInCategory(CategoryWrapper parent);

    CollectionWrapper<TagWrapper> newTagCollection();

    CollectionWrapper<TagInCategoryWrapper> newTagInCategoryCollection(CategoryWrapper parent);
}
