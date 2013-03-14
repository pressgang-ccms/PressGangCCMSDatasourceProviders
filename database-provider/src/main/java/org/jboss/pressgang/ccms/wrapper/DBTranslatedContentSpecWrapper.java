package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTranslatedContentSpecWrapper extends DBBaseWrapper<TranslatedContentSpecWrapper> implements TranslatedContentSpecWrapper {
    private final TranslatedContentSpec translatedContentSpec;

    public DBTranslatedContentSpecWrapper(final DBProviderFactory providerFactory, final TranslatedContentSpec translatedContentSpec, boolean isRevision) {
        super(providerFactory, isRevision);
        this.translatedContentSpec = translatedContentSpec;
    }

    protected TranslatedContentSpec getCSTranslatedNode() {
        return translatedContentSpec;
    }

    protected ContentSpec getEnversContentSpec() {
        return getCSTranslatedNode().getEnversContentSpec(getEntityManager());
    }

    @Override
    public Integer getId() {
        return getCSTranslatedNode().getId();
    }

    @Override
    public void setId(Integer id) {
        getCSTranslatedNode().setTranslatedContentSpecId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getCSTranslatedNode().getRevision();
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getCSTranslatedNode()),
                TranslatedContentSpec.class, true);
    }

    @Override
    public TranslatedContentSpec unwrap() {
        return translatedContentSpec;
    }

    @Override
    public Integer getContentSpecId() {
        return getCSTranslatedNode().getContentSpecId();
    }

    @Override
    public void setContentSpecId(Integer id) {
        getCSTranslatedNode().setContentSpecId(id);
    }

    @Override
    public Integer getContentSpecRevision() {
        return getCSTranslatedNode().getContentSpecRevision();
    }

    @Override
    public void setContentSpecRevision(Integer revision) {
        getCSTranslatedNode().setContentSpecRevision(revision);
    }

    @Override
    public String getZanataId() {
        return "CS" + getCSTranslatedNode().getContentSpecId() + "-" + getCSTranslatedNode().getContentSpecRevision();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes() {
        final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                getCSTranslatedNode().getTranslatedCSNodes(), TranslatedCSNode.class, isRevisionEntity(),
                TranslatedCSNodeWrapper.class);
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
    }

    @Override
    public void setTranslatedNodes(UpdateableCollectionWrapper<TranslatedCSNodeWrapper> translatedNodes) {
        if (translatedNodes == null) return;

        final List<TranslatedCSNodeWrapper> addTranslatedNodes = translatedNodes.getAddItems();
        final List<TranslatedCSNodeWrapper> removeTranslatedNodes = translatedNodes.getRemoveItems();
        /*
         * There is no need to do update outgoing topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<TranslatedCSNodeWrapper> updateTranslatedNodes = translatedNodes.getUpdateItems();

        // Add Translated Nodes
        for (final TranslatedCSNodeWrapper addTranslatedNode : addTranslatedNodes) {
            getCSTranslatedNode().addTranslatedNode((TranslatedCSNode) addTranslatedNode.unwrap());
        }

        // Remove Translated Nodes
        for (final TranslatedCSNodeWrapper removeTranslatedNode : removeTranslatedNodes) {
            getCSTranslatedNode().removeTranslatedNode((TranslatedCSNode) removeTranslatedNode.unwrap());
        }
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return getWrapperFactory().create(getEnversContentSpec(), true);
    }

    @Override
    public void setContentSpec(ContentSpecWrapper node) {
        getCSTranslatedNode().setEnversContentSpec(node == null ? null : (ContentSpec) node.unwrap());
    }
}
