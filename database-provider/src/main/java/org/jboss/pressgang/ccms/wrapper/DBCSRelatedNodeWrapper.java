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

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBCSRelatedNodeWrapper extends DBBaseEntityWrapper<CSRelatedNodeWrapper, CSNodeToCSNode> implements CSRelatedNodeWrapper {
    private final CSNodeToCSNode csNodeToCSNode;

    public DBCSRelatedNodeWrapper(final DBProviderFactory providerFactory, final CSNodeToCSNode csNodeToCSNode, boolean isRevision) {
        super(providerFactory, isRevision, CSNodeToCSNode.class);
        this.csNodeToCSNode = csNodeToCSNode;
    }

    @Override
    protected CSNodeToCSNode getEntity() {
        return csNodeToCSNode;
    }

    protected CSNode getCSNode() {
        return getEntity().getRelatedNode();
    }

    @Override
    public String getTitle() {
        return getCSNode().getCSNodeTitle();
    }

    @Override
    public void setTitle(String title) {
        getCSNode().setCSNodeTitle(title);
    }

    @Override
    public String getTargetId() {
        return getCSNode().getCSNodeTargetId();
    }

    @Override
    public void setTargetId(String targetId) {
        getCSNode().setCSNodeTargetId(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getCSNode().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getCSNode().setAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getCSNode().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getCSNode().setCondition(condition);
    }

    @Override
    public Integer getEntityId() {
        return getCSNode().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getCSNode().setEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getCSNode().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getCSNode().setEntityRevision(revision);
    }

    @Override
    public Integer getNodeType() {
        return getCSNode().getCSNodeType();
    }

    @Override
    public void setNodeType(Integer typeId) {
        getCSNode().setCSNodeType(typeId);
    }

    @Override
    public Integer getId() {
        return getCSNode().getId();
    }

    @Override
    public void setId(Integer id) {
        getCSNode().setCSNodeId(id);
    }

    @Override
    public Integer getRelationshipType() {
        return getEntity().getRelationshipType();
    }

    @Override
    public void setRelationshipType(Integer typeId) {
        getEntity().setRelationshipType(typeId);
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }

    @Override
    public void setRelationshipId(Integer id) {
        getEntity().setCSNodeToCSNodeId(id);
    }

    @Override
    public Integer getRelationshipSort() {
        return getEntity().getRelationshipSort();
    }

    @Override
    public void setRelationshipSort(Integer sort) {
        getEntity().setRelationshipSort(sort);
    }

    @Override
    public Integer getRelationshipMode() {
        return getEntity().getRelationshipMode();
    }

    @Override
    public void setRelationshipMode(Integer mode) {
        getEntity().setRelationshipMode(mode);
    }
}
