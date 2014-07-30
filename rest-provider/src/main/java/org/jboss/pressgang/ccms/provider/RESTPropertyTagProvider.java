/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTPropertyTagInPropertyCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTPropertyTagProvider extends RESTDataProvider implements PropertyTagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTPropertyTagProvider.class);

    public RESTPropertyTagProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
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
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTPropertyTag(id, revision))
                .isRevision(revision != null)
                .build();
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
        return RESTCollectionWrapperBuilder.<PropertyTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTPropertyTagRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }

    @Override
    public PropertyTagWrapper newPropertyTag() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTPropertyTagV1())
                .newEntity()
                .build();
    }

    @Override
    public PropertyTagInPropertyCategoryWrapper newPropertyTagInPropertyCategory(final PropertyCategoryWrapper propertyCategory) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTPropertyTagInPropertyCategoryV1())
                .newEntity()
                .parent(propertyCategory == null ? null : (RESTPropertyCategoryV1) propertyCategory.unwrap())
                .build();
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrappedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTAssignedPropertyTagV1())
                .newEntity()
                .parent(unwrappedTopic)
                .wrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final PropertyTagWrapper propertyTag, final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrappedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()))
                .newEntity()
                .parent(unwrappedTopic)
                .wrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final TagWrapper tag) {
        final RESTTagV1 unwrappedTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTAssignedPropertyTagV1())
                .newEntity()
                .parent(unwrappedTag)
                .wrapperInterface(PropertyTagInTagWrapper.class)
                .build();
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final PropertyTagWrapper propertyTag, final TagWrapper tag) {
        final RESTTagV1 unwrappedTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()))
                .newEntity()
                .parent(unwrappedTag)
                .wrapperInterface(PropertyTagInTagWrapper.class)
                .build();
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTAssignedPropertyTagV1())
                .newEntity()
                .parent(unwrappedContentSpec)
                .wrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final PropertyTagWrapper propertyTag,
            final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTAssignedPropertyTagV1((RESTPropertyTagV1) propertyTag.unwrap()))
                .newEntity()
                .parent(unwrappedContentSpec)
                .wrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> newPropertyTagCollection() {
        return RESTCollectionWrapperBuilder.<PropertyTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTPropertyTagCollectionV1())
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> newPropertyTagInTopicCollection(final BaseTopicWrapper<?> topic) {
        final RESTBaseTopicV1<?, ?, ?> unwrappedTopic = topic == null ? null : (RESTBaseTopicV1<?, ?, ?>) topic.unwrap();
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInTopicWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTAssignedPropertyTagCollectionV1())
                .parent(unwrappedTopic)
                .entityWrapperInterface(PropertyTagInTopicWrapper.class)
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> newPropertyTagInTagCollection(final TagWrapper tag) {
        final RESTTagV1 unwrappedTag = tag == null ? null : (RESTTagV1) tag.unwrap();
        return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTAssignedPropertyTagCollectionV1())
                .parent(unwrappedTag)
                .entityWrapperInterface(PropertyTagInTagWrapper.class)
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> newPropertyTagInContentSpecCollection(
            final ContentSpecWrapper contentSpec) {
        final RESTContentSpecV1 unwrappedContentSpec = contentSpec == null ? null : (RESTContentSpecV1) contentSpec.unwrap();
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTAssignedPropertyTagCollectionV1())
                .parent(unwrappedContentSpec)
                .entityWrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> newPropertyTagInPropertyCategoryCollection(
            PropertyCategoryWrapper propertyCategory) {
        final RESTPropertyCategoryV1 unwrappedCategory = propertyCategory == null ? null : (RESTPropertyCategoryV1) propertyCategory.unwrap();
        return (UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInPropertyCategoryWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTPropertyTagInPropertyCategoryCollectionV1())
                .parent(unwrappedCategory)
                .entityWrapperInterface(PropertyTagInPropertyCategoryWrapper.class)
                .build();
    }
}
