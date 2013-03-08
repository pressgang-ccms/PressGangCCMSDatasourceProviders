package org.jboss.pressgang.ccms.contentspec.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagProvider extends RESTDataProvider implements PropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    public RESTPropertyTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    @Override
    public PropertyTagWrapper getPropertyTag(int id) {
        return getPropertyTag(id, null);
    }

    @Override
    public PropertyTagWrapper getPropertyTag(int id, final Integer revision) {
        try {
            final RESTPropertyTagV1 propertyTag;
            if (entityCache.containsKeyValue(RESTPropertyTagV1.class, id, revision)) {
                propertyTag = entityCache.get(RESTPropertyTagV1.class, id, revision);
            } else {
                if (revision == null) {
                    propertyTag = client.getJSONPropertyTag(id, null);
                    entityCache.add(propertyTag);
                } else {
                    propertyTag = client.getJSONPropertyTagRevision(id, revision, null);
                    entityCache.add(propertyTag, revision);
                }
            }
            return getWrapperFactory().create(propertyTag, revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve Property Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    /*@Override
    public UpdateableCollectionWrapper<PropertyCategoryWrapper> getPropertyTagCategories(int id, final Integer revision) {
        try {
            RESTPropertyTagV1 propertyTag = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTPropertyTagV1.class, id, revision)) {
                propertyTag = entityCache.get(RESTPropertyTagV1.class, id, revision);

                if (propertyTag.getPropertyCategories() != null) {
                    return (UpdateableCollectionWrapper<PropertyCategoryWrapper>) getWrapperFactory().createPropertyTagCollection(
                            propertyTag.getPropertyCategories(), revision != null);
                }
            }

            // We need to expand the all the items in the topic collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails(RESTPropertyTagV1.PROPERTY_CATEGORIES_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandTags));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the property tag from the REST Interface
            final RESTPropertyTagV1 tempPropertyTag;
            if (revision == null) {
                tempPropertyTag = client.getJSONPropertyTag(id, expandString);
            } else {
                tempPropertyTag = client.getJSONPropertyTagRevision(id, revision, expandString);
            }

            if (propertyTag == null) {
                propertyTag = tempPropertyTag;
                if (revision == null) {
                    entityCache.add(propertyTag);
                } else {
                    entityCache.add(propertyTag, revision);
                }
            } else {
                propertyTag.setPropertyCategories(tempPropertyTag.getPropertyCategories());
            }

            return (UpdateableCollectionWrapper<PropertyCategoryWrapper>) getWrapperFactory().createPropertyTagCollection(
                    propertyTag.getPropertyCategories(), revision != null);
        } catch (Exception e) {
            log.error("Failed to retrieve the Property Categories for Property Tag " + id + (revision == null ? "" :
                    (", Revision " + revision)), e);
        }
        return null;
    }*/

    @Override
    public CollectionWrapper<PropertyTagWrapper> getPropertyTagRevisions(int id, Integer revision) {
        try {
            RESTPropertyTagV1 propertyTag = null;
            if (entityCache.containsKeyValue(RESTPropertyTagV1.class, id, revision)) {
                propertyTag = entityCache.get(RESTPropertyTagV1.class, id, revision);

                if (propertyTag.getRevisions() != null) {
                    return getWrapperFactory().createCollection(propertyTag.getRevisions(), RESTPropertyTagV1.class, true);
                }
            }
            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTCategoryV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            final String expandString = mapper.writeValueAsString(expand);

            final RESTPropertyTagV1 tempPropertyTag;
            if (revision == null) {
                tempPropertyTag = client.getJSONPropertyTag(id, expandString);
            } else {
                tempPropertyTag = client.getJSONPropertyTagRevision(id, revision, expandString);
            }

            if (propertyTag == null) {
                propertyTag = tempPropertyTag;
                if (revision == null) {
                    entityCache.add(propertyTag);
                } else {
                    entityCache.add(propertyTag, revision);
                }
            } else {
                propertyTag.setRevisions(tempPropertyTag.getRevisions());
            }

            return getWrapperFactory().createCollection(propertyTag.getRevisions(), RESTPropertyTagV1.class, true);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public PropertyTagWrapper newPropertyTag() {
        return getWrapperFactory().create(new RESTPropertyTagV1(), false);
    }

    @Override
    public PropertyTagInPropertyCategoryWrapper newPropertyTagInPropertyCategory() {
        return getWrapperFactory().create(new RESTPropertyTagInPropertyCategoryV1(), false);
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrapperedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1(), false, unwrapperedTopic, PropertyTagInTopicWrapper.class);
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final PropertyTagWrapper propertyTag, final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrapperedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()),
                propertyTag.isRevisionEntity(), unwrapperedTopic, PropertyTagInTopicWrapper.class);
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final TagWrapper tag) {
        final RESTTagV1 unwrapperTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1(), false, unwrapperTag, PropertyTagInTagWrapper.class);
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final PropertyTagWrapper propertyTag, final TagWrapper tag) {
        final RESTTagV1 unwrapperTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()),
                propertyTag.isRevisionEntity(), unwrapperTag, PropertyTagInTagWrapper.class);
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1(), false, unwrappedContentSpec,
                PropertyTagInContentSpecWrapper.class);
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final PropertyTagWrapper propertyTag,
            final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()),
                propertyTag.isRevisionEntity(), unwrappedContentSpec, PropertyTagInContentSpecWrapper.class);
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> newPropertyTagCollection() {
        return getWrapperFactory().createCollection(new RESTPropertyTagCollectionV1(), RESTPropertyTagV1.class, false);
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> newPropertyTagInTopicCollection(final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrapperedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                new RESTAssignedPropertyTagCollectionV1(), RESTAssignedPropertyTagV1.class, false, unwrapperedTopic,
                PropertyTagInTopicWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> newPropertyTagInTagCollection(final TagWrapper tag) {
        final RESTTagV1 unwrapperTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        final CollectionWrapper<PropertyTagInTagWrapper> collection = getWrapperFactory().createCollection(
                new RESTAssignedPropertyTagCollectionV1(), RESTAssignedPropertyTagV1.class, false, unwrapperTag,
                PropertyTagInTagWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> newPropertyTagInContentSpecCollection(
            final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                new RESTAssignedPropertyTagCollectionV1(), RESTAssignedPropertyTagV1.class, false, unwrappedContentSpec,
                PropertyTagInContentSpecWrapper.class);
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> newPropertyTagInPropertyCategoryCollection() {
        final CollectionWrapper<PropertyTagInPropertyCategoryWrapper> collection = getWrapperFactory().createCollection(
                new RESTPropertyTagInPropertyCategoryCollectionV1(), RESTPropertyTagInPropertyCategoryV1.class, false);
        return (UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper>) collection;
    }
}
