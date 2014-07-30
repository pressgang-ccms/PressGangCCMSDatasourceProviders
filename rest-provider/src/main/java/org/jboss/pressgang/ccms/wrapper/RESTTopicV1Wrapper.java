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
import java.util.Date;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.enums.RESTXMLFormat;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class RESTTopicV1Wrapper extends RESTBaseTopicV1Wrapper<TopicWrapper, RESTTopicV1> implements TopicWrapper {

    protected RESTTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicV1 topic, boolean isRevision,
            boolean isNewEntity) {
        super(providerFactory, topic, isRevision, isNewEntity);
    }

    protected RESTTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicV1 topic, boolean isRevision,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, topic, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public Integer getTopicId() {
        return getId();
    }

    @Override
    public Integer getTopicRevision() {
        return getRevision();
    }

    @Override
    public String getDescription() {
        return getProxyEntity().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getProxyEntity().explicitSetDescription(description);
    }

    @Override
    public Date getCreated() {
        return getProxyEntity().getCreated();
    }

    @Override
    public void setCreated(Date created) {
        getProxyEntity().setCreated(created);
    }

    @Override
    public Date getLastModified() {
        return getProxyEntity().getLastModified();
    }

    @Override
    public void setLastModified(Date lastModified) {
        getProxyEntity().setLastModified(lastModified);
    }

    @Override
    public void setXmlFormat(Integer formatId) {
        getEntity().explicitSetXmlDoctype(RESTXMLFormat.getXMLFormat(formatId));
    }

    @Override
    public String getBugzillaBuildId() {
        return ComponentTopicV1.returnBugzillaBuildId(getProxyEntity());
    }

    @Override
    public String getEditorURL(final ZanataDetails zanataDetails) {
        return ComponentTopicV1.returnEditorURL(getProxyEntity());
    }

    @Override
    public String getEditorURL() {
        return ComponentTopicV1.returnEditorURL(getProxyEntity());
    }

    @Override
    public String getPressGangURL() {
        return ComponentTopicV1.returnPressGangCCMSURL(getProxyEntity());
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TopicWrapper> outgoingTopics) {
        getProxyEntity().explicitSetOutgoingRelationships(outgoingTopics == null ? null : (RESTTopicCollectionV1) outgoingTopics.unwrap());
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TopicWrapper> incomingTopics) {
        getProxyEntity().explicitSetIncomingRelationships(incomingTopics == null ? null : (RESTTopicCollectionV1) incomingTopics.unwrap());
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties) {
        getProxyEntity().explicitSetProperties(properties == null ? null : (RESTAssignedPropertyTagCollectionV1) properties.unwrap());
    }

    @Override
    public void setSourceURLs(UpdateableCollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        getProxyEntity().explicitSetSourceUrls_OTM(sourceURLs == null ? null : (RESTTopicSourceUrlCollectionV1) sourceURLs.unwrap());
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics() {
        return RESTCollectionWrapperBuilder.<TranslatedTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getTranslatedTopics_OTM())
                .isRevisionCollection(isRevisionEntity())
                .build();
    }

    @Override
    public String getXRefId() {
        return ComponentTopicV1.returnXRefID(getProxyEntity());
    }

    @Override
    public String getXRefPropertyOrId(int propertyId) {
        return ComponentTopicV1.returnXrefPropertyOrId(getProxyEntity(), propertyId);
    }

    @Override
    public TopicWrapper clone(boolean deepCopy) {
        return new RESTTopicV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getErrorXRefId() {
        return ComponentTopicV1.returnErrorXRefID(getProxyEntity());
    }

    @Override
    public void setXml(String xml) {
        getProxyEntity().explicitSetXml(xml);
    }

    @Override
    public void setLocale(String locale) {
        getProxyEntity().explicitSetLocale(locale);
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        getProxyEntity().explicitSetTags(tags == null ? null : (RESTTagCollectionV1) tags.unwrap());
    }

    @Override
    public void setTitle(String title) {
        getProxyEntity().explicitSetTitle(title);
    }
}
