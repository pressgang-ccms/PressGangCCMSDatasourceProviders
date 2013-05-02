package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTranslatedContentSpecWrapper extends DBBaseWrapper<TranslatedContentSpecWrapper,
        TranslatedContentSpec> implements TranslatedContentSpecWrapper {
    private final TranslatedContentSpec translatedContentSpec;

    public DBTranslatedContentSpecWrapper(final DBProviderFactory providerFactory, final TranslatedContentSpec translatedContentSpec,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedContentSpec.class);
        this.translatedContentSpec = translatedContentSpec;
    }

    @Override
    protected TranslatedContentSpec getEntity() {
        return translatedContentSpec;
    }

    protected ContentSpec getEnversContentSpec() {
        return getEntity().getEnversContentSpec(getEntityManager());
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTranslatedContentSpecId(id);
    }

    @Override
    public TranslatedContentSpec unwrap() {
        return translatedContentSpec;
    }

    @Override
    public Integer getContentSpecId() {
        return getEntity().getContentSpecId();
    }

    @Override
    public void setContentSpecId(Integer id) {
        getEntity().setContentSpecId(id);
    }

    @Override
    public Integer getContentSpecRevision() {
        return getEntity().getContentSpecRevision();
    }

    @Override
    public void setContentSpecRevision(Integer revision) {
        getEntity().setContentSpecRevision(revision);
    }

    @Override
    public String getZanataId() {
        return "CS" + getEntity().getContentSpecId() + "-" + getEntity().getContentSpecRevision();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes() {
        final CollectionWrapper<TranslatedCSNodeWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getTranslatedCSNodes(), TranslatedCSNode.class, isRevisionEntity(), TranslatedCSNodeWrapper.class);
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
            getEntity().addTranslatedNode((TranslatedCSNode) addTranslatedNode.unwrap());
        }

        // Remove Translated Nodes
        for (final TranslatedCSNodeWrapper removeTranslatedNode : removeTranslatedNodes) {
            getEntity().removeTranslatedNode((TranslatedCSNode) removeTranslatedNode.unwrap());
        }
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return getWrapperFactory().create(getEnversContentSpec(), true);
    }

    @Override
    public void setContentSpec(ContentSpecWrapper node) {
        getEntity().setEnversContentSpec(node == null ? null : (ContentSpec) node.unwrap());
    }

    @Override
    public String getEditorURL(ZanataDetails zanataDetails, String locale) {
        final String zanataServerUrl = zanataDetails == null ? null : zanataDetails.getServer();
        final String zanataProject = zanataDetails == null ? null : zanataDetails.getProject();
        final String zanataVersion = zanataDetails == null ? null : zanataDetails.getVersion();

        if (zanataServerUrl != null && !zanataServerUrl.isEmpty() && zanataProject != null && !zanataProject.isEmpty() && zanataVersion
                != null && !zanataVersion.isEmpty()) {
            final String zanataId = getZanataId();
            return zanataServerUrl + "webtrans/Application.html?project=" + zanataProject + "&amp;iteration=" + zanataVersion +
                    "&amp;doc=" + zanataId + "&amp;localeId=" + locale + "#view:doc;doc:" + zanataId;
        } else {
            return null;
        }
    }
}
