package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTTranslatedCSNodeStringV1Wrapper extends RESTBaseWrapper<TranslatedCSNodeStringWrapper, RESTTranslatedCSNodeStringV1> implements TranslatedCSNodeStringWrapper {
    private final RESTTranslatedCSNodeV1 parent;

    protected RESTTranslatedCSNodeStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeStringV1 translatedString,
            boolean isRevision, final RESTTranslatedCSNodeV1 parent, boolean isNewEntity) {
        super(providerFactory, translatedString, isRevision, parent, isNewEntity);
        this.parent = parent;
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeStringWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTranslatedCSNodeStringV1.class, true, parent);
    }

    @Override
    public TranslatedCSNodeStringWrapper clone(boolean deepCopy) {
        return new RESTTranslatedCSNodeStringV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent,
                isNewEntity());
    }

    @Override
    public String getTranslatedString() {
        return getProxyEntity().getTranslatedString();
    }

    @Override
    public void setTranslatedString(String translatedString) {
        getEntity().explicitSetTranslatedString(translatedString);
    }

    @Override
    public Boolean isFuzzy() {
        return getProxyEntity().getFuzzyTranslation();
    }

    @Override
    public void setFuzzy(Boolean fuzzy) {
        getEntity().explicitSetFuzzyTranslation(fuzzy);
    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().explicitSetLocale(locale);
    }
}
