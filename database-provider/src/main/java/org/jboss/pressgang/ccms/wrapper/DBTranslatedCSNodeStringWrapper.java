package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTranslatedCSNodeStringWrapper extends DBBaseWrapper<TranslatedCSNodeStringWrapper> implements TranslatedCSNodeStringWrapper {
    private final TranslatedCSNodeString translatedCSNodeString;

    public DBTranslatedCSNodeStringWrapper(final DBProviderFactory providerFactory, final TranslatedCSNodeString translatedCSNodeString,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.translatedCSNodeString = translatedCSNodeString;
    }

    protected TranslatedCSNodeString getCSTranslatedNodeString() {
        return translatedCSNodeString;
    }

    @Override
    public Integer getId() {
        return getCSTranslatedNodeString().getId();
    }

    @Override
    public void setId(Integer id) {
        getCSTranslatedNodeString().setTranslatedCSNodeStringId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getCSTranslatedNodeString().getRevision();
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeStringWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getCSTranslatedNodeString()),
                TranslatedCSNode.class, true);
    }

    @Override
    public TranslatedCSNodeString unwrap() {
        return translatedCSNodeString;
    }

    @Override
    public String getOriginalString() {
        return getCSTranslatedNodeString().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getCSTranslatedNodeString().setOriginalString(originalString);
    }

    @Override
    public String getTranslatedString() {
        return getCSTranslatedNodeString().getTranslatedString();
    }

    @Override
    public void setTranslatedString(String translatedString) {
        getCSTranslatedNodeString().setTranslatedString(translatedString);
    }

    @Override
    public Boolean isFuzzy() {
        return getCSTranslatedNodeString().getFuzzyTranslation();
    }

    @Override
    public void setFuzzy(Boolean fuzzy) {
        getCSTranslatedNodeString().setFuzzyTranslation(fuzzy);
    }

    @Override
    public String getLocale() {
        return getCSTranslatedNodeString().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getCSTranslatedNodeString().setLocale(locale);
    }
}
