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

import org.jboss.pressgang.ccms.provider.RESTCSNodeProvider;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.join.RESTCSRelatedNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.enums.RESTCSNodeTypeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseAuditedEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTCSNodeV1Wrapper extends RESTBaseAuditedEntityWrapper<CSNodeWrapper, RESTCSNodeV1> implements CSNodeWrapper {

    protected RESTCSNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSNodeV1 entity, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTCSNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTCSNodeV1 entity, boolean isRevision,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public CSNodeWrapper clone(boolean deepCopy) {
        return new RESTCSNodeV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity(),
                getProxyProcessedMethodNames());
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getChildren() {
        return (UpdateableCollectionWrapper<CSNodeWrapper>) RESTCollectionWrapperBuilder.<CSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getChildren_OTM())
                .expandedEntityMethods(RESTCSNodeProvider.DEFAULT_METHODS)
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes) {
        getEntity().explicitSetChildren_OTM(nodes == null ? null : (RESTCSNodeCollectionV1) nodes.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes() {
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) RESTCollectionWrapperBuilder.<CSRelatedNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getRelatedToNodes())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes) {
        getEntity().explicitSetRelatedToNodes(relatedToNodes == null ? null : (RESTCSRelatedNodeCollectionV1) relatedToNodes.unwrap());
    }

    @Override
    public UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes() {
        return (UpdateableCollectionWrapper<CSRelatedNodeWrapper>) RESTCollectionWrapperBuilder.<CSRelatedNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getRelatedFromNodes())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes) {
        getEntity().explicitSetRelatedFromNodes(
                relatedFromNodes == null ? null : (RESTCSRelatedNodeCollectionV1) relatedFromNodes.unwrap());
    }

    @Override
    public CSNodeWrapper getParent() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getParent())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public void setParent(CSNodeWrapper parent) {
        getEntity().setParent(parent == null ? null : (RESTCSNodeV1) parent.unwrap());
    }

    @Override
    public ContentSpecWrapper getContentSpec() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getContentSpec())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public void setContentSpec(ContentSpecWrapper contentSpec) {
        getEntity().setContentSpec(contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap());
    }

    @Override
    public String getInheritedCondition() {
        return getProxyEntity().getInheritedCondition();
    }

    @Override
    public CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes() {
        return RESTCollectionWrapperBuilder.<TranslatedCSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTranslatedNodes_OTM())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public CSInfoNodeWrapper getInfoTopicNode() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getInfoTopicNode())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public void setInfoTopicNode(CSInfoNodeWrapper csNodeInfo) {
        getEntity().explicitSetInfoTopicNode(csNodeInfo == null ? null : (RESTCSInfoNodeV1) csNodeInfo.unwrap());
    }

    @Override
    public String getFixedURL() {
        return getEntity().getFixedUrl();
    }

    @Override
    public void setFixedURL(String fixedURL) {
        getEntity().explicitSetFixedUrl(fixedURL);
    }

    @Override
    public String getTitle() {
        return getProxyEntity().getTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().explicitSetTitle(title);
    }

    @Override
    public String getTargetId() {
        return getProxyEntity().getTargetId();
    }

    @Override
    public void setTargetId(String targetId) {
        getEntity().explicitSetTargetId(targetId);
    }

    @Override
    public String getAdditionalText() {
        return getProxyEntity().getAdditionalText();
    }

    @Override
    public void setAdditionalText(String additionalText) {
        getEntity().explicitSetAdditionalText(additionalText);
    }

    @Override
    public String getCondition() {
        return getProxyEntity().getCondition();
    }

    @Override
    public void setCondition(String condition) {
        getEntity().explicitSetCondition(getCondition());
    }

    @Override
    public Integer getEntityId() {
        return getProxyEntity().getEntityId();
    }

    @Override
    public void setEntityId(Integer id) {
        getEntity().explicitSetEntityId(id);
    }

    @Override
    public Integer getEntityRevision() {
        return getProxyEntity().getEntityRevision();
    }

    @Override
    public void setEntityRevision(Integer revision) {
        getEntity().explicitSetEntityRevision(revision);
    }

    @Override
    public CSNodeWrapper getNextNode() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getNextNode())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public void setNextNode(CSNodeWrapper nextNode) {
        getEntity().explicitSetNextNode(nextNode == null ? null : (RESTCSNodeV1) nextNode.unwrap());
    }

    @Override
    public Integer getNodeType() {
        return RESTCSNodeTypeV1.getNodeTypeId(getProxyEntity().getNodeType());
    }

    @Override
    public void setNodeType(Integer typeId) {
        getEntity().explicitSetNodeType(RESTCSNodeTypeV1.getNodeType(typeId));
    }
}
