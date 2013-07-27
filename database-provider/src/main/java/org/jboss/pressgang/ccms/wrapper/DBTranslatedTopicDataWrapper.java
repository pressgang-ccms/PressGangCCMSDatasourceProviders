package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.contentspec.utils.EntityUtilities;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopic;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.utils.constants.CommonConstants;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicSourceURLCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedTopicDataCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedTopicStringCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.CollectionEventListener;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;
import org.jboss.pressgang.ccms.zanata.ZanataDetails;

public class DBTranslatedTopicDataWrapper extends DBBaseWrapper<TranslatedTopicWrapper,
        TranslatedTopicData> implements TranslatedTopicWrapper {
    private final TagCollectionEventListener tagCollectionEventListener = new TagCollectionEventListener();
    private final SourceUrlCollectionEventListener sourceUrlCollectionEventListener = new SourceUrlCollectionEventListener();
    private final PropertyCollectionEventListener propertyCollectionEventListener = new PropertyCollectionEventListener();
    private final RelatedFromTopicCollectionEventListener relatedFromTopicCollectionEventListener = new
            RelatedFromTopicCollectionEventListener();
    private final RelatedToTopicCollectionEventListener relatedToTopicCollectionEventListener = new RelatedToTopicCollectionEventListener();
    private final TranslatedTopicStringCollectionEventListener translatedTopicStringCollectionEventListener = new
            TranslatedTopicStringCollectionEventListener();

    private final TranslatedTopicData translatedTopicData;

    public DBTranslatedTopicDataWrapper(final DBProviderFactory providerFactory, final TranslatedTopicData translatedTopicData,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedTopicData.class);
        this.translatedTopicData = translatedTopicData;
    }

    protected TranslatedTopic getTranslatedTopic() {
        if (getEntity().getTranslatedTopic() == null) {
            getEntity().setTranslatedTopic(new TranslatedTopic());
        }
        return getEntity().getTranslatedTopic();
    }

    @Override
    protected TranslatedTopicData getEntity() {
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
        getEntity().setTranslatedTopicDataId(id);
    }

    @Override
    public TranslatedTopicData unwrap() {
        return getEntity();
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
        return getEntity().getTranslatedXml();
    }

    @Override
    public void setXml(String xml) {
        getEntity().setTranslatedXml(xml);
    }

    @Override
    public String getLocale() {
        return getEntity().getTranslationLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().setTranslationLocale(locale);
    }

    @Override
    public boolean hasTag(int tagId) {
        return getEnversTopic().isTaggedWith(tagId);
    }

    @Override
    public CollectionWrapper<TagWrapper> getTags() {
        final CollectionWrapper<TagWrapper> collection = getWrapperFactory().createCollection(getEnversTopic().getTags(), Tag.class,
                isRevisionEntity());
        final DBTagCollectionWrapper dbCollection = (DBTagCollectionWrapper) collection;
        dbCollection.registerEventListener(tagCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setTags(CollectionWrapper<TagWrapper> tags) {
        if (tags == null) return;
        final DBTagCollectionWrapper dbTags = (DBTagCollectionWrapper) tags;
        dbTags.registerEventListener(tagCollectionEventListener);

        // Remove Tags
        final List<Tag> currentTags = getEnversTopic().getTags();
        for (final Tag removeTag : currentTags) {
            getEnversTopic().removeTag(removeTag);
        }

        // Add Tags
        final Collection<Tag> newTags = dbTags.unwrap();
        for (final Tag addTag : newTags) {
            getEnversTopic().addTag(addTag);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getOutgoingRelationships() {
        final CollectionWrapper<TranslatedTopicWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getOutgoingRelatedTranslatedTopicData(getEntityManager()), TranslatedTopicData.class, isRevisionEntity());
        final DBTranslatedTopicDataCollectionWrapper dbCollection = (DBTranslatedTopicDataCollectionWrapper) collection;
        dbCollection.registerEventListener(relatedToTopicCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setOutgoingRelationships(CollectionWrapper<TranslatedTopicWrapper> outgoingTopics) {
        if (outgoingTopics == null) return;
        final DBTranslatedTopicDataCollectionWrapper dbOutgoingTopics = (DBTranslatedTopicDataCollectionWrapper) outgoingTopics;
        dbOutgoingTopics.registerEventListener(relatedToTopicCollectionEventListener);

        // Remove Topics
        final List<Topic> currentTopics = getEnversTopic().getOutgoingRelatedTopicsArray();
        for (final Topic removeTopic : currentTopics) {
            getEnversTopic().removeRelationshipTo(removeTopic.getTopicId(), 1);
        }

        // Add Topics
        final Collection<TranslatedTopicData> newTopics = dbOutgoingTopics.unwrap();
        for (final TranslatedTopicData addTopic : newTopics) {
            getEnversTopic().addRelationshipTo(getEntityManager(), addTopic.getTranslatedTopic().getTopicId(), 1);
        }
    }

    @Override
    public CollectionWrapper<TranslatedTopicWrapper> getIncomingRelationships() {
        final CollectionWrapper<TranslatedTopicWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getIncomingRelatedTranslatedTopicData(getEntityManager()), TranslatedTopicData.class, isRevisionEntity());
        final DBTranslatedTopicDataCollectionWrapper dbCollection = (DBTranslatedTopicDataCollectionWrapper) collection;
        dbCollection.registerEventListener(relatedFromTopicCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setIncomingRelationships(CollectionWrapper<TranslatedTopicWrapper> incomingTopics) {
        if (incomingTopics == null) return;
        final DBTranslatedTopicDataCollectionWrapper dbIncomingTopics = (DBTranslatedTopicDataCollectionWrapper) incomingTopics;
        dbIncomingTopics.registerEventListener(relatedFromTopicCollectionEventListener);

        // Remove Topics
        final List<Topic> currentTopics = getEnversTopic().getOutgoingRelatedTopicsArray();
        for (final Topic removeTopic : currentTopics) {
            getEnversTopic().removeRelationshipFrom(removeTopic.getTopicId(), 1);
        }

        // Add Topics
        final Collection<TranslatedTopicData> newTopics = dbIncomingTopics.unwrap();
        for (final TranslatedTopicData addTopic : newTopics) {
            getEnversTopic().addRelationshipFrom(getEntityManager(), addTopic.getTranslatedTopic().getTopicId(), 1);
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
        final DBTopicToPropertyTagCollectionWrapper dbCollection = (DBTopicToPropertyTagCollectionWrapper) collection;
        dbCollection.registerEventListener(propertyCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setProperties(UpdateableCollectionWrapper<PropertyTagInTopicWrapper> properties) {
        if (properties == null) return;
        final DBTopicToPropertyTagCollectionWrapper dbProperties = (DBTopicToPropertyTagCollectionWrapper) properties;
        dbProperties.registerEventListener(propertyCollectionEventListener);

        // Remove the current properties
        final Set<TopicToPropertyTag> propertyTags = new HashSet<TopicToPropertyTag>(getEnversTopic().getTopicToPropertyTags());
        for (final TopicToPropertyTag propertyTag : propertyTags) {
            getEnversTopic().removePropertyTag(propertyTag);
        }

        // Set the new properties
        final Collection<TopicToPropertyTag> newPropertyTags = dbProperties.unwrap();
        for (final TopicToPropertyTag propertyTag : newPropertyTags) {
            getEnversTopic().addPropertyTag(propertyTag);
        }
    }

    @Override
    public PropertyTagInTopicWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getEnversTopic().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TopicSourceURLWrapper> getSourceURLs() {
        final CollectionWrapper<TopicSourceURLWrapper> collection = getWrapperFactory().createCollection(
                getEnversTopic().getTopicSourceUrls(), TopicSourceUrl.class, isRevisionEntity());
        final DBTopicSourceURLCollectionWrapper dbCollection = (DBTopicSourceURLCollectionWrapper) collection;
        dbCollection.registerEventListener(sourceUrlCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setSourceURLs(CollectionWrapper<TopicSourceURLWrapper> sourceURLs) {
        if (sourceURLs == null) return;
        final DBTopicSourceURLCollectionWrapper dbProperties = (DBTopicSourceURLCollectionWrapper) sourceURLs;
        dbProperties.registerEventListener(sourceUrlCollectionEventListener);

        // Remove the current properties
        final List<TopicSourceUrl> currentSourceUrls = getEnversTopic().getTopicSourceUrls();
        for (final TopicSourceUrl sourceUrl : currentSourceUrls) {
            getEnversTopic().removeTopicSourceUrl(sourceUrl.getId());
        }

        // Set the new properties
        final Collection<TopicSourceUrl> newSourceUrls = dbProperties.unwrap();
        for (final TopicSourceUrl sourceUrl : newSourceUrls) {
            getEnversTopic().addTopicSourceUrl(sourceUrl);
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
        return "TranslatedTopicID" + getEntity().getId();
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
        return getEntity().containsFuzzyTranslation();
    }

    @Override
    public Integer getTranslationPercentage() {
        return getEntity().getTranslationPercentage();
    }

    @Override
    public void setTranslationPercentage(Integer percentage) {
        getEntity().setTranslationPercentage(percentage);
    }

    @Override
    public String getTranslatedXMLCondition() {
        return getEntity().getTranslatedXMLCondition();
    }

    @Override
    public void setTranslatedXMLCondition(String translatedXMLCondition) {
        getEntity().setTranslatedXMLCondition(translatedXMLCondition);
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedTopicStringWrapper> getTranslatedTopicStrings() {
        final CollectionWrapper<TranslatedTopicStringWrapper> collection = getWrapperFactory().createCollection(
                getEntity().getTranslatedTopicDataStringsArray(), TranslatedTopicString.class, isRevisionEntity());
        final DBTranslatedTopicStringCollectionWrapper dbCollection = (DBTranslatedTopicStringCollectionWrapper) collection;
        dbCollection.registerEventListener(translatedTopicStringCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setTranslatedTopicStrings(UpdateableCollectionWrapper<TranslatedTopicStringWrapper> translatedStrings) {
        if (translatedStrings == null) return;
        final DBTranslatedTopicStringCollectionWrapper dbTranslatedStrings = (DBTranslatedTopicStringCollectionWrapper) translatedStrings;
        dbTranslatedStrings.registerEventListener(translatedTopicStringCollectionEventListener);

        // Remove the current translated strings
        final Set<TranslatedTopicString> currentTranslatedStrings = getEntity().getTranslatedTopicStrings();
        for (final TranslatedTopicString translatedString : currentTranslatedStrings) {
            getEntity().removeTranslatedTopicString(translatedString);
        }

        // Set the new translated strings
        final Collection<TranslatedTopicString> newTranslatedStrings = dbTranslatedStrings.unwrap();
        for (final TranslatedTopicString translatedString : newTranslatedStrings) {
            getEntity().addTranslatedTopicString(translatedString);
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

    @Override
    public TranslatedCSNodeWrapper getTranslatedCSNode() {
        return getWrapperFactory().create(getEntity().getTranslatedCSNode(), isRevisionEntity(), TranslatedCSNodeWrapper.class);
    }

    @Override
    public void setTranslatedCSNode(TranslatedCSNodeWrapper translatedCSNode) {
        getEntity().setTranslatedCSNode(translatedCSNode == null ? null : (TranslatedCSNode) translatedCSNode.unwrap());
    }

    /**
     *
     */
    private class TranslatedTopicStringCollectionEventListener implements UpdateableCollectionEventListener<TranslatedTopicString> {
        @Override
        public void onAddItem(final TranslatedTopicString entity) {
            getEntity().addTranslatedTopicString(entity);
        }

        @Override
        public void onRemoveItem(final TranslatedTopicString entity) {
            getEntity().addTranslatedTopicString(entity);
        }

        @Override
        public void onUpdateItem(final TranslatedTopicString entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof TranslatedTopicStringCollectionEventListener;
        }
    }

    /**
     *
     */
    private class RelatedToTopicCollectionEventListener implements CollectionEventListener<TranslatedTopicData> {

        @Override
        public void onAddItem(TranslatedTopicData entity) {
            getEnversTopic().addRelationshipTo(getEntityManager(), entity.getTranslatedTopic().getTopicId(), 1);
        }

        @Override
        public void onRemoveItem(TranslatedTopicData entity) {
            getEnversTopic().removeRelationshipTo(entity.getTranslatedTopic().getTopicId(), 1);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof RelatedToTopicCollectionEventListener;
        }
    }

    /**
     *
     */
    private class RelatedFromTopicCollectionEventListener implements CollectionEventListener<TranslatedTopicData> {

        @Override
        public void onAddItem(TranslatedTopicData entity) {
            getEnversTopic().addRelationshipFrom(getEntityManager(), entity.getTranslatedTopic().getTopicId(), 1);
        }

        @Override
        public void onRemoveItem(TranslatedTopicData entity) {
            getEnversTopic().removeRelationshipFrom(entity.getTranslatedTopic().getTopicId(), 1);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof RelatedFromTopicCollectionEventListener;
        }
    }

    /**
     *
     */
    private class TagCollectionEventListener implements CollectionEventListener<Tag> {

        @Override
        public void onAddItem(Tag entity) {
            getEnversTopic().addTag(entity);
        }

        @Override
        public void onRemoveItem(Tag entity) {
            getEnversTopic().removeTag(entity);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof TagCollectionEventListener;
        }
    }

    /**
     *
     */
    private class PropertyCollectionEventListener implements UpdateableCollectionEventListener<TopicToPropertyTag> {
        @Override
        public void onAddItem(final TopicToPropertyTag entity) {
            getEnversTopic().addPropertyTag(entity);
        }

        @Override
        public void onRemoveItem(final TopicToPropertyTag entity) {
            getEnversTopic().removePropertyTag(entity);
        }

        @Override
        public void onUpdateItem(final TopicToPropertyTag entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof PropertyCollectionEventListener;
        }
    }

    /**
     *
     */
    private class SourceUrlCollectionEventListener implements UpdateableCollectionEventListener<TopicSourceUrl> {
        @Override
        public void onAddItem(final TopicSourceUrl entity) {
            getEnversTopic().addTopicSourceUrl(entity);
        }

        @Override
        public void onRemoveItem(final TopicSourceUrl entity) {
            getEnversTopic().removeTopicSourceUrl(entity.getId());
        }

        @Override
        public void onUpdateItem(final TopicSourceUrl entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SourceUrlCollectionEventListener;
        }
    }
}
