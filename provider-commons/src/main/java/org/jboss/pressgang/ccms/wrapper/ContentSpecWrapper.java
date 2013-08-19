package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ContentSpecWrapper extends BaseContentSpecWrapper<ContentSpecWrapper> {
    CollectionWrapper<TagWrapper> getBookTags();

    void setBookTags(CollectionWrapper<TagWrapper> bookTags);

    UpdateableCollectionWrapper<CSNodeWrapper> getChildren();

    void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes);

    CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecs();

    void setType(Integer typeId);

    String getCondition();

    void setCondition(String condition);

    CSNodeWrapper getMetaData(final String metaDataTitle);
}
