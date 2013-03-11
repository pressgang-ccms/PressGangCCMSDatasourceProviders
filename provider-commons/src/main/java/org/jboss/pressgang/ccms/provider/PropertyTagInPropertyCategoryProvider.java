package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface PropertyTagInPropertyCategoryProvider extends PropertyTagProvider {
    CollectionWrapper<PropertyTagInPropertyCategoryWrapper> getPropertyTagInPropertyCategoryRevisions(int id, Integer revision);
}
