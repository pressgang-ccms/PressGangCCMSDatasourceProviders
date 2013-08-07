package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;

public class DBTranslatedCSNodeStringWrapper extends DBBaseWrapper<TranslatedCSNodeStringWrapper,
        TranslatedCSNodeString> implements TranslatedCSNodeStringWrapper {
    private final TranslatedCSNodeString translatedCSNodeString;

    public DBTranslatedCSNodeStringWrapper(final DBProviderFactory providerFactory, final TranslatedCSNodeString translatedCSNodeString,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedCSNodeString.class);
        this.translatedCSNodeString = translatedCSNodeString;
    }

    @Override
    protected TranslatedCSNodeString getEntity() {
        return translatedCSNodeString;
    }

    @Override
    public Integer getId() {
        return getEntity().getId();
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTranslatedCSNodeStringId(id);
    }

    @Override
    public TranslatedCSNodeString unwrap() {
        return translatedCSNodeString;
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

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().setLocale(locale);
    }
}
