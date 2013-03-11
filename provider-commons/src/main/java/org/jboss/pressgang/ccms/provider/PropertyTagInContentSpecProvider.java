package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface PropertyTagInContentSpecProvider extends PropertyTagProvider {
    CollectionWrapper<PropertyTagInContentSpecWrapper> getPropertyTagInContentSpecRevisions(int id, Integer revision);
}
