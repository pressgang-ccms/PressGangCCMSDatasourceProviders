package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BasePropertyTagWrapper;

public interface PropertyTagInPropertyCategoryWrapper extends BasePropertyTagWrapper<PropertyTagInPropertyCategoryWrapper> {
    Integer getSort();

    void setSort(Integer sort);

    Integer getRelationshipId();
}
