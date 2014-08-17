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
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseAuditedEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTTranslatedCSNodeV1Wrapper extends RESTBaseAuditedEntityWrapper<TranslatedCSNodeWrapper,
        RESTTranslatedCSNodeV1> implements TranslatedCSNodeWrapper {

    protected RESTTranslatedCSNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeV1 entity,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTTranslatedCSNodeV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeV1 entity,
            boolean isRevision, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public TranslatedCSNodeWrapper clone(boolean deepCopy) {
        return new RESTTranslatedCSNodeV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public Integer getNodeId() {
        return getProxyEntity().getNodeId();
    }

    @Override
    public void setNodeId(Integer id) {
        getEntity().explicitSetNodeId(id);
    }

    @Override
    public Integer getNodeRevision() {
        return getProxyEntity().getNodeRevision();
    }

    @Override
    public void setNodeRevision(Integer revision) {
        getEntity().explicitSetNodeRevision(revision);
    }

    @Override
    public String getOriginalString() {
        return getProxyEntity().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getEntity().explicitSetOriginalString(originalString);
    }

    @Override
    public String getZanataId() {
        return "CS" + getNodeId() + "-" + getNodeRevision();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedStrings() {
        return (UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper>) RESTCollectionWrapperBuilder
                .<TranslatedCSNodeStringWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTranslatedNodeStrings_OTM())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public void setTranslatedStrings(UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> translatedStrings) {
        getEntity().explicitSetTranslatedNodeString_OTM(
                translatedStrings == null ? null : (RESTTranslatedCSNodeStringCollectionV1) translatedStrings.unwrap());
    }

    @Override
    public CSNodeWrapper getCSNode() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getNode())
                .expandedMethods(RESTCSNodeProvider.DEFAULT_METHODS)
                .isRevision()
                .build();
    }

    @Override
    public void setCSNode(CSNodeWrapper node) {
        getEntity().setNode(node == null ? null : (RESTCSNodeV1) node.unwrap());
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getTranslatedContentSpec())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics() {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTranslatedTopics_OTM())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }
}
