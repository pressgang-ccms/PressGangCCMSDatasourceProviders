package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedCSNodeWrapper extends EntityWrapper<TranslatedCSNodeWrapper> {
    Integer getNodeId();

    void setNodeId(Integer id);

    Integer getNodeRevision();

    void setNodeRevision(Integer revision);

    String getOriginalString();

    void setOriginalString(String originalString);

    String getZanataId();

    UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedStrings();

    void setTranslatedStrings(UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> translatedStrings);

    CSNodeWrapper getCSNode();

    void setCSNode(CSNodeWrapper node);

    TranslatedContentSpecWrapper getTranslatedContentSpec();

    TranslatedTopicWrapper getTranslatedTopic();

    void setTranslatedTopic(TranslatedTopicWrapper translatedTopic);
}
