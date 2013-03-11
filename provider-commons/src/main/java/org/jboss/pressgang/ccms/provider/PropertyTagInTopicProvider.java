package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface PropertyTagInTopicProvider extends PropertyTagProvider {
    CollectionWrapper<PropertyTagInTopicWrapper> getPropertyTagInTopicRevisions(int id, Integer revision);
}
