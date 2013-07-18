package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedTopicWrapper extends BaseTopicWrapper<TranslatedTopicWrapper> {
    void setTopicId(Integer id);

    void setTopicRevision(Integer revision);

    Integer getTranslatedTopicId();

    void setTranslatedTopicId(Integer translatedTopicId);

    String getZanataId();

    boolean getContainsFuzzyTranslations();

    Integer getTranslationPercentage();

    void setTranslationPercentage(Integer percentage);

    String getTranslatedXMLCondition();

    void setTranslatedXMLCondition(String translatedXMLCondition);

    UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings();

    void setTranslatedTopicStrings(UpdateableCollectionWrapper<TranslatedTopicStringWrapper> translatedStrings);

    TopicWrapper getTopic();

    void setTopic(TopicWrapper topic);

    TranslatedCSNodeWrapper getTranslatedCSNode();

    void setTranslatedCSNode(TranslatedCSNodeWrapper translatedCSNode);
}
