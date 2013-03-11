package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseCategoryWrapper;

public interface CategoryInTagWrapper extends BaseCategoryWrapper<CategoryInTagWrapper> {
    Integer getRelationshipId();

    Integer getRelationshipSort();
}
