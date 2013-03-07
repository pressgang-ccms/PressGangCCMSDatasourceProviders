package org.jboss.pressgang.ccms.contentspec.wrapper;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSTranslatedNodeV1;

public class RESTCSTranslatedNodeStringV1Wrapper extends RESTBaseWrapper<CSTranslatedNodeStringWrapper, RESTCSTranslatedNodeStringV1> implements CSTranslatedNodeStringWrapper {
    private final RESTCSTranslatedNodeStringV1 translatedTopicString;
    private final RESTCSTranslatedNodeV1 parent;

    protected RESTCSTranslatedNodeStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSTranslatedNodeStringV1 topic,
            boolean isRevision, final RESTCSTranslatedNodeV1 parent) {
        super(providerFactory, isRevision);
        this.translatedTopicString = RESTEntityProxyFactory.createProxy(providerFactory, topic, isRevision);
        this.parent = parent;
    }

    @Override
    protected RESTCSTranslatedNodeStringV1 getProxyEntity() {
        return translatedTopicString;
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeStringWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTCSTranslatedNodeStringV1.class, true, parent);
    }

    @Override
    public CSTranslatedNodeStringWrapper clone(boolean deepCopy) {
        return new RESTCSTranslatedNodeStringV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent);
    }

    @Override
    public String getOriginalString() {
        return getProxyEntity().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getEntity().explicitSetOriginalString(originalString);

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
