package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedCSNodeWrapper extends DBBaseWrapper<TranslatedCSNodeWrapper> implements TranslatedCSNodeWrapper {
    private final TranslatedCSNode csNode;

    public DBTranslatedCSNodeWrapper(final DBProviderFactory providerFactory, final TranslatedCSNode csNode, boolean isRevision) {
        super(providerFactory, isRevision);
        this.csNode = csNode;
    }

    protected TranslatedCSNode getCSTranslatedNode() {
        return csNode;
    }

    protected CSNode getEnversCSNode() {
        return getCSTranslatedNode().getEnversCSNode(getEntityManager());
    }

    @Override
    public Integer getId() {
        return getCSTranslatedNode().getId();
    }

    @Override
    public void setId(Integer id) {
        getCSTranslatedNode().setTranslatedCSNodeId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getCSTranslatedNode().getRevision();
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getCSTranslatedNode()),
                TranslatedCSNode.class, true);
    }

    @Override
    public TranslatedCSNode unwrap() {
        return csNode;
    }

    @Override
    public Integer getNodeId() {
        return getCSTranslatedNode().getCSNodeId();
    }

    @Override
    public void setNodeId(Integer id) {
        getCSTranslatedNode().setCSNodeId(id);
    }

    @Override
    public Integer getNodeRevision() {
        return getCSTranslatedNode().getCSNodeRevision();
    }

    @Override
    public void setNodeRevision(Integer revision) {
        getCSTranslatedNode().setCSNodeRevision(revision);
    }

    @Override
    public String getOriginalString() {
        return getCSTranslatedNode().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getCSTranslatedNode().setOriginalString(originalString);
    }

    @Override
    public String getZanataId() {
        return "CS" + getCSTranslatedNode().getCSNodeId() + "-" + getCSTranslatedNode().getCSNodeRevision();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedStrings() {
        final CollectionWrapper<TranslatedCSNodeStringWrapper> collection = getWrapperFactory().createCollection(
                getCSTranslatedNode().getTranslatedCSNodeStrings(), TranslatedCSNodeString.class, isRevisionEntity(),
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
            getCSTranslatedNode().addTranslatedString((TranslatedCSNodeString) addTranslatedString.unwrap());
        }

        // Remove Translated Strings
        for (final TranslatedCSNodeStringWrapper removeTranslatedString : removeTranslatedStrings) {
            getCSTranslatedNode().removeTranslatedString((TranslatedCSNodeString) removeTranslatedString.unwrap());
        }
    }

    @Override
    public CSNodeWrapper getCSNode() {
        return getWrapperFactory().create(getEnversCSNode(), true);
    }

    @Override
    public void setCSNode(CSNodeWrapper node) {
        getCSTranslatedNode().setEnversCSNode(node == null ? null : (CSNode) node.unwrap());
    }
}
