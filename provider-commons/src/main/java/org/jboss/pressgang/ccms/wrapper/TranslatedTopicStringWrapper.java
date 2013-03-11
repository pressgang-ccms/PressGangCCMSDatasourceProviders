package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface TranslatedTopicStringWrapper extends EntityWrapper<TranslatedTopicStringWrapper> {
    String getOriginalString();

    String getTranslatedString();

    Boolean isFuzzy();
}
