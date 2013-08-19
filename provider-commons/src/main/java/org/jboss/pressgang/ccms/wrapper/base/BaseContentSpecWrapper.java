package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Date;

import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface BaseContentSpecWrapper<T extends BaseContentSpecWrapper<T>> extends EntityWrapper<T> {
    CollectionWrapper<TagWrapper> getTags();

    void setTags(CollectionWrapper<TagWrapper> tags);

    UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties();

    void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties);

    String getTitle();

    String getProduct();

    String getVersion();

    String getLocale();

    void setLocale(String locale);

    Integer getType();

    Date getLastModified();

    PropertyTagInContentSpecWrapper getProperty(final int propertyId);
}
