package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBTranslatedTopicStringWrapper extends DBBaseWrapper<TranslatedTopicStringWrapper> implements TranslatedTopicStringWrapper {

    private final TranslatedTopicString translatedTopicString;

    public DBTranslatedTopicStringWrapper(final DBProviderFactory providerFactory, final TranslatedTopicString translatedTopicString,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.translatedTopicString = translatedTopicString;
    }

    protected TranslatedTopicString getTranslatedTopicString() {
        return translatedTopicString;
    }

    @Override
    public Integer getId() {
        return getTranslatedTopicString().getId();
    }

    @Override
    public void setId(Integer id) {
        getTranslatedTopicString().setTranslatedTopicStringID(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getTranslatedTopicString().getRevision();
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getTranslatedTopicString()),
                TranslatedTopicString.class, true);
    }

    @Override
    public TranslatedTopicString unwrap() {
        return getTranslatedTopicString();
    }

    @Override
    public String getOriginalString() {
        return getTranslatedTopicString().getOriginalString();
    }

    @Override
    public String getTranslatedString() {
        return getTranslatedTopicString().getTranslatedString();
    }

    @Override
    public Boolean isFuzzy() {
        return getTranslatedTopicString().getFuzzyTranslation();
    }

}
