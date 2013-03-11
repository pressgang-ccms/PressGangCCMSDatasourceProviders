package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface BasePropertyCategoryWrapper<T extends BasePropertyCategoryWrapper<T>> extends EntityWrapper<T> {
    String getName();

    void setName(String name);

    UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> getPropertyTags();

    void setPropertyTags(UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> propertyTags);
}
