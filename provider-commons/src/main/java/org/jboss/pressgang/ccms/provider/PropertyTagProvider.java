package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface PropertyTagProvider {
    PropertyTagWrapper getPropertyTag(int id);

    PropertyTagWrapper getPropertyTag(int id, Integer revision);

    CollectionWrapper<PropertyTagWrapper> getPropertyTagRevisions(int id, Integer revision);

    PropertyTagWrapper newPropertyTag();

    PropertyTagInTopicWrapper newPropertyTagInTopic(BaseTopicWrapper<?> topic);

    PropertyTagInTopicWrapper newPropertyTagInTopic(PropertyTagWrapper propertyTag, BaseTopicWrapper<?> topic);

    PropertyTagInTagWrapper newPropertyTagInTag(TagWrapper tag);

    PropertyTagInTagWrapper newPropertyTagInTag(PropertyTagWrapper propertyTag, TagWrapper tag);

    PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(ContentSpecWrapper contentSpec);

    PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(PropertyTagWrapper propertyTag, ContentSpecWrapper contentSpec);

    PropertyTagInPropertyCategoryWrapper newPropertyTagInPropertyCategory(PropertyCategoryWrapper propertyCategory);

    CollectionWrapper<PropertyTagWrapper> newPropertyTagCollection();

    UpdateableCollectionWrapper<PropertyTagInTopicWrapper> newPropertyTagInTopicCollection(BaseTopicWrapper<?> topic);

    UpdateableCollectionWrapper<PropertyTagInTagWrapper> newPropertyTagInTagCollection(TagWrapper tag);

    UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> newPropertyTagInContentSpecCollection(ContentSpecWrapper contentSpec);

    UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> newPropertyTagInPropertyCategoryCollection(
            PropertyCategoryWrapper propertyCategory);
}
