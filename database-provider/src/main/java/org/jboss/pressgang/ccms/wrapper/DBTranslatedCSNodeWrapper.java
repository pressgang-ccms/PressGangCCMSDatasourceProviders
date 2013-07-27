package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.Set;

import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedCSNodeStringCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBTranslatedCSNodeWrapper extends DBBaseWrapper<TranslatedCSNodeWrapper, TranslatedCSNode> implements TranslatedCSNodeWrapper {
    private final TranslatedCSNodeStringCollectionEventListener translatedCSNodeStringCollectionEventListener = new
            TranslatedCSNodeStringCollectionEventListener();

    private final TranslatedCSNode csNode;

    public DBTranslatedCSNodeWrapper(final DBProviderFactory providerFactory, final TranslatedCSNode csNode, boolean isRevision) {
        super(providerFactory, isRevision, TranslatedCSNode.class);
        this.csNode = csNode;
    }

    @Override
    protected TranslatedCSNode getEntity() {
        return csNode;
    }

    protected CSNode getEnversCSNode() {
        return getEntity().getEnversCSNode(getEntityManager());
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTranslatedCSNodeId(id);
    }

    @Override
    public TranslatedCSNode unwrap() {
        return csNode;
    }

    @Override
    public Integer getNodeId() {
        return getEntity().getCSNodeId();
    }

    @Override
    public void setNodeId(Integer id) {
        getEntity().setCSNodeId(id);
    }

    @Override
    public Integer getNodeRevision() {
        return getEntity().getCSNodeRevision();
    }

    @Override
    public void setNodeRevision(Integer revision) {
        getEntity().setCSNodeRevision(revision);
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
    public String getZanataId() {
        return "CS" + getEntity().getCSNodeId() + "-" + getEntity().getCSNodeRevision();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedStrings() {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getTranslatedCSNodeStrings(), TranslatedCSNodeString.class, isRevisionEntity(),
                TranslatedCSNodeStringWrapper.class);
        final DBTranslatedCSNodeStringCollectionWrapper dbCollection = (DBTranslatedCSNodeStringCollectionWrapper) collection;
        dbCollection.registerEventListener(translatedCSNodeStringCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setTranslatedStrings(UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> translatedStrings) {
        if (translatedStrings == null) return;
        final DBTranslatedCSNodeStringCollectionWrapper dbTranslatedStrings = (DBTranslatedCSNodeStringCollectionWrapper) translatedStrings;
        dbTranslatedStrings.registerEventListener(translatedCSNodeStringCollectionEventListener);

        // Remove the current translated strings
        final Set<TranslatedCSNodeString> currentTranslatedStrings = getEntity().getTranslatedCSNodeStrings();
        for (final TranslatedCSNodeString translatedString : currentTranslatedStrings) {
            getEntity().removeTranslatedString(translatedString);
        }

        // Set the new translated strings
        final Collection<TranslatedCSNodeString> newTranslatedStrings = dbTranslatedStrings.unwrap();
        for (final TranslatedCSNodeString translatedString : newTranslatedStrings) {
            getEntity().addTranslatedString(translatedString);
        }
    }

    @Override
    public CSNodeWrapper getCSNode() {
        return getWrapperFactory().create(getEnversCSNode(), true);
    }

    @Override
    public void setCSNode(CSNodeWrapper node) {
        getEntity().setEnversCSNode(node == null ? null : (CSNode) node.unwrap());
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec() {
        return getWrapperFactory().create(getEntity().getTranslatedContentSpec(), isRevisionEntity(), TranslatedContentSpecWrapper.class);
    }

    @Override
    public TranslatedTopicWrapper getTranslatedTopic() {
        return getWrapperFactory().create(getEntity().getTranslatedTopicData(), isRevisionEntity(), TranslatedTopicWrapper.class);
    }

    @Override
    public void setTranslatedTopic(TranslatedTopicWrapper translatedTopic) {
        getEntity().setTranslatedTopicData(translatedTopic == null ? null : (TranslatedTopicData) translatedTopic.unwrap());
    }

    /**
     *
     */
    private class TranslatedCSNodeStringCollectionEventListener implements UpdateableCollectionEventListener<TranslatedCSNodeString> {
        @Override
        public void onAddItem(final TranslatedCSNodeString entity) {
            getEntity().addTranslatedString(entity);
        }

        @Override
        public void onRemoveItem(final TranslatedCSNodeString entity) {
            getEntity().removeTranslatedString(entity);
        }

        @Override
        public void onUpdateItem(final TranslatedCSNodeString entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof TranslatedCSNodeStringCollectionEventListener;
        }
    }
}
