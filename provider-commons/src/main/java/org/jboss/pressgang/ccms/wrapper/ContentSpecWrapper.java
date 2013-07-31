package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ContentSpecWrapper extends EntityWrapper<ContentSpecWrapper> {
    CollectionWrapper<TagWrapper> getTags();

    void setTags(CollectionWrapper<TagWrapper> tags);

    CollectionWrapper<TagWrapper> getBookTags();

    void setBookTags(CollectionWrapper<TagWrapper> bookTags);

    UpdateableCollectionWrapper<CSNodeWrapper> getChildren();

    void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes);

    UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getProperties();

    void setProperties(UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> properties);

    CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecs();

    String getTitle();

    String getProduct();

    String getVersion();

    String getLocale();

    void setLocale(String locale);

    Integer getType();

    void setType(Integer typeId);

    String getCondition();

    void setCondition(String condition);

    Date getLastModified();

    PropertyTagInContentSpecWrapper getProperty(final int propertyId);

    CSNodeWrapper getMetaData(final String metaDataTitle);
}
