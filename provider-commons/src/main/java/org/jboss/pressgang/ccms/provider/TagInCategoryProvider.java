package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface TagInCategoryProvider extends TagProvider {
    CollectionWrapper<TagInCategoryWrapper> getTagInCategoryRevisions(int id, Integer revision);
}