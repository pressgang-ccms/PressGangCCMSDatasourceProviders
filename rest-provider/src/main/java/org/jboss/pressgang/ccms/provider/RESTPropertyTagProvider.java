package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagProvider extends RESTDataProvider implements PropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagProvider.class);

    public RESTPropertyTagProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTPropertyTagV1 loadPropertyTag(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONPropertyTag(id, expandString);
        } else {
            return getRESTClient().getJSONPropertyTagRevision(id, revision, expandString);
        }
    }

    public RESTPropertyTagV1 getRESTPropertyTag(int id) {
        return getRESTPropertyTag(id, null);
    }

    @Override
    public PropertyTagWrapper getPropertyTag(int id) {
        return getPropertyTag(id, null);
    }

    public RESTPropertyTagV1 getRESTPropertyTag(int id, final Integer revision) {
        try {
            final RESTPropertyTagV1 propertyTag;
            if (getRESTEntityCache().containsKeyValue(RESTPropertyTagV1.class, id, revision)) {
                propertyTag = getRESTEntityCache().get(RESTPropertyTagV1.class, id, revision);
            } else {
                propertyTag = loadPropertyTag(id, revision, null);
                getRESTEntityCache().add(propertyTag, revision);
            }
            return propertyTag;
        } catch (Exception e) {
            log.debug("Failed to retrieve Property Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public PropertyTagWrapper getPropertyTag(int id, final Integer revision) {
        return getWrapperFactory().create(getRESTPropertyTag(id, revision), revision != null);
    }

    /*@Override
    public UpdateableCollectionWrapper<PropertyCategoryWrapper> getPropertyTagCategories(int id, final Integer revision) {
        try {
            RESTPropertyTagV1 propertyTag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTPropertyTagV1.class, id, revision)) {
                propertyTag = getRESTEntityCache().get(RESTPropertyTagV1.class, id, revision);

                if (propertyTag.getPropertyCategories() != null) {
                    return (UpdateableCollectionWrapper<PropertyCategoryWrapper>) getWrapperFactory().createPropertyTagCollection(
                            propertyTag.getPropertyCategories(), revision != null);
                }
            }

            // We need to expand the all the property tag categories in the property tag
            final String expandString = getExpansionString(RESTPropertyTagV1.PROPERTY_CATEGORIES_NAME);

            // Load the property tag from the REST Interface
            final RESTPropertyTagV1 tempPropertyTag = loadPropertyTag(id, revision, expandString);

            if (propertyTag == null) {
                propertyTag = tempPropertyTag;
                getRESTEntityCache().add(propertyTag, revision);
            } else {
                propertyTag.setPropertyCategories(tempPropertyTag.getPropertyCategories());
            }

            return (UpdateableCollectionWrapper<PropertyCategoryWrapper>) getWrapperFactory().createPropertyTagCollection(
                    propertyTag.getPropertyCategories(), revision != null);
        } catch (Exception e) {
            log.debug("Failed to retrieve the Property Categories for Property Tag " + id + (revision == null ? "" :
                    (", Revision " + revision)), e);
            throw handleException(e);
        }
    }*/

    public RESTPropertyTagCollectionV1 getRESTPropertyTagRevisions(int id, Integer revision) {
        try {
            RESTPropertyTagV1 propertyTag = null;
            if (getRESTEntityCache().containsKeyValue(RESTPropertyTagV1.class, id, revision)) {
                propertyTag = getRESTEntityCache().get(RESTPropertyTagV1.class, id, revision);

                if (propertyTag.getRevisions() != null) {
                    return propertyTag.getRevisions();
                }
            }

            // We need to expand the all the revisions in the property tag
            final String expandString = getExpansionString(RESTPropertyTagV1.REVISIONS_NAME);

            // Load the property tag from the REST Interface
            final RESTPropertyTagV1 tempPropertyTag = loadPropertyTag(id, revision, expandString);

            if (propertyTag == null) {
                propertyTag = tempPropertyTag;
                getRESTEntityCache().add(propertyTag, revision);
            } else {
                propertyTag.setRevisions(tempPropertyTag.getRevisions());
            }

            return propertyTag.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve Revisions for Property Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> getPropertyTagRevisions(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTPropertyTagRevisions(id, revision), RESTPropertyTagV1.class, true);
    }

    @Override
    public PropertyTagWrapper newPropertyTag() {
        return getWrapperFactory().create(new RESTPropertyTagV1(), false, true);
    }

    @Override
    public PropertyTagInPropertyCategoryWrapper newPropertyTagInPropertyCategory() {
        return getWrapperFactory().create(new RESTPropertyTagInPropertyCategoryV1(), false, true);
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrapperedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1(), false, unwrapperedTopic, PropertyTagInTopicWrapper.class, true);
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final PropertyTagWrapper propertyTag, final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrapperedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()),
                propertyTag.isRevisionEntity(), unwrapperedTopic, PropertyTagInTopicWrapper.class, true);
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final TagWrapper tag) {
        final RESTTagV1 unwrapperTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1(), false, unwrapperTag, PropertyTagInTagWrapper.class, true);
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final PropertyTagWrapper propertyTag, final TagWrapper tag) {
        final RESTTagV1 unwrapperTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()),
                propertyTag.isRevisionEntity(), unwrapperTag, PropertyTagInTagWrapper.class, true);
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1(), false, unwrappedContentSpec,
                PropertyTagInContentSpecWrapper.class, true);
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final PropertyTagWrapper propertyTag,
            final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return getWrapperFactory().create(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()),
                propertyTag.isRevisionEntity(), unwrappedContentSpec, PropertyTagInContentSpecWrapper.class, true);
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
