package org.jboss.pressgang.ccms.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopic;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTopicWrapper extends DBBaseWrapper<TopicWrapper, Topic> implements TopicWrapper {

    private final Topic topic;

    public DBTopicWrapper(final DBProviderFactory providerFactory, final Topic topic, boolean isRevision) {
        super(providerFactory, isRevision, Topic.class);
        this.topic = topic;
    }

    @Override
    protected Topic getEntity() {
        return topic;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTopicId(id);
    }

    @Override
    public Topic unwrap() {
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
    public String getTitle() {
        return getEntity().getTopicTitle();
    }

    @Override
    public void setTitle(String title) {
        getEntity().setTopicTitle(title);
    }

    @Override
    public String getXml() {
        return getEntity().getTopicXML();
    }

    @Override
    public void setXml(String xml) {
        getEntity().setTopicXML(xml);
    }

    @Override
    public String getLocale() {
        return getEntity().getTopicLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().setTopicLocale(locale);
    }

    @Override
    public String getHtml() {
        return getEntity().getTopicRendered();
    }

    @Override
    public void setHtml(String html) {
        getEntity().setTopicRendered(html);
    }

    @Override
    public boolean hasTag(int tagId) {
        return getEntity().isTaggedWith(tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getEntity().getTags(), Tag.class, isRevisionEntity());
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        if (tags == null) return;

        final List<TagWrapper> addTags = tags.getAddItems();
        final List<TagWrapper> removeTags = tags.getRemoveItems();
        /*
         * There is no need to do update tags as when the original entities are altered they will automatically be updated when using
         * database entities.
         */
        //final List<TagWrapper> updateTags = tags.getUpdateItems();

        // Remove Tags
        for (final TagWrapper removeTag : removeTags) {
            getEntity().removeTag((Tag) removeTag.unwrap());
        }

        // Add Tags
        for (final TagWrapper addTag : addTags) {
            getEntity().addTag((Tag) addTag.unwrap());
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getOutgoingRelationships() {
        return getWrapperFactory().createCollection(getEntity().getOutgoingRelatedTopicsArray(), Topic.class, isRevisionEntity());
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TopicWrapper> outgoingTopics) {
        if (outgoingTopics == null) return;

        final List<TopicWrapper> addTopics = outgoingTopics.getAddItems();
        final List<TopicWrapper> removeTopics = outgoingTopics.getRemoveItems();
        /*
         * There is no need to do update outgoing topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<TopicWrapper> updateTopics = outgoingTopics.getUpdateItems();

        // Add Topics
        for (final TopicWrapper addTopic : addTopics) {
            getEntity().addRelationshipTo(getEntityManager(), (Topic) addTopic.unwrap(), 1);
        }

        // Remove Topics
        for (final TopicWrapper removeTopic : removeTopics) {
            getEntity().removeRelationshipTo(((Topic) removeTopic.unwrap()).getTopicId(), 1);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getIncomingRelationships() {
        return getWrapperFactory().createCollection(getEntity().getIncomingRelatedTopicsArray(), Topic.class, isRevisionEntity());
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TopicWrapper> incomingTopics) {
        if (incomingTopics == null) return;

        final List<TopicWrapper> addTopics = incomingTopics.getAddItems();
        final List<TopicWrapper> removeTopics = incomingTopics.getRemoveItems();
        /*
         * There is no need to do update incoming topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<TopicWrapper> updateTopics = incomingTopics.getUpdateItems();

        // Add Topics
        for (final TopicWrapper addTopic : addTopics) {
            getEntity().addRelationshipFrom(getEntityManager(), (Topic) addTopic.unwrap(), 1);
        }

        // Remove Topics
        for (final TopicWrapper removeTopic : removeTopics) {
            getEntity().removeRelationshipFrom(removeTopic.getTopicId(), 1);
        }
    }

    @Override
    public List<TagWrapper> getTagsInCategories(List<Integer> categoryIds) {
        return getWrapperFactory().createList(getEntity().getTagsInCategoriesByID(categoryIds), isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getTopicToPropertyTags(), TopicToPropertyTag.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties) {
        if (properties == null) return;

        final List<PropertyTagInTopicWrapper> addProperties = properties.getAddItems();
        final List<PropertyTagInTopicWrapper> removeProperties = properties.getRemoveItems();
        /*
         * There is no need to do update properties as when the original entities are altered they will automatically be updated when using
         * database entities.
         */
        //final List<PropertyTagInTopicWrapper> updateProperties = properties.getUpdateItems();

        // Add Properties
        for (final PropertyTagInTopicWrapper addProperty : addProperties) {
            getEntity().addPropertyTag((TopicToPropertyTag) addProperty.unwrap());
        }

        // Remove Properties
        for (final PropertyTagInTopicWrapper removeProperty : removeProperties) {
            getEntity().removePropertyTag((TopicToPropertyTag) removeProperty.unwrap());
        }
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getEntity().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        return getWrapperFactory().createCollection(getEntity().getTopicSourceUrls(), TopicSourceUrl.class, isRevisionEntity());
    }

    @Override
    public void setSourceURLs(CollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        if (sourceURLs == null) return;

        final List<TopicSourceURLWrapper> addSourceUrls = sourceURLs.getAddItems();
        final List<TopicSourceURLWrapper> removeSourceUrls = sourceURLs.getRemoveItems();
        /*
         * There is no need to do update properties as when the original entities are altered they will automatically be updated when using
         * database entities.
         */
        //final List<TopicSourceURLWrapper> updateProperties = sourceURLs.getUpdateItems();

        // Add Source URLs
        for (final TopicSourceURLWrapper addProperty : addSourceUrls) {
            getEntity().addTopicSourceUrl((TopicSourceUrl) addProperty.unwrap());
        }

        // Remove Source URLs
        for (final TopicSourceURLWrapper removeSourceUrl : removeSourceUrls) {
            getEntity().removeTopicSourceUrl(((TopicSourceUrl) removeSourceUrl.unwrap()).getId());
        }
    }

    @Override
    public String getBugzillaBuildId() {
        final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
        return getId() + "-" + getRevision() + " " + formatter.format(
                EnversUtilities.getFixedLastModifiedDate(getEntityManager(), getEntity())) + " " + getLocale();
    }

    @Override
    public String getEditorURL(final ZanataDetails zanataDetails) {
        return getEditorURL();
    }

    @Override
    public String getPressGangURL() {
        final String serverUrl = System.getProperty(CommonConstants.PRESS_GANG_UI_SYSTEM_PROPERTY);
        return (serverUrl.endsWith("/") ? serverUrl : (serverUrl + "/")) + "#SearchResultsAndTopicView;query;topicIds=" + getId();
    }

    @Override
    public String getEditorURL() {
        return getPressGangURL();
    }

    @Override
    public String getInternalURL() {
        return "Topic.seam?topicTopicId=" + getId() + "&selectedTab=Rendered+View";
    }

    @Override
    public String getErrorXRefId() {
        return CommonConstants.ERROR_XREF_ID_PREFIX + getId();
    }

    @Override
    public String getXRefId() {
        return "TopicID" + getId();
    }

    @Override
    public String getXRefPropertyOrId(int propertyId) {
        final PropertyTagInTopicWrapper propertyTag = getProperty(propertyId);
        if (propertyTag != null) {
            return propertyTag.getValue();
        } else {
            return getXRefId();
        }
    }

    @Override
    public String getDescription() {
        return getEntity().getTopicText();
    }

    @Override
    public void setDescription(String description) {
        getEntity().setTopicText(description);
    }

    @Override
    public Date getCreated() {
        return getEntity().getTopicTimeStamp();
    }

    @Override
    public void setCreated(Date created) {
        getEntity().setTopicTimeStamp(created);
    }

    @Override
    public Date getLastModified() {
        return EnversUtilities.getFixedLastModifiedDate(getEntityManager(), getEntity());
    }

    @Override
    public void setLastModified(Date lastModified) {
        getEntity().setLastModifiedDate(lastModified);
    }

    @Override
    public Integer getXmlDoctype() {
        return getEntity().getXmlDoctype();
    }

    @Override
    public void setXmlDoctype(Integer doctypeId) {
        getEntity().setXmlDoctype(doctypeId);
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics() {
        return getWrapperFactory().createCollection(getEntity().getTranslatedTopics(getEntityManager(), getRevision()),
                TranslatedTopic.class, isRevisionEntity());
    }
}
