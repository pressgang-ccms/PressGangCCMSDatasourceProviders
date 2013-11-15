package org.jboss.pressgang.ccms.wrapper;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.RelationshipTag;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopic;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicSourceURLCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBPropertyTagCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBRelatedFromTopicCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBRelatedToTopicCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTagCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTopicSourceUrlCollectionHandler;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTopicWrapper extends DBBaseEntityWrapper<TopicWrapper, Topic> implements TopicWrapper {
    private final static RelationshipTag dummyRelationshipTag = new RelationshipTag();
    static {
        dummyRelationshipTag.setRelationshipTagId(1);
    }

    private final DBTagCollectionHandler tagCollectionHandler;
    private final DBTopicSourceUrlCollectionHandler sourceUrlCollectionHandler;
    private final DBPropertyTagCollectionHandler<TopicToPropertyTag> propertyCollectionHandler;
    private final DBRelatedFromTopicCollectionHandler relatedFromCollectionHandler;
    private final DBRelatedToTopicCollectionHandler relatedToCollectionHandler;

    private final Topic topic;

    public DBTopicWrapper(final DBProviderFactory providerFactory, final Topic topic, boolean isRevision) {
        super(providerFactory, isRevision, Topic.class);
        this.topic = topic;
        tagCollectionHandler = new DBTagCollectionHandler(topic);
        sourceUrlCollectionHandler = new DBTopicSourceUrlCollectionHandler(topic);
        propertyCollectionHandler = new DBPropertyTagCollectionHandler(topic);
        relatedFromCollectionHandler = new DBRelatedFromTopicCollectionHandler(topic);
        relatedToCollectionHandler = new DBRelatedToTopicCollectionHandler(topic);
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
    public boolean hasTag(int tagId) {
        return getEntity().isTaggedWith(tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getEntity().getTags(), Tag.class, isRevisionEntity(), tagCollectionHandler);
    }

    @Override
    public void setTags(final CollectionWrapper<TagWrapper> tags) {
        if (tags == null) return;
        final DBTagCollectionWrapper dbTags = (DBTagCollectionWrapper) tags;
        dbTags.setHandler(tagCollectionHandler);

        // Since tags in a topic are generated from a set and not cached, there is no way to see if this collection is the
        // same as the collection passed. So just process all the tags anyway.

        // Add new tags and skip any existing tags
        final List<Tag> currentTags = getEntity().getTags();
        final Collection<Tag> newTags = dbTags.unwrap();
        for (final Tag tag : newTags) {
            if (currentTags.contains(tag)) {
                currentTags.remove(tag);
                continue;
            } else {
                getEntity().addTag(tag);
            }
        }

        // Remove tags that should no longer exist in the collection
        for (final Tag removeTag : currentTags) {
            getEntity().removeTag(removeTag);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getOutgoingRelationships() {
        final CollectionWrapper<TopicWrapper> collection = getWrapperFactory().createCollection(getEntity().getOutgoingRelatedTopicsArray(),
                Topic.class, isRevisionEntity(), relatedToCollectionHandler);
        return collection;
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TopicWrapper> outgoingTopics) {
        if (outgoingTopics == null) return;
        final DBTopicCollectionWrapper dbOutgoingTopics = (DBTopicCollectionWrapper) outgoingTopics;
        dbOutgoingTopics.setHandler(relatedToCollectionHandler);

        // Since relationships in a topic are generated from a set and not cached, there is no way to see if this collection is the
        // same as the collection passed. So just process all the relationships anyway.

        // Add new relationships and skip any existing relationships
        final List<Topic> currentRelationships = getEntity().getOutgoingRelatedTopicsArray();
        final Collection<Topic> newRelationships = dbOutgoingTopics.unwrap();
        for (final Topic relationship : newRelationships) {
            if (currentRelationships.contains(relationship)) {
                currentRelationships.remove(relationship);
                continue;
            } else {
                getEntity().addRelationshipTo(relationship, dummyRelationshipTag);
            }
        }

        // Remove relationships that should no longer exist in the collection
        for (final Topic removeRelationship : currentRelationships) {
            getEntity().removeRelationshipTo(removeRelationship, dummyRelationshipTag);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getIncomingRelationships() {
        final CollectionWrapper<TopicWrapper> collection = getWrapperFactory().createCollection(getEntity().getIncomingRelatedTopicsArray(),
                Topic.class, isRevisionEntity(), relatedFromCollectionHandler);
        return collection;
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TopicWrapper> incomingTopics) {
        if (incomingTopics == null) return;
        final DBTopicCollectionWrapper dbIncomingTopics = (DBTopicCollectionWrapper) incomingTopics;
        dbIncomingTopics.setHandler(relatedFromCollectionHandler);

        // Since relationships in a topic are generated from a set and not cached, there is no way to see if this collection is the
        // same as the collection passed. So just process all the relationships anyway.

        // Add new relationships and skip any existing relationships
        final List<Topic> currentRelationships = getEntity().getIncomingRelatedTopicsArray();
        final Collection<Topic> newRelationships = dbIncomingTopics.unwrap();
        for (final Topic relationship : newRelationships) {
            if (currentRelationships.contains(relationship)) {
                currentRelationships.remove(relationship);
                continue;
            } else {
                getEntity().addRelationshipFrom(relationship, dummyRelationshipTag);
            }
        }

        // Remove relationships that should no longer exist in the collection
        for (final Topic removeRelationship : currentRelationships) {
            getEntity().removeRelationshipFrom(removeRelationship, dummyRelationshipTag);
        }
    }

    @Override
    public List<TagWrapper> getTagsInCategories(List<Integer> categoryIds) {
        return getWrapperFactory().createList(getEntity().getTagsInCategoriesByID(categoryIds), isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getTopicToPropertyTags(), TopicToPropertyTag.class, isRevisionEntity(), propertyCollectionHandler);
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties) {
        if (properties == null) return;
        final DBTopicToPropertyTagCollectionWrapper dbProperties = (DBTopicToPropertyTagCollectionWrapper) properties;
        dbProperties.setHandler(propertyCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbProperties.unwrap() != getEntity().getPropertyTags()) {
            // Add new property tags and skip any existing tags
            final Set<TopicToPropertyTag> currentProperties = new HashSet<TopicToPropertyTag>(getEntity().getPropertyTags());
            final Collection<TopicToPropertyTag> newProperties = dbProperties.unwrap();
            for (final TopicToPropertyTag property : newProperties) {
                if (currentProperties.contains(property)) {
                    currentProperties.remove(property);
                    continue;
                } else {
                    property.setTopic(getEntity());
                    getEntity().addPropertyTag(property);
                }
            }

            // Remove property tags that should no longer exist in the collection
            for (final TopicToPropertyTag removeProperty : currentProperties) {
                getEntity().removePropertyTag(removeProperty);
            }
        }
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getEntity().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public List<PropertyTagInTopicWrapper> getProperties(int propertyId) {
        return getWrapperFactory().createList(getEntity().getProperties(propertyId), isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        final CollectionWrapper<TopicSourceURLWrapper> collection = getWrapperFactory().createCollection(getEntity().getTopicSourceUrls(),
                TopicSourceUrl.class, isRevisionEntity(), sourceUrlCollectionHandler);
        return (UpdateableCollectionWrapper<TopicSourceURLWrapper>) collection;
    }

    @Override
    public void setSourceURLs(UpdateableCollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        if (sourceURLs == null) return;
        final DBTopicSourceURLCollectionWrapper dbSourceUrls = (DBTopicSourceURLCollectionWrapper) sourceURLs;
        dbSourceUrls.setHandler(sourceUrlCollectionHandler);

        // Since source urls in a topic are generated from a set and not cached, there is no way to see if this collection is the
        // same as the collection passed. So just process all the urls anyway.

        // Add new source urls and skip any existing urls
        final Set<TopicSourceUrl> currentSourceUrls = new HashSet<TopicSourceUrl>(getEntity().getTopicSourceUrls());
        final Collection<TopicSourceUrl> newSourceUrls = dbSourceUrls.unwrap();
        for (final TopicSourceUrl sourceUrl : newSourceUrls) {
            if (currentSourceUrls.contains(sourceUrl)) {
                currentSourceUrls.remove(sourceUrl);
                continue;
            } else {
                getEntity().addTopicSourceUrl(sourceUrl);
            }
        }

        // Remove source urls that should no longer exist in the collection
        for (final TopicSourceUrl removeSourceUrl : currentSourceUrls) {
            getEntity().removeTopicSourceUrl(removeSourceUrl);
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