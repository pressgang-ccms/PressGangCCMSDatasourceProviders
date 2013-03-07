package org.jboss.pressgang.ccms.contentspec.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNode;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNodeString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;

public class DBCSTranslatedNodeWrapper extends DBBaseWrapper<CSTranslatedNodeWrapper> implements CSTranslatedNodeWrapper {
    private final CSTranslatedNode csNode;

    public DBCSTranslatedNodeWrapper(final DBProviderFactory providerFactory, final CSTranslatedNode csNode, boolean isRevision) {
        super(providerFactory, isRevision);
        this.csNode = csNode;
    }

    protected CSTranslatedNode getCSTranslatedNode() {
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
        getCSTranslatedNode().setCSTranslatedNodeId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getCSTranslatedNode().getRevision();
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getCSTranslatedNode()),
                CSTranslatedNode.class, true);
    }

    @Override
    public CSTranslatedNode unwrap() {
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
    public String getZanataId() {
        return "CS" + getCSTranslatedNode().getCSNodeId() + "-" + getCSTranslatedNode().getCSNodeRevision();
    }

    @Override
    public CollectionWrapper<CSTranslatedNodeStringWrapper> getTranslatedStrings() {
        return getWrapperFactory().createCollection(getCSTranslatedNode().getCSTranslatedNodeStrings(), CSTranslatedNodeString.class,
                isRevisionEntity(), CSTranslatedNodeStringWrapper.class);
    }

    @Override
    public void setTranslatedStrings(CollectionWrapper<CSTranslatedNodeStringWrapper> translatedStrings) {
        if (translatedStrings == null) return;

        final List<CSTranslatedNodeStringWrapper> addTranslatedStrings = translatedStrings.getAddItems();
        final List<CSTranslatedNodeStringWrapper> removeTranslatedStrings = translatedStrings.getRemoveItems();
        /*
         * There is no need to do update outgoing topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<CSTranslatedNodeStringWrapper> updateTranslatedStrings = translatedStrings.getUpdateItems();

        // Add Translated Strings
        for (final CSTranslatedNodeStringWrapper addTranslatedString : addTranslatedStrings) {
            getCSTranslatedNode().addTranslatedString((CSTranslatedNodeString) addTranslatedString.unwrap());
        }

        // Remove Translated Strings
        for (final CSTranslatedNodeStringWrapper removeTranslatedString : removeTranslatedStrings) {
            getCSTranslatedNode().removeTranslatedString((CSTranslatedNodeString) removeTranslatedString.unwrap());
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
