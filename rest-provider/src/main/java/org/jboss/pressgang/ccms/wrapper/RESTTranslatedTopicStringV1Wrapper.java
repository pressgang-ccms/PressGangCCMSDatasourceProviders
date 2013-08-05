package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTTranslatedTopicStringV1Wrapper extends RESTBaseWrapper<TranslatedTopicStringWrapper,
        RESTTranslatedTopicStringV1> implements TranslatedTopicStringWrapper {
    private final RESTTranslatedTopicStringV1 translatedTopicString;
    private final RESTTranslatedTopicV1 parent;

    protected RESTTranslatedTopicStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedTopicStringV1 topic,
            boolean isRevision, final RESTTranslatedTopicV1 parent) {
        super(providerFactory, isRevision);
        this.translatedTopicString = RESTEntityProxyFactory.createProxy(providerFactory, topic, isRevision);
        this.parent = parent;
    }

    @Override
    protected RESTTranslatedTopicStringV1 getProxyEntity() {
        return translatedTopicString;
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTranslatedTopicStringV1.class, true, parent);
    }

    @Override
    public TranslatedTopicStringWrapper clone(boolean deepCopy) {
        return new RESTTranslatedTopicStringV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent);
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
}
