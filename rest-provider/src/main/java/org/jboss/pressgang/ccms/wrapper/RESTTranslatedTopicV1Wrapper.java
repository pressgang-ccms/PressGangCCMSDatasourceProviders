package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTopicSourceUrlCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslatedTopicCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.components.ComponentTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTopicV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class RESTTranslatedTopicV1Wrapper extends RESTBaseTopicV1Wrapper<TranslatedTopicWrapper,
        RESTTranslatedTopicV1> implements TranslatedTopicWrapper {

    private RESTTranslatedTopicV1 topic = null;

    protected RESTTranslatedTopicV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedTopicV1 topic,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.topic = RESTEntityProxyFactory.createProxy(providerFactory, topic, isRevision);
    }

    @Override
    protected RESTTranslatedTopicV1 getProxyEntity() {
        return topic;
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
        getProxyEntity().setTitle(title);
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
        getProxyEntity().setTags(tags == null ? null : (RESTTagCollectionV1) tags.unwrap());
    }

    @Override
    public void setTopicId(Integer id) {
        getProxyEntity().explicitSetTopicId(id);
    }

    @Override
    public void setTopicRevision(Integer revision) {
        getProxyEntity().explicitSetTopicRevision(revision);
    }

    @Override
    public Integer getTranslatedTopicId() {
        return getProxyEntity().getTranslatedTopicId();
    }

    @Override
    public void setTranslatedTopicId(Integer translatedTopicId) {
        getProxyEntity().setTranslatedTopicId(translatedTopicId);
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
    public CollectionWrapper<TranslatedTopicWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTTranslatedTopicV1.class, true);
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
    public CollectionWrapper<TranslatedTopicWrapper> getOutgoingRelationships() {
        return getWrapperFactory().createCollection(getProxyEntity().getOutgoingRelationships(), RESTTranslatedTopicV1.class,
                isRevisionEntity());
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TranslatedTopicWrapper> outgoingTopics) {
        getProxyEntity().setOutgoingRelationships((RESTTranslatedTopicCollectionV1) outgoingTopics.unwrap());
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getIncomingRelationships() {
        return getWrapperFactory().createCollection(getProxyEntity().getIncomingRelationships(), RESTTranslatedTopicV1.class,
                isRevisionEntity());
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties) {
        getProxyEntity().setProperties(properties == null ? null : (RESTAssignedPropertyTagCollectionV1) properties.unwrap());
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TranslatedTopicWrapper> incomingTopics) {
        getProxyEntity().setIncomingRelationships((RESTTranslatedTopicCollectionV1) incomingTopics.unwrap());
    }

    @Override
    public void setSourceURLs(CollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        getProxyEntity().setSourceUrls_OTM(sourceURLs == null ? null : (RESTTopicSourceUrlCollectionV1) sourceURLs.unwrap());
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
    public String getInternalURL() {
        return ComponentTranslatedTopicV1.returnInternalURL(getProxyEntity());
    }

    @Override
    public String getPressGangURL() {
        return ComponentTranslatedTopicV1.returnSkynetURL(getProxyEntity());
    }

    @Override
    public TranslatedTopicWrapper clone(boolean deepCopy) {
        return new RESTTranslatedTopicV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity());
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
        getProxyEntity().setTranslationPercentage(percentage);
    }

    @Override
    public Date getHtmlUpdated() {
        return getProxyEntity().getHtmlUpdated();
    }

    @Override
    public void setHtmlUpdated(Date htmlUpdated) {
        getProxyEntity().explicitSetHtmlUpdated(htmlUpdated);
    }

    @Override
    public CollectionWrapper<TranslatedTopicStringWrapper> getTranslatedStrings() {
        return getWrapperFactory().createCollection(getProxyEntity().getTranslatedTopicStrings_OTM(), RESTTranslatedTopicStringV1.class,
                isRevisionEntity(), getProxyEntity());
    }

    @Override
    public TopicWrapper getTopic() {
        return getWrapperFactory().create(getProxyEntity().getTopic(), true);
    }

    @Override
    public void setTopic(TopicWrapper topic) {
        getProxyEntity().setTopic((RESTTopicV1) topic.unwrap());
    }
}
