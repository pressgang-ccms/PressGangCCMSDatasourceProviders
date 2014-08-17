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

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseAuditedEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSRelatedNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCSNodeCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBRelatedFromCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBRelatedToCollectionHandler;

public class DBCSNodeWrapper extends DBBaseAuditedEntityWrapper<CSNodeWrapper, CSNode> implements CSNodeWrapper {
    private final DBCSNodeCollectionHandler csNodeCollectionHandler;
    private final DBRelatedToCollectionHandler<CSNodeToCSNode> relatedToCollectionHandler;
    private final DBRelatedFromCollectionHandler<CSNodeToCSNode> relatedFromNodeCollectionHandler;

    private final CSNode csNode;

    public DBCSNodeWrapper(final DBProviderFactory providerFactory, final CSNode csNode, boolean isRevision) {
        super(providerFactory, isRevision, CSNode.class);
        this.csNode = csNode;
        csNodeCollectionHandler = new DBCSNodeCollectionHandler(csNode);
        relatedToCollectionHandler = new DBRelatedToCollectionHandler<CSNodeToCSNode>(csNode);
        relatedFromNodeCollectionHandler = new DBRelatedFromCollectionHandler<CSNodeToCSNode>(csNode);
    }

    @Override
    protected CSNode getEntity() {
        return csNode;
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getRelatedToNodes(),
                CSNodeToCSNode.class, isRevisionEntity(), relatedToCollectionHandler);
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes) {
        if (relatedToNodes == null) return;
        final DBCSRelatedNodeCollectionWrapper dbRelatedToNodes = (DBCSRelatedNodeCollectionWrapper) relatedToNodes;
        dbRelatedToNodes.setHandler(relatedToCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbRelatedToNodes.unwrap() != getEntity().getRelatedToNodes()) {
            // Add new related nodes and skip any existing relationships
            final Set<CSNodeToCSNode> currentRelationships = new HashSet<CSNodeToCSNode>(getEntity().getRelatedToNodes());
            final Collection<CSNodeToCSNode> newRelationships = dbRelatedToNodes.unwrap();
            for (final CSNodeToCSNode relationship : newRelationships) {
                if (currentRelationships.contains(relationship)) {
                    currentRelationships.remove(relationship);
                    continue;
                } else {
                    getEntity().addRelationshipTo(relationship);
                }
            }

            // Remove children that should no longer exist in the collection
            for (final CSNodeToCSNode removeRelationship : currentRelationships) {
                getEntity().removeRelationshipTo(removeRelationship);
            }
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes() {
        final CollectionWrapper<CSRelatedNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getRelatedFromNodes(),
                CSNodeToCSNode.class, isRevisionEntity(), relatedFromNodeCollectionHandler);
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) collection;
    }

    @Override
    public void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes) {
        if (relatedFromNodes == null) return;
        final DBCSRelatedNodeCollectionWrapper dbRelatedFromNodes = (DBCSRelatedNodeCollectionWrapper) relatedFromNodes;
        dbRelatedFromNodes.setHandler(relatedFromNodeCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbRelatedFromNodes.unwrap() != getEntity().getRelatedFromNodes()) {
            // Add new related nodes and skip any existing relationships
            final Set<CSNodeToCSNode> currentRelationships = new HashSet<CSNodeToCSNode>(getEntity().getRelatedFromNodes());
            final Collection<CSNodeToCSNode> newRelationships = dbRelatedFromNodes.unwrap();
            for (final CSNodeToCSNode relationship : newRelationships) {
                if (currentRelationships.contains(relationship)) {
                    currentRelationships.remove(relationship);
                    continue;
                } else {
                    getEntity().addRelationshipFrom(relationship);
                }
            }

            // Remove children that should no longer exist in the collection
            for (final CSNodeToCSNode removeRelationship : currentRelationships) {
                getEntity().removeRelationshipFrom(removeRelationship);
            }
        }
    }

    @Override
    public CSNodeWrapper getParent() {
        return getWrapperFactory().create(getEntity().getParent(), isRevisionEntity());
    }

    @Override
    public void setParent(CSNodeWrapper parent) {
        getEntity().setParent(parent == null ? null : (CSNode) parent.unwrap());
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return getWrapperFactory().create(getEntity().getContentSpec(), isRevisionEntity(), ContentSpecWrapper.class);
    }

    @Override
    public void setContentSpec(ContentSpecWrapper contentSpec) {
        getEntity().setContentSpec(contentSpec == null ? null : (ContentSpec) contentSpec.unwrap());
    }

    @Override
    public String getInheritedCondition() {
        return getEntity().getInheritedCondition();
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes() {
        return getWrapperFactory().createCollection(getEntity().getTranslatedNodes(getEntityManager(), getEntityRevision()),
                TranslatedCSNode.class, isRevisionEntity(), TranslatedCSNodeWrapper.class);
    }

    @Override
    public CSInfoNodeWrapper getInfoTopicNode() {
        return getWrapperFactory().create(getEntity().getCSInfoNode(), isRevisionEntity(), CSInfoNodeWrapper.class);
    }

    @Override
    public void setInfoTopicNode(CSInfoNodeWrapper csNodeInfo) {
        final CSInfoNode csInfoNodeEntity = csNodeInfo == null ? null : (CSInfoNode) csNodeInfo.unwrap();
        if (getEntity().getCSInfoNode() != null) {
            getEntity().getCSInfoNode().setCSNode(null);
        }
        getEntity().setCSInfoNode(csInfoNodeEntity);
        if (csInfoNodeEntity != null) {
            csInfoNodeEntity.setCSNode(getEntity());
        }
    }

    @Override
    public String getFixedURL() {
        return getEntity().getCSNodeURL() == null ? null : getEntity().getCSNodeURL().getUrl();
    }

    @Override
    public void setFixedURL(String fixedURL) {
        if (isNullOrEmpty(fixedURL) && getEntity().getCSNodeURL() != null) {
            getEntityManager().remove(getEntity().getCSNodeURL());
            getEntity().setCSNodeURL(null);
        } else {
            getEntity().setFixedUrl(fixedURL);
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getChildren() {
        final CollectionWrapper<CSNodeWrapper> collection = getWrapperFactory().createCollection(getEntity().getChildren(), CSNode.class,
                isRevisionEntity(), csNodeCollectionHandler);
        return (UpdateableCollectionWrapper<CSNodeWrapper>) collection;
    }

    @Override
    public void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes) {
        if (nodes == null) return;
        final DBCSNodeCollectionWrapper dbNodes = (DBCSNodeCollectionWrapper) nodes;
        dbNodes.setHandler(csNodeCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbNodes.unwrap() != getEntity().getChildren()) {
            // Add new children and skip any existing children
            final Set<CSNode> currentChildren = new HashSet<CSNode>(getEntity().getChildren());
            final Collection<CSNode> newChildren = dbNodes.unwrap();
            for (final CSNode child : newChildren) {
                if (currentChildren.contains(child)) {
                    currentChildren.remove(child);
                    continue;
                } else {
                    getEntity().addChild(child);
                }
            }

            // Remove children that should no longer exist in the collection
            for (final CSNode removeChild : currentChildren) {
                getEntity().removeChild(removeChild);
            }
        }
    }

    @Override
    public String getTitle() {
        return getEntity().getCSNodeTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setCSNodeTitle(title);
    }

    @Override
    public String getTargetId() {
        return getEntity().getCSNodeTargetId();
    }

    @Override
    public void setTargetId(String targetId) {
        getEntity().setCSNodeTargetId(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getEntity().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getEntity().setAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(condition);
    }

    @Override
    public Integer getEntityId() {
        return getEntity().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getEntity().setEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getEntity().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getEntity().setEntityRevision(revision);
    }

    @Override
    public CSNodeWrapper getNextNode() {
        return getWrapperFactory().create(getEntity().getNext(), isRevisionEntity());
    }

    @Override
    public void setNextNode(CSNodeWrapper nextNode) {
        getEntity().setNextAndClean(nextNode == null ? null : (CSNode) nextNode.unwrap());
    }

    @Override
    public Integer getNodeType() {
        return getEntity().getCSNodeType();
    }

    @Override
    public void setNodeType(Integer typeId) {
        getEntity().setCSNodeType(typeId);
    }

    @Override
    public void setId(Integer id) {
        getEntity().setCSNodeId(id);
    }
}
