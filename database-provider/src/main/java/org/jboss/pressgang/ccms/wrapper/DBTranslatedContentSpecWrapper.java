/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTranslatedCSNodeCollectionHandler;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTranslatedContentSpecWrapper extends DBBaseEntityWrapper<TranslatedContentSpecWrapper,
        TranslatedContentSpec> implements TranslatedContentSpecWrapper {
    private final DBTranslatedCSNodeCollectionHandler translatedCSNodeCollectionHandler;

    private final TranslatedContentSpec translatedContentSpec;

    public DBTranslatedContentSpecWrapper(final DBProviderFactory providerFactory, final TranslatedContentSpec translatedContentSpec,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedContentSpec.class);
        this.translatedContentSpec = translatedContentSpec;
        translatedCSNodeCollectionHandler = new DBTranslatedCSNodeCollectionHandler(translatedContentSpec);
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
                getEntity().getTranslatedCSNodes(), TranslatedCSNode.class, isRevisionEntity(), TranslatedCSNodeWrapper.class,
                translatedCSNodeCollectionHandler);
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) collection;
    }

    @Override
    public void setTranslatedNodes(UpdateableCollectionWrapper<TranslatedCSNodeWrapper> translatedNodes) {
        if (translatedNodes == null) return;
        final DBTranslatedCSNodeCollectionWrapper dbTranslatedNodes = (DBTranslatedCSNodeCollectionWrapper) translatedNodes;
        dbTranslatedNodes.setHandler(translatedCSNodeCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbTranslatedNodes.unwrap() != getEntity().getTranslatedCSNodes()) {
            // Add new translated nodes and skip any existing nodes
            final Set<TranslatedCSNode> currentNodes = new HashSet<TranslatedCSNode>(getEntity().getTranslatedCSNodes());
            final Collection<TranslatedCSNode> newNodes = dbTranslatedNodes.unwrap();
            for (final TranslatedCSNode node : newNodes) {
                if (currentNodes.contains(node)) {
                    currentNodes.remove(node);
                    continue;
                } else {
                    getEntity().addTranslatedNode(node);
                }
            }

            // Remove nodes that should no longer exist in the collection
            for (final TranslatedCSNode removeNode : currentNodes) {
                getEntity().removeTranslatedNode(removeNode);
            }
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
