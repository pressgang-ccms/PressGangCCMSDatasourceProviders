package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedCSNodeWrapper extends DBBaseWrapper<TranslatedCSNodeWrapper, TranslatedCSNode> implements TranslatedCSNodeWrapper {
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
        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }

    @Override
    public void setTranslatedStrings(UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> translatedStrings) {
        if (translatedStrings == null) return;

        final List<TranslatedCSNodeStringWrapper> addTranslatedStrings = translatedStrings.getAddItems();
        final List<TranslatedCSNodeStringWrapper> removeTranslatedStrings = translatedStrings.getRemoveItems();
        /*
         * There is no need to do update outgoing topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<TranslatedCSNodeStringWrapper> updateTranslatedStrings = translatedStrings.getUpdateItems();

        // Add Translated Strings
        for (final TranslatedCSNodeStringWrapper addTranslatedString : addTranslatedStrings) {
            getEntity().addTranslatedString((TranslatedCSNodeString) addTranslatedString.unwrap());
        }

        // Remove Translated Strings
        for (final TranslatedCSNodeStringWrapper removeTranslatedString : removeTranslatedStrings) {
            getEntity().removeTranslatedString((TranslatedCSNodeString) removeTranslatedString.unwrap());
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
}
