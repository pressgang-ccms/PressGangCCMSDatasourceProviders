package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TranslatedTopicStringWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface TranslatedTopicStringProvider {
    CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStringRevisions(int id, Integer revision);

    TranslatedTopicStringWrapper newTranslatedTopicString();

    CollectionWrapper<TranslatedTopicStringWrapper> newTranslatedTopicStringCollection();
}
