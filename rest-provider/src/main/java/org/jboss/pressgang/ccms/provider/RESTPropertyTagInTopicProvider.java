package org.jboss.pressgang.ccms.provider;

import java.io.IOException;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInTopicProvider extends RESTPropertyTagProvider implements PropertyTagInTopicProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInTopicProvider.class);

    protected RESTPropertyTagInTopicProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    @Override
    public CollectionWrapper<PropertyTagInTopicWrapper> getPropertyTagInTopicRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get PropertyTagInTopic revisions using V1 of the REST Interface.");
    }

    /**
     * Get the REST Property Tag Revision Collection for a specific Property Tag defined by ID and Revision.
     *
     * @param id
     * @param revision
     * @param topic
     * @return
     */
    public RESTAssignedPropertyTagCollectionV1 getRESTPropertyTagInTopicRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        try {
            final RESTAssignedPropertyTagCollectionV1 propertyTagRevisions;
            if (topic instanceof RESTTranslatedTopicV1) {
                propertyTagRevisions = getRESTTranslatedTopicPropertyRevisions(id, revision, topic);
            } else {
                propertyTagRevisions = getRESTTopicPropertyRevisions(id, revision, topic);
            }

            return propertyTagRevisions;
        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInTopic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }

        return null;
    }

    /**
     * Get the Wrapped Property Tag Revision Collection for a specific Property Tag defined by ID and Revision.
     *
     * @param id
     * @param revision
     * @param topic
     * @return
     */
    public CollectionWrapper<PropertyTagInTopicWrapper> getPropertyTagInTopicRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        return getWrapperFactory().createCollection(getRESTPropertyTagInTopicRevisions(id, revision, topic),
                RESTAssignedPropertyTagV1.class, true, topic);
    }

    @SuppressWarnings("unchecked")
    protected RESTAssignedPropertyTagCollectionV1 getRESTTopicPropertyRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) throws IOException {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTopicV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        RESTTopicV1 topic = null;
        // Check the cache first
        if (getRESTEntityCache().containsKeyValue(RESTTopicV1.class, tagId, tagRevision)) {
            topic = getRESTEntityCache().get(RESTTopicV1.class, tagId, tagRevision);
        }

        // We need to expand the all the property tag revisions in the topic
        final String expandString = getExpansionString(RESTTopicV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

        // Load the topic from the REST Interface
        final RESTTopicV1 tempTopic;
        if (tagRevision == null) {
            tempTopic = getRESTClient().getJSONTopic(tagId, expandString);
        } else {
            tempTopic = getRESTClient().getJSONTopicRevision(tagId, tagRevision, expandString);
        }

        if (topic == null) {
            topic = tempTopic;
            getRESTEntityCache().add(topic, tagRevision);
        } else if (topic.getProperties() == null) {
            topic.setProperties(tempTopic.getProperties());
        } else {
            // Iterate over the current and old properties and add any missing objects.
            final List<RESTAssignedPropertyTagV1> properties = topic.getProperties().returnItems();
            final List<RESTAssignedPropertyTagV1> newProperties = tempTopic.getProperties().returnItems();
            for (final RESTAssignedPropertyTagV1 newProperty : newProperties) {
                boolean found = false;

                for (final RESTAssignedPropertyTagV1 property : properties) {
                    if (property.getId().equals(newProperty.getId())) {
                        property.setRevisions(newProperty.getRevisions());

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    topic.getProperties().addItem(newProperty);
                }
            }
        }

        // Find the matching properties and return it's revisions
        for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : topic.getProperties().getItems()) {
            final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

            if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                return propertyTag.getRevisions();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    protected RESTAssignedPropertyTagCollectionV1 getRESTTranslatedTopicPropertyRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) throws IOException {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        RESTTranslatedTopicV1 translatedTopic = null;
        // Check the cache first
        if (getRESTEntityCache().containsKeyValue(RESTTranslatedTopicV1.class, topicId, topicRevision)) {
            translatedTopic = getRESTEntityCache().get(RESTTranslatedTopicV1.class, topicId, topicRevision);
        }

        // We need to expand the all the property tag revisions in the translated topic
        final String expandString = getExpansionString(RESTTranslatedTopicV1.PROPERTIES_NAME, RESTAssignedPropertyTagV1.REVISIONS_NAME);

        // Load the translated topic from the REST Interface
        final RESTTranslatedTopicV1 tempTranslatedTopic;
        if (topicRevision == null) {
            tempTranslatedTopic = getRESTClient().getJSONTranslatedTopic(topicId, expandString);
        } else {
            tempTranslatedTopic = getRESTClient().getJSONTranslatedTopicRevision(topicId, topicRevision, expandString);
        }

        if (translatedTopic == null) {
            translatedTopic = tempTranslatedTopic;
            getRESTEntityCache().add(translatedTopic, topicRevision);
        } else if (translatedTopic.getSourceUrls_OTM() == null) {
            translatedTopic.setProperties(tempTranslatedTopic.getProperties());
        } else {
            // Iterate over the current and old source urls and add any missing objects.
            final List<RESTAssignedPropertyTagV1> properties = translatedTopic.getProperties().returnItems();
            final List<RESTAssignedPropertyTagV1> newProperties = tempTranslatedTopic.getProperties().returnItems();
            for (final RESTAssignedPropertyTagV1 newProperty : newProperties) {
                boolean found = false;

                for (final RESTAssignedPropertyTagV1 property : properties) {
                    if (property.getId().equals(newProperty.getId())) {
                        property.setRevisions(newProperty.getRevisions());

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    translatedTopic.getProperties().addItem(newProperty);
                }
            }
        }

        // Find the matching properties and return it's revisions
        for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : translatedTopic.getProperties().getItems()) {
            final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

            if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                return propertyTag.getRevisions();
            }
        }

        return null;
    }
}