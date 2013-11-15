package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBTranslatedTopicStringWrapper extends DBBaseEntityWrapper<TranslatedTopicStringWrapper,
        TranslatedTopicString> implements TranslatedTopicStringWrapper {

    private final TranslatedTopicString translatedTopicString;

    public DBTranslatedTopicStringWrapper(final DBProviderFactory providerFactory, final TranslatedTopicString translatedTopicString,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedTopicString.class);
        this.translatedTopicString = translatedTopicString;
    }

    @Override
    protected TranslatedTopicString getEntity() {
        return translatedTopicString;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTranslatedTopicStringID(id);
    }

    @Override
    public String getOriginalString() {
        return getEntity().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getEntity().setOriginalString(originalString);
    }

    @Override
    public String getTranslatedString() {
        return getEntity().getTranslatedString();
    }

    @Override
    public void setTranslatedString(String translatedString) {
        getEntity().setTranslatedString(translatedString);
    }

    @Override
    public Boolean isFuzzy() {
        return getEntity().getFuzzyTranslation();
    }

    @Override
    public void setFuzzy(Boolean fuzzy) {
        getEntity().setFuzzyTranslation(fuzzy);
    }
}
