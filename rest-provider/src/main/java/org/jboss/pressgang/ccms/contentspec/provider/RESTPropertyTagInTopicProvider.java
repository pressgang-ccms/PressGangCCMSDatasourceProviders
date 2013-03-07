package org.jboss.pressgang.ccms.contentspec.provider;

import java.io.IOException;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.items.join.RESTAssignedPropertyTagCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.exceptions.InternalProcessingException;
import org.jboss.pressgang.ccms.rest.v1.exceptions.InvalidParameterException;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagInTopicProvider extends RESTPropertyTagProvider implements PropertyTagInTopicProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagInTopicProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    protected RESTPropertyTagInTopicProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public CollectionWrapper<PropertyTagInTopicWrapper> getPropertyTagInTopicRevisions(int id, Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get PropertyTagInTopic revisions using V1 of the REST Interface.");
    }

    public CollectionWrapper<PropertyTagInTopicWrapper> getPropertyTagInTopicRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> topic) {
        try {
            final RESTAssignedPropertyTagCollectionV1 propertyTagRevisions;
            if (topic instanceof RESTTranslatedTopicV1) {
                propertyTagRevisions = getTranslatedTopicPropertyRevisions(id, revision, topic);
            } else {
                propertyTagRevisions = getTopicPropertyRevisions(id, revision, topic);
            }

            return getWrapperFactory().createCollection(propertyTagRevisions, RESTAssignedPropertyTagV1.class, true, topic);
        } catch (Exception e) {
            log.error("Unable to retrieve the Revisions for PropertyTagInTopic " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    protected RESTAssignedPropertyTagCollectionV1 getTopicPropertyRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) throws IOException, InvalidParameterException, InternalProcessingException {
        final Integer tagId = parent.getId();
        final Integer tagRevision = ((RESTBaseEntityV1ProxyHandler<RESTTopicV1>) ((ProxyObject) parent).getHandler()).getEntityRevision();

        RESTTopicV1 topic = null;
        // Check the cache first
        if (entityCache.containsKeyValue(RESTTopicV1.class, tagId, tagRevision)) {
            topic = entityCache.get(RESTTopicV1.class, tagId, tagRevision);
        }

        /* We need to expand the all the items in the topic collection */
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.PROPERTIES_NAME));
        final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTAssignedPropertyTagV1.REVISIONS_NAME));

        expandProperties.setBranches(CollectionUtilities.toArrayList(expandRevisions));
        expand.setBranches(CollectionUtilities.toArrayList(expandProperties));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTopicV1 tempTopic;
        if (tagRevision == null) {
            tempTopic = client.getJSONTopic(tagId, expandString);
        } else {
            tempTopic = client.getJSONTopicRevision(tagId, tagRevision, expandString);
        }

        if (topic == null) {
            topic = tempTopic;
            if (tagRevision == null) {
                entityCache.add(topic);
            } else {
                entityCache.add(topic, tagRevision);
            }
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

        for (final RESTAssignedPropertyTagCollectionItemV1 propertyItem : topic.getProperties().getItems()) {
            final RESTAssignedPropertyTagV1 propertyTag = propertyItem.getItem();

            if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                return propertyTag.getRevisions();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    protected RESTAssignedPropertyTagCollectionV1 getTranslatedTopicPropertyRevisions(int id, final Integer revision,
            final RESTBaseTopicV1<?, ?, ?> parent) throws IOException, InvalidParameterException, InternalProcessingException {
        final Integer topicId = parent.getId();
        final Integer topicRevision = ((RESTBaseEntityV1ProxyHandler<RESTTranslatedTopicV1>) ((ProxyObject) parent).getHandler())
                .getEntityRevision();

        RESTTranslatedTopicV1 topic = null;
        // Check the cache first
        if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, topicId, topicRevision)) {
            topic = entityCache.get(RESTTranslatedTopicV1.class, topicId, topicRevision);
        }

                /* We need to expand the all the items in the topic collection */
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandProperties = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.PROPERTIES_NAME));
        final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTAssignedPropertyTagV1.REVISIONS_NAME));

        expandProperties.setBranches(CollectionUtilities.toArrayList(expandRevisions));
        expand.setBranches(CollectionUtilities.toArrayList(expandProperties));

        final String expandString = mapper.writeValueAsString(expand);

        final RESTTranslatedTopicV1 tempTranslatedTopic;
        if (topicRevision == null) {
            tempTranslatedTopic = client.getJSONTranslatedTopic(topicId, expandString);
        } else {
            tempTranslatedTopic = client.getJSONTranslatedTopicRevision(topicId, topicRevision, expandString);
        }

        if (topic == null) {
            topic = tempTranslatedTopic;
            if (topicRevision == null) {
                entityCache.add(topic);
            } else {
                entityCache.add(topic, topicRevision);
            }
        } else if (topic.getSourceUrls_OTM() == null) {
            topic.setProperties(tempTranslatedTopic.getProperties());
        } else {
            // Iterate over the current and old source urls and add any missing objects.
            final List<RESTAssignedPropertyTagV1> properties = topic.getProperties().returnItems();
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
                    topic.getProperties().addItem(newProperty);
                }
            }
        }

        for (final RESTAssignedPropertyTagCollectionItemV1 sourceURLItem : topic.getProperties().getItems()) {
            final RESTAssignedPropertyTagV1 propertyTag = sourceURLItem.getItem();

            if (propertyTag.getId() == id && (revision == null || propertyTag.getRevision().equals(revision))) {
                return propertyTag.getRevisions();
            }
        }

        return null;
    }
}
