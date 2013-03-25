package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.enums.RESTXMLDoctype;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class RESTTopicV1Wrapper extends RESTBaseTopicV1Wrapper<TopicWrapper, RESTTopicV1> implements TopicWrapper {

    private final RESTTopicV1 topic;

    protected RESTTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTTopicV1 topic, boolean isRevision) {
        super(providerFactory, isRevision);
        this.topic = RESTEntityProxyFactory.createProxy(providerFactory, topic, isRevision);
    }

    @Override
    protected RESTTopicV1 getProxyEntity() {
        return topic;
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
    public Integer getXmlDoctype() {
        return RESTXMLDoctype.getXMLDoctypeId(getProxyEntity().getXmlDoctype());
    }

    @Override
    public void setXmlDoctype(Integer doctypeId) {
        getEntity().explicitSetXmlDoctype(RESTXMLDoctype.getXMLDoctype(doctypeId));
    }

    @Override
    public CollectionWrapper<TopicWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTopicV1.class, true);
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
    public CollectionWrapper<TopicWrapper> getOutgoingRelationships() {
        return getWrapperFactory().createCollection(getProxyEntity().getOutgoingRelationships(), RESTTopicV1.class, isRevisionEntity());
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TopicWrapper> outgoingTopics) {
        getProxyEntity().explicitSetOutgoingRelationships(outgoingTopics == null ? null : (RESTTopicCollectionV1) outgoingTopics.unwrap());
    }

    @Override
    public CollectionWrapper<TopicWrapper> getIncomingRelationships() {
        return getWrapperFactory().createCollection(getProxyEntity().getIncomingRelationships(), RESTTopicV1.class, isRevisionEntity());
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
    public void setSourceURLs(CollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        getProxyEntity().explicitSetSourceUrls_OTM(sourceURLs == null ? null : (RESTTopicSourceUrlCollectionV1) sourceURLs.unwrap());
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics() {
        return getWrapperFactory().createCollection(getProxyEntity().getTranslatedTopics_OTM(), RESTTranslatedTopicV1.class,
                isRevisionEntity());
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
    public String getInternalURL() {
        return ComponentTopicV1.returnInternalURL(getProxyEntity());
    }

    @Override
    public TopicWrapper clone(boolean deepCopy) {
        return new RESTTopicV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity());
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
    public void setHtml(String html) {
        getProxyEntity().explicitSetHtml(html);
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
