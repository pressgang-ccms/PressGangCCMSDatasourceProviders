package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNode;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBCSTranslatedNodeStringWrapper extends DBBaseWrapper<CSTranslatedNodeStringWrapper> implements CSTranslatedNodeStringWrapper {
    private final CSTranslatedNodeString csTranslatedNodeString;

    public DBCSTranslatedNodeStringWrapper(final DBProviderFactory providerFactory, final CSTranslatedNodeString csTranslatedNodeString,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.csTranslatedNodeString = csTranslatedNodeString;
    }

    protected CSTranslatedNodeString getCSTranslatedNodeString() {
        return csTranslatedNodeString;
    }

    @Override
    public Integer getId() {
        return getCSTranslatedNodeString().getId();
    }

    @Override
    public void setId(Integer id) {
        getCSTranslatedNodeString().setCSTranslatedNodeStringId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getCSTranslatedNodeString().getRevision();
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeStringWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getCSTranslatedNodeString()),
                CSTranslatedNode.class, true);
    }

    @Override
    public CSTranslatedNodeString unwrap() {
        return csTranslatedNodeString;
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
