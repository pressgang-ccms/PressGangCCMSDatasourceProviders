/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseAuditedEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedCSNodeStringCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTranslatedStringCollectionHandler;

public class DBTranslatedCSNodeWrapper extends DBBaseAuditedEntityWrapper<TranslatedCSNodeWrapper, TranslatedCSNode> implements TranslatedCSNodeWrapper {
    private final DBTranslatedStringCollectionHandler<TranslatedCSNodeString> translatedStringCollectionHandler;

    private final TranslatedCSNode csNode;

    public DBTranslatedCSNodeWrapper(final DBProviderFactory providerFactory, final TranslatedCSNode csNode, boolean isRevision) {
        super(providerFactory, isRevision, TranslatedCSNode.class);
        this.csNode = csNode;
        translatedStringCollectionHandler = new DBTranslatedStringCollectionHandler<TranslatedCSNodeString>(csNode);
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
                TranslatedCSNodeStringWrapper.class, translatedStringCollectionHandler);
        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) collection;
    }

    @Override
    public void setTranslatedStrings(UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> translatedStrings) {
        if (translatedStrings == null) return;
        final DBTranslatedCSNodeStringCollectionWrapper dbTranslatedStrings = (DBTranslatedCSNodeStringCollectionWrapper) translatedStrings;
        dbTranslatedStrings.setHandler(translatedStringCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbTranslatedStrings.unwrap() != getEntity().getTranslatedCSNodeStrings()) {
            // Add new translated strings and skip any existing strings
            final Set<TranslatedCSNodeString> currentStrings = new HashSet<TranslatedCSNodeString>(
                    getEntity().getTranslatedCSNodeStrings());
            final Collection<TranslatedCSNodeString> newStrings = dbTranslatedStrings.unwrap();
            for (final TranslatedCSNodeString string : newStrings) {
                if (currentStrings.contains(string)) {
                    currentStrings.remove(string);
                    continue;
                } else {
                    getEntity().addTranslatedString(string);
                }
            }

            // Remove strings that should no longer exist in the collection
            for (final TranslatedCSNodeString removeString : currentStrings) {
                getEntity().removeTranslatedString(removeString);
            }
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
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics() {
        return getWrapperFactory().createCollection(getEntity().getTranslatedTopicDatas(), TranslatedTopicData.class,
                isRevisionEntity(), TranslatedTopicWrapper.class);
    }
}
