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
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicStringCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class RESTTranslatedTopicV1Wrapper extends RESTBaseTopicV1Wrapper<TranslatedTopicWrapper,
        RESTTranslatedTopicV1> implements TranslatedTopicWrapper {

    protected RESTTranslatedTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedTopicV1 topic, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, topic, isRevision, isNewEntity);
    }

    protected RESTTranslatedTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedTopicV1 topic, boolean isRevision,
            boolean isNewEntity, final Collection<String> ignoreMethods) {
        super(providerFactory, topic, isRevision, isNewEntity, ignoreMethods);
    }

    @Override
    public Integer getTopicId() {
        return getProxyEntity().getTopicId();
    }

    @Override
    public Integer getTopicRevision() {
        return getProxyEntity().getTopicRevision();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setTitle(title);
    }

    @Override
    public void setXml(String xml) {
        getEntity().explicitSetXml(xml);
    }

    @Override
    public void setLocale(String locale) {
        getEntity().explicitSetLocale(locale);
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        getEntity().setTags(tags == null ? null : (RESTTagCollectionV1) tags.unwrap());
    }

    @Override
    public void setTopicId(Integer id) {
        getEntity().explicitSetTopicId(id);
    }

    @Override
    public void setTopicRevision(Integer revision) {
        getEntity().explicitSetTopicRevision(revision);
    }

    @Override
    public Integer getTranslatedTopicId() {
        return getProxyEntity().getTranslatedTopicId();
    }

    @Override
    public void setTranslatedTopicId(Integer translatedTopicId) {
        getEntity().setTranslatedTopicId(translatedTopicId);
    }

    @Override
    public String getZanataId() {
        return ComponentTranslatedTopicV1.returnZanataId(getProxyEntity());
    }

    @Override
    public boolean getContainsFuzzyTranslations() {
        return getProxyEntity().getContainsFuzzyTranslation();
    }

    @Override
    public String getBugzillaBuildId() {
        return ComponentTranslatedTopicV1.returnBugzillaBuildId(getProxyEntity());
    }

    @Override
    public String getEditorURL(final ZanataDetails zanataDetails) {
        return ComponentTranslatedTopicV1.returnEditorURL(getProxyEntity(), zanataDetails);
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TranslatedTopicWrapper> outgoingTopics) {
        getEntity().setOutgoingRelationships((RESTTranslatedTopicCollectionV1) outgoingTopics.unwrap());
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties) {
        getEntity().setProperties(properties == null ? null : (RESTAssignedPropertyTagCollectionV1) properties.unwrap());
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TranslatedTopicWrapper> incomingTopics) {
        getProxyEntity().setIncomingRelationships((RESTTranslatedTopicCollectionV1) incomingTopics.unwrap());
    }

    @Override
    public void setSourceURLs(UpdateableCollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        getEntity().setSourceUrls_OTM(sourceURLs == null ? null : (RESTTopicSourceUrlCollectionV1) sourceURLs.unwrap());
    }

    @Override
    public String getXRefId() {
        return ComponentTranslatedTopicV1.returnXRefID(getProxyEntity());
    }

    @Override
    public String getXRefPropertyOrId(int propertyId) {
        return ComponentTranslatedTopicV1.returnXrefPropertyOrId(getProxyEntity(), propertyId);
    }

    @Override
    public String getPressGangURL() {
        return ComponentTranslatedTopicV1.returnPressGangCCMSURL(getProxyEntity());
    }

    @Override
    public TranslatedTopicWrapper clone(boolean deepCopy) {
        return new RESTTranslatedTopicV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getErrorXRefId() {
        return ComponentTranslatedTopicV1.returnErrorXRefID(getProxyEntity());
    }

    @Override
    public Integer getTranslationPercentage() {
        return getProxyEntity().getTranslationPercentage();
    }

    @Override
    public void setTranslationPercentage(Integer percentage) {
        getEntity().explicitSetTranslationPercentage(percentage);
    }

    @Override
    public String getTranslatedXMLCondition() {
        return getProxyEntity().getTranslatedXMLCondition();
    }

    @Override
    public void setTranslatedXMLCondition(String translatedXMLCondition) {
        getEntity().explicitSetTranslatedXMLCondition(translatedXMLCondition);
    }

    @Override
    public String getTranslatedAdditionalXML() {
        return getProxyEntity().getTranslatedAdditionalXML();
    }

    @Override
    public void setTranslatedAdditionalXML(String translatedAdditionalXML) {
        getEntity().explicitSetTranslatedAdditionalXML(translatedAdditionalXML);
    }

    @Override
    public String getCustomEntities() {
        return getEntity().getCustomEntities();
    }

    @Override
    public void setCustomEntities(String customEntities) {
        getEntity().explicitSetCustomEntities(customEntities);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings() {
        return (UpdateableCollectionWrapper<TranslatedTopicStringWrapper>) RESTCollectionWrapperBuilder
                .<TranslatedTopicStringWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTranslatedTopicStrings_OTM())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public void setTranslatedTopicStrings(UpdateableCollectionWrapper<TranslatedTopicStringWrapper> translatedStrings) {
        getEntity().explicitSetTranslatedTopicString_OTM(
                translatedStrings == null ? null : (RESTTranslatedTopicStringCollectionV1) translatedStrings.unwrap());
    }

    @Override
    public TopicWrapper getTopic() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getTopic())
                .isRevision()
                .build();
    }

    @Override
    public void setTopic(TopicWrapper topic) {
        getEntity().setTopic((RESTTopicV1) topic.unwrap());
    }

    @Override
    public TranslatedCSNodeWrapper getTranslatedCSNode() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getTranslatedCSNode())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public void setTranslatedCSNode(TranslatedCSNodeWrapper translatedCSNode) {
        getEntity().explicitSetTranslatedCSNode(translatedCSNode == null ? null : (RESTTranslatedCSNodeV1) translatedCSNode.unwrap());
    }
}
