package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface CategoryInTagProvider extends CategoryProvider {
    CollectionWrapper<CategoryInTagWrapper> getCategoryInTagRevisions(int id, Integer revision);
}
