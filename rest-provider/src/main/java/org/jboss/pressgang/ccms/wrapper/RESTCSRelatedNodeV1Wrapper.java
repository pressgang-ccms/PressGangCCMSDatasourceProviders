/*
  Copyright 2011-2014 Red Hat

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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeRelationshipModeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeRelationshipTypeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeTypeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTCSRelatedNodeV1Wrapper extends RESTBaseEntityWrapper<CSRelatedNodeWrapper, RESTCSRelatedNodeV1> implements CSRelatedNodeWrapper {

    protected RESTCSRelatedNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeV1 entity, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTCSRelatedNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSRelatedNodeV1 entity, boolean isRevision,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public CSRelatedNodeWrapper clone(boolean deepCopy) {
        return new RESTCSRelatedNodeV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setTitle(title);
    }

    @Override
    public String getTargetId() {
        return getProxyEntity().getTargetId();
    }

    @Override
    public void setTargetId(String targetId) {
        getEntity().setTargetId(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getProxyEntity().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getEntity().setAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getProxyEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().setCondition(getCondition());
    }

    @Override
    public Integer getEntityId() {
        return getProxyEntity().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getEntity().setEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getProxyEntity().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getEntity().setEntityRevision(revision);
    }

    @Override
    public Integer getRelationshipType() {
        return RESTCSNodeRelationshipTypeV1.getRelationshipTypeId(getProxyEntity().getRelationshipType());
    }

    @Override
    public void setRelationshipType(Integer typeId) {
        getEntity().explicitSetRelationshipType(RESTCSNodeRelationshipTypeV1.getRelationshipType(typeId));
    }

    @Override
    public Integer getRelationshipId() {
        return getProxyEntity().getRelationshipId();
    }

    @Override
    public void setRelationshipId(Integer id) {
        getEntity().setRelationshipId(id);
    }

    @Override
    public Integer getRelationshipSort() {
        return getProxyEntity().getRelationshipSort();
    }

    @Override
    public void setRelationshipSort(Integer sort) {
        getEntity().setRelationshipSort(sort);
    }

    @Override
    public Integer getNodeType() {
        return RESTCSNodeTypeV1.getNodeTypeId(getProxyEntity().getNodeType());
    }

    @Override
    public void setNodeType(Integer typeId) {
        getEntity().setNodeType(RESTCSNodeTypeV1.getNodeType(typeId));
    }

    @Override
    public Integer getRelationshipMode() {
        return RESTCSNodeRelationshipModeV1.getRelationshipModeId(getProxyEntity().getRelationshipMode());
    }

    @Override
    public void setRelationshipMode(Integer mode) {
        getEntity().setRelationshipMode(RESTCSNodeRelationshipModeV1.getRelationshipMode(mode));
    }
}
