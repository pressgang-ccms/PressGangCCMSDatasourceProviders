package org.jboss.pressgang.ccms.wrapper;

import java.util.Date;
import java.util.List;

import org.jboss.pressgang.ccms.contentspec.utils.EntityUtilities;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopic;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTranslatedTopicDataWrapper extends DBBaseWrapper<TranslatedTopicWrapper> implements TranslatedTopicWrapper {

    private final TranslatedTopicData translatedTopicData;

    public DBTranslatedTopicDataWrapper(final DBProviderFactory providerFactory, final TranslatedTopicData translatedTopicData,
            boolean isRevision) {
        super(providerFactory, isRevision);
        this.translatedTopicData = translatedTopicData;
    }

    protected TranslatedTopic getTranslatedTopic() {
        if (getTranslatedTopicData().getTranslatedTopic() == null) {
            getTranslatedTopicData().setTranslatedTopic(new TranslatedTopic());
        }
        return getTranslatedTopicData().getTranslatedTopic();
    }

    protected TranslatedTopicData getTranslatedTopicData() {
        return translatedTopicData;
    }

    protected Topic getEnversTopic() {
        return getTranslatedTopic().getEnversTopic(getDatabaseProvider().getEntityManager());
    }

    @Override
    public Integer getId() {
        return getTranslatedTopic().getTopicId();
    }

    @Override
    public void setId(Integer id) {
        getTranslatedTopicData().setTranslatedTopicDataId(id);
    }

    @Override
    public TranslatedTopicData unwrap() {
        return getTranslatedTopicData();
    }

    @Override
    public Integer getRevision() {
        return getTranslatedTopic().getTopicRevision();
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getTranslatedTopicData()),
                TranslatedTopicData.class, true);
    }

    @Override
    public Integer getTopicId() {
        return getTranslatedTopic().getTopicId();
    }

    @Override
    public Integer getTopicRevision() {
        return getTranslatedTopic().getTopicRevision();
    }

    @Override
    public String getTitle() {
        return getEnversTopic().getTopicTitle();
    }

    @Override
    public void setTitle(String title) {
        getEnversTopic().setTopicTitle(title);
    }

    @Override
    public String getXml() {
        return getTranslatedTopicData().getTranslatedXml();
    }

    @Override
    public void setXml(String xml) {
        getTranslatedTopicData().setTranslatedXml(xml);
    }

    @Override
    public String getLocale() {
        return getTranslatedTopicData().getTranslationLocale();
    }

    @Override
    public void setLocale(String locale) {
        getTranslatedTopicData().setTranslationLocale(locale);
    }

    @Override
    public String getHtml() {
        return getTranslatedTopicData().getTranslatedXmlRendered();
    }

    @Override
    public void setHtml(String html) {
        getTranslatedTopicData().setTranslatedXmlRendered(html);
    }

    @Override
    public boolean hasTag(int tagId) {
        return getEnversTopic().isTaggedWith(tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        return getWrapperFactory().createCollection(getEnversTopic().getTags(), Tag.class, isRevisionEntity());
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
            getEnversTopic().removeTag((Tag) removeTag.unwrap());
        }

        // Add Tags
        for (final TagWrapper addTag : addTags) {
            getEnversTopic().addTag((Tag) addTag.unwrap());
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getOutgoingRelationships() {
        return getWrapperFactory().createCollection(getTranslatedTopicData().getOutgoingRelatedTranslatedTopicData(getEntityManager()),
                TranslatedTopicData.class, isRevisionEntity());
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TranslatedTopicWrapper> outgoingTopics) {
        if (outgoingTopics == null) return;

        final List<TranslatedTopicWrapper> addTopics = outgoingTopics.getAddItems();
        final List<TranslatedTopicWrapper> removeTopics = outgoingTopics.getRemoveItems();
        /*
         * There is no need to do update outgoing topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<TranslatedTopicWrapper> updateTopics = outgoingTopics.getUpdateItems();

        // Add Topics
        for (final TranslatedTopicWrapper addTopic : addTopics) {
            getEnversTopic().addRelationshipTo(getEntityManager(), addTopic.getTopicId(), 1);
        }

        // Remove Topics
        for (final TranslatedTopicWrapper removeTopic : removeTopics) {
            getEnversTopic().removeRelationshipTo(removeTopic.getTopicId(), 1);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getIncomingRelationships() {
        return getWrapperFactory().createCollection(getTranslatedTopicData().getIncomingRelatedTranslatedTopicData(getEntityManager()),
                TranslatedTopicData.class, isRevisionEntity());
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TranslatedTopicWrapper> incomingTopics) {
        if (incomingTopics == null) return;

        final List<TranslatedTopicWrapper> addTopics = incomingTopics.getAddItems();
        final List<TranslatedTopicWrapper> removeTopics = incomingTopics.getRemoveItems();
        /*
         * There is no need to do update incoming topics as when the original entities are altered they will automatically be updated when
         * using database entities.
         */
        //final List<TranslatedTopicWrapper> updateTopics = incomingTopics.getUpdateItems();

        // Add Topics
        for (final TranslatedTopicWrapper addTopic : addTopics) {
            getEnversTopic().addRelationshipFrom(getEntityManager(), addTopic.getTopicId(), 1);
        }

        // Remove Topics
        for (final TranslatedTopicWrapper removeTopic : removeTopics) {
            getEnversTopic().removeRelationshipFrom(removeTopic.getTopicId(), 1);
        }
    }

    @Override
    public List<TagWrapper> getTagsInCategories(List<Integer> categoryIds) {
        return getWrapperFactory().createList(getEnversTopic().getTagsInCategoriesByID(categoryIds), isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> getProperties() {
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                getEnversTopic().getTopicToPropertyTags(), TopicToPropertyTag.class, isRevisionEntity());
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
            getEnversTopic().addPropertyTag((TopicToPropertyTag) addProperty.unwrap());
        }

        // Remove Properties
        for (final PropertyTagInTopicWrapper removeProperty : removeProperties) {
            getEnversTopic().removePropertyTag((TopicToPropertyTag) removeProperty.unwrap());
        }
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getEnversTopic().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        return getWrapperFactory().createCollection(getEnversTopic().getTopicSourceUrls(), TopicSourceUrl.class, isRevisionEntity());
    }

    @Override
    public void setSourceURLs(CollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        getEnversTopic().getTopicToTopicSourceUrls().clear();
        for (final TopicSourceURLWrapper topicSourceUrl : sourceURLs.getItems()) {
            getEnversTopic().addTopicSourceUrl((TopicSourceUrl) topicSourceUrl.unwrap());
        }
    }

    @Override
    public String getBugzillaBuildId() {
        return "Translation " + getTranslatedTopic().getZanataId() + " " + getLocale();
    }

    @Override
    public String getEditorURL(ZanataDetails zanataDetails) {
        final String zanataServerUrl = zanataDetails == null ? null : zanataDetails.getServer();
        final String zanataProject = zanataDetails == null ? null : zanataDetails.getProject();
        final String zanataVersion = zanataDetails == null ? null : zanataDetails.getVersion();

        if (zanataServerUrl != null && !zanataServerUrl.isEmpty() && zanataProject != null && !zanataProject.isEmpty() && zanataVersion
                != null && !zanataVersion.isEmpty()) {
            final String zanataId = getTranslatedTopic().getZanataId();

            return zanataServerUrl + "webtrans/Application.html?project=" + zanataProject + "&amp;iteration=" + zanataVersion + "&amp;" +
                    "doc=" + zanataId + "&amp;localeId=" + getLocale() + "#view:doc;doc:" + zanataId;
        } else {
            return null;
        }
    }

    @Override
    public String getPressGangURL() {
        /*
         * If the topic isn't a dummy then link to the translated counterpart. If the topic is a dummy URL and the locale doesn't match
         * the historical topic's
         * locale then it means that the topic has been pushed to zanata so link to the original pushed translation. If neither of these
         * rules apply then link
         * to the standard topic.
         */
        if (EntityUtilities.isDummyTopic(this) || EntityUtilities.hasBeenPushedForTranslation(this)) {
            final String serverUrl = System.getProperty(CommonConstants.PRESS_GANG_UI_SYSTEM_PROPERTY);
            return (serverUrl.endsWith(
                    "/") ? serverUrl : (serverUrl + "/")) + "#TranslatedTopicResultsAndTranslatedTopicView;query;zanataIds=" +
                    getZanataId();
        } else {
            final String serverUrl = System.getProperty(CommonConstants.PRESS_GANG_UI_SYSTEM_PROPERTY);
            return (serverUrl.endsWith("/") ? serverUrl : (serverUrl + "/")) + "#SearchResultsAndTopicView;query;topicIds=" + getTopicId();
        }
    }

    @Override
    public String getInternalURL() {
        return "TranslatedTopic.seam?translatedTopicId=" + getTranslatedTopic().getId() + "&locale=" + getLocale() +
                "&selectedTab=Rendered+View";
    }

    @Override
    public String getErrorXRefId() {
        return CommonConstants.ERROR_XREF_ID_PREFIX + getTranslatedTopic().getZanataId();
    }

    @Override
    public String getXRefId() {
        return "TranslatedTopicID" + getTranslatedTopicData().getId();
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
    public void setTopicId(Integer id) {
        getTranslatedTopic().setTopicId(id);
    }

    @Override
    public void setTopicRevision(Integer revision) {
        getTranslatedTopic().setTopicRevision(revision);
    }

    @Override
    public Integer getTranslatedTopicId() {
        return getTranslatedTopic().getTranslatedTopicId();
    }

    @Override
    public void setTranslatedTopicId(Integer translatedTopicId) {
        getTranslatedTopic().setTranslatedTopicId(translatedTopicId);
    }

    @Override
    public String getZanataId() {
        return getTranslatedTopic().getZanataId();
    }

    @Override
    public boolean getContainsFuzzyTranslations() {
        return getTranslatedTopicData().containsFuzzyTranslation();
    }

    @Override
    public Integer getTranslationPercentage() {
        return getTranslatedTopicData().getTranslationPercentage();
    }

    @Override
    public void setTranslationPercentage(Integer percentage) {
        getTranslatedTopicData().setTranslationPercentage(percentage);
    }

    @Override
    public Date getHtmlUpdated() {
        return getTranslatedTopicData().getTranslatedXmlRenderedUpdated();
    }

    @Override
    public void setHtmlUpdated(Date htmlUpdated) {
        getTranslatedTopicData().setTranslatedXmlRenderedUpdated(htmlUpdated);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings() {
        final CollectionWrapper<TranslatedTopicStringWrapper> collection = getWrapperFactory().createCollection(
                getTranslatedTopicData().getTranslatedTopicDataStringsArray(), TranslatedTopicString.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<TranslatedTopicStringWrapper>) collection;
    }

    @Override
    public void setTranslatedTopicStrings(UpdateableCollectionWrapper<TranslatedTopicStringWrapper> translatedStrings) {
        if (translatedStrings == null) return;

        final List<TranslatedTopicStringWrapper> addTranslatedStrings = translatedStrings.getAddItems();
        final List<TranslatedTopicStringWrapper> removeTranslatedStrings = translatedStrings.getRemoveItems();
        /*
         * There is no need to do update properties as when the original entities are altered they will automatically be updated when using
         * database entities.
         */
        //final List<TranslatedTopicStringWrapper> updateTranslatedStrings = translatedStrings.getUpdateItems();

        // Add Translated Strings
        for (final TranslatedTopicStringWrapper addTranslatedString : addTranslatedStrings) {
            getTranslatedTopicData().addTranslatedTopicString((TranslatedTopicString) addTranslatedString.unwrap());
        }

        // Remove Translated Strings
        for (final TranslatedTopicStringWrapper removeTranslatedString : removeTranslatedStrings) {
            getTranslatedTopicData().removeTranslatedTopicString((TranslatedTopicString) removeTranslatedString.unwrap());
        }
    }

    @Override
    public TopicWrapper getTopic() {
        return getWrapperFactory().create(getEnversTopic(), true);
    }

    @Override
    public void setTopic(TopicWrapper topic) {
        getTranslatedTopic().setEnversTopic(topic == null ? null : (Topic) topic.unwrap());
    }
}
