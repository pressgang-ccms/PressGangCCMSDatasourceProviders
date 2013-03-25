package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface TranslatedCSNodeStringWrapper extends EntityWrapper<TranslatedCSNodeStringWrapper> {
    String getTranslatedString();

    void setTranslatedString(String translatedString);

    Boolean isFuzzy();

    void setFuzzy(Boolean fuzzy);

    String getLocale();

    void setLocale(String locale);
}
