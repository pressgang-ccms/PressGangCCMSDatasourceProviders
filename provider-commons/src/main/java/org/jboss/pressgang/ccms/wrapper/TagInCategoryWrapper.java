package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseTagWrapper;

public interface TagInCategoryWrapper extends BaseTagWrapper<TagInCategoryWrapper> {
    Integer getInCategorySort();

    Integer getRelationshipId();
}
