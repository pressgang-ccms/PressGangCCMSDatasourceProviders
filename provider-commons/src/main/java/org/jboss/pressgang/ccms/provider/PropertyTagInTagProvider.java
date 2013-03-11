package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface PropertyTagInTagProvider extends PropertyTagProvider {
    CollectionWrapper<PropertyTagInTagWrapper> getPropertyTagInTagRevisions(int id, Integer revision);
}
