package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTTranslatedTopicStringV1Wrapper extends RESTBaseEntityWrapper<TranslatedTopicStringWrapper,
        RESTTranslatedTopicStringV1> implements TranslatedTopicStringWrapper {
    private final RESTTranslatedTopicV1 parent;

    protected RESTTranslatedTopicStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedTopicStringV1 topic,
            boolean isRevision, final RESTTranslatedTopicV1 parent, boolean isNewEntity) {
        super(providerFactory, topic, isRevision, parent, isNewEntity);
        this.parent = parent;
    }

    protected RESTTranslatedTopicStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedTopicStringV1 topic,
            boolean isRevision, final RESTTranslatedTopicV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, topic, isRevision, parent, isNewEntity, expandedMethods);
        this.parent = parent;
    }

    @Override
    public TranslatedTopicStringWrapper clone(boolean deepCopy) {
        return new RESTTranslatedTopicStringV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent,
                isNewEntity());
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
