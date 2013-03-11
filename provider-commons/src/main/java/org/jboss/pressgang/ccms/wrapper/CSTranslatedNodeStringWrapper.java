package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface CSTranslatedNodeStringWrapper extends EntityWrapper<CSTranslatedNodeStringWrapper> {
    String getOriginalString();

    void setOriginalString(String originalString);

    String getTranslatedString();

    void setTranslatedString(String translatedString);

    Boolean isFuzzy();

    void setFuzzy(Boolean fuzzy);

    String getLocale();

    void setLocale(String locale);
}
