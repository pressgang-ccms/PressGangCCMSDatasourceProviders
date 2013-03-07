package org.jboss.pressgang.ccms.contentspec.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopic;
import org.jboss.pressgang.ccms.model.exceptions.CustomConstraintViolationException;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTopicWrapper extends DBBaseWrapper<TopicWrapper> implements TopicWrapper {

    private final Topic topic;

    public DBTopicWrapper(final DBProviderFactory providerFactory, final Topic topic, boolean isRevision) {
        super(providerFactory, isRevision);
        this.topic = topic;
    }

    protected Topic getTopic() {
        return topic;
    }

    @Override
    public Integer getId() {
        return getTopic().getId();
    }

    @Override
    public void setId(Integer id) {
        getTopic().setTopicId(id);
    }

    @Override
    public Topic unwrap() {
        return topic;
    }

    @Override
    public Integer getRevision() {
        return getTopic().getRevision() == null ? EnversUtilities.getLatestRevision(getDatabaseProvider().getEntityManager(),
                getTopic()).intValue() : getTopic().getRevision().intValue();
    }

    @Override
    public CollectionWrapper<TopicWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getTopic()), Topic.class, true);
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
        return getTopic().getTopicTitle();
    }

    @Override
    public void setTitle(String title) {
        getTopic().setTopicTitle(title);
    }

    @Override
    public String getXml() {
        return getTopic().getTopicXML();
    }

    @Override
    public void setXml(String xml) {
        getTopic().setTopicXML(xml);
    }

    @Override
    public String getLocale() {
        return getTopic().getTopicLocale();
    }

    @Override
    public void setLocale(String locale) {
        getTopic().setTopicLocale(locale);
    }

    @Override
    public String getHtml() {
        return getTopic().getTopicRendered();
    }

    @Override
    public void setHtml(String html) {
        getTopic().setTopicRendered(html);
    }

    @Override
    public boolean hasTag(int tagId) {
        return getTopic().isTaggedWith(tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getTopic().getTags(), Tag.class, isRevisionEntity());
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

        // Add Tags
        for (final TagWrapper addTag : addTags) {
            try {
                getTopic().addTag((Tag) addTag.unwrap());
            } catch (CustomConstraintViolationException e) {
                // TODO
            }
        }

        // Remove Tags
        for (final TagWrapper removeTag : removeTags) {
            getTopic().removeTag((Tag) removeTag.unwrap());
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getOutgoingRelationships() {
        return getWrapperFactory().createCollection(getTopic().getOutgoingRelatedTopicsArray(), Topic.class, isRevisionEntity());
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
            getTopic().addRelationshipTo(getEntityManager(), (Topic) addTopic.unwrap(), 1);
        }

        // Remove Topics
        for (final TopicWrapper removeTopic : removeTopics) {
            getTopic().removeRelationshipTo(((Topic) removeTopic.unwrap()).getTopicId(), 1);
        }
    }

    @Override
    public CollectionWrapper<TopicWrapper> getIncomingRelationships() {
        return getWrapperFactory().createCollection(getTopic().getIncomingRelatedTopicsArray(), Topic.class, isRevisionEntity());
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
            getTopic().addRelationshipFrom(getEntityManager(), (Topic) addTopic.unwrap(), 1);
        }

        // Remove Topics
        for (final TopicWrapper removeTopic : removeTopics) {
            getTopic().removeRelationshipFrom(removeTopic.getTopicId(), 1);
        }
    }

    @Override
    public List<TagWrapper> getTagsInCategories(List<Integer> categoryIds) {
        return getWrapperFactory().createList(getTopic().getTagsInCategoriesByID(categoryIds), isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                getTopic().getTopicToPropertyTags(), TopicToPropertyTag.class, isRevisionEntity());
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
            getTopic().addPropertyTag((TopicToPropertyTag) addProperty.unwrap());
        }

        // Remove Properties
        for (final PropertyTagInTopicWrapper removeProperty : removeProperties) {
            getTopic().removePropertyTag((TopicToPropertyTag) removeProperty.unwrap());
        }
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getTopic().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        return getWrapperFactory().createCollection(getTopic().getTopicSourceUrls(), TopicSourceUrl.class, isRevisionEntity());
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
            getTopic().addTopicSourceUrl((TopicSourceUrl) addProperty.unwrap());
        }

        // Remove Source URLs
        for (final TopicSourceURLWrapper removeSourceUrl : removeSourceUrls) {
            getTopic().removeTopicSourceUrl(((TopicSourceUrl) removeSourceUrl.unwrap()).getId());
        }
    }

    @Override
    public String getBugzillaBuildId() {
        final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
        return getId() + "-" + getRevision() + " " + formatter.format(
                EnversUtilities.getFixedLastModifiedDate(getEntityManager(), getTopic())) + " " + getLocale();
    }

    @Override
    public String getEditorURL(final ZanataDetails zanataDetails) {
        return getEditorURL();
    }

    @Override
    public String getPressGangURL() {
        return CommonConstants.SERVER_URL + "/TopicIndex/Topic.seam?topicTopicId=" + getId() + (getRevision() != null ? ("&amp;" +
                "topicRevision=" + getRevision()) : "");
    }

    @Override
    public String getEditorURL() {
        return CommonConstants.SERVER_URL + "/TopicIndex/TopicEdit.seam?topicTopicId=" + getId();
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
        return getTopic().getTopicText();
    }

    @Override
    public void setDescription(String description) {
        getTopic().setTopicText(description);
    }

    @Override
    public Date getCreated() {
        return getTopic().getTopicTimeStamp();
    }

    @Override
    public void setCreated(Date created) {
        getTopic().setTopicTimeStamp(created);
    }

    @Override
    public Date getLastModified() {
        return EnversUtilities.getFixedLastModifiedDate(getEntityManager(), getTopic());
    }

    @Override
    public void setLastModified(Date lastModified) {
        getTopic().setLastModifiedDate(lastModified);
    }

    @Override
    public Integer getXmlDoctype() {
        return getTopic().getXmlDoctype();
    }

    @Override
    public void setXmlDoctype(Integer doctypeId) {
        getTopic().setXmlDoctype(doctypeId);
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getTranslatedTopics() {
        return getWrapperFactory().createCollection(getTopic().getTranslatedTopics(getEntityManager(), getRevision()),
                TranslatedTopic.class, isRevisionEntity());
    }
}
