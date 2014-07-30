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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTCategoryInTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTTagInCategoryCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.utils.constants.CommonFilterConstants;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTagProvider extends RESTDataProvider implements TagProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTagProvider.class);

    protected RESTTagProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTTagV1 loadTag(int id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTag(id, expandString);
        } else {
            return getRESTClient().getJSONTagRevision(id, revision, expandString);
        }
    }

    public RESTTagV1 getRESTTag(int id) {
        return getRESTTag(id, null);
    }

    @Override
    public TagWrapper getTag(int id) {
        return getTag(id, null);
    }

    public RESTTagV1 getRESTTag(int id, Integer revision) {
        try {
            final RESTTagV1 tag;
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, id, revision);
            } else {
                tag = loadTag(id, revision, "");
                getRESTEntityCache().add(tag, revision);
            }
            return tag;
        } catch (Exception e) {
            log.debug("Failed to retrieve Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TagWrapper getTag(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTTag(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTTagCollectionV1 getRESTTagsByName(final String name) {
        final List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add(name);

        try {
            final RESTTagCollectionV1 tags;
            if (getRESTCollectionCache().containsKey(RESTTagV1.class, keys)) {
                tags = getRESTCollectionCache().get(RESTTagV1.class, RESTTagCollectionV1.class, keys);
            } else {
                final String query = "query;" + CommonFilterConstants.TAG_NAME_MATCHES_FILTER_VAR + "=" + name + ";";
                // We need to expand the Tags collection
                final String expandString = getExpansionString(RESTv1Constants.TAGS_EXPANSION_NAME);

                // Load the tags from the REST Interface
                tags = getRESTClient().getJSONTagsWithQuery(new PathSegmentImpl(query, false), expandString);
                getRESTCollectionCache().add(RESTTagV1.class, tags, keys);
            }

            return tags;
        } catch (Exception e) {
            log.debug("Failed to retrieve Tags for Name " + name, e);
            throw handleException(e);
        }
    }

    @Override
    public TagWrapper getTagByName(final String name) {
        final RESTTagCollectionV1 tags = getRESTTagsByName(name);
        if (tags != null && tags.getItems() != null && !tags.getItems().isEmpty()) {
            for (final RESTTagV1 tag : tags.returnItems()) {
                if (tag.getName().equals(name)) {
                    return RESTEntityWrapperBuilder.newBuilder()
                            .providerFactory(getProviderFactory())
                            .entity(tag)
                            .build();
                }
            }
        }

        return null;
    }

    public RESTCategoryInTagCollectionV1 getRESTTagCategories(int id, final Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, id, revision);

                if (tag.getCategories() != null) {
                    return tag.getCategories();
                }
            }

            // We need to expand the categories in the tag collection
            final String expandString = getExpansionString(RESTTagV1.CATEGORIES_NAME);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, revision);
            } else {
                tag.setCategories(tempTag.getCategories());
            }
            getRESTEntityCache().add(tag.getCategories(), revision != null);

            return tag.getCategories();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Categories for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTTagCollectionV1 getRESTTagChildTags(int id, final Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, id, revision);

                if (tag.getChildTags() != null) {
                    return tag.getChildTags();
                }
            }

            // We need to expand the all child tags in the tag
            final String expandString = getExpansionString(RESTTagV1.CHILD_TAGS_NAME);

            // Load the Tag from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, revision);
            } else {
                tag.setChildTags(tempTag.getChildTags());
            }
            getRESTEntityCache().add(tag.getChildTags(), revision != null);

            return tag.getChildTags();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Child Tags for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagChildTags(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTagChildTags(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getChildTags"))
                .build();
    }

    public RESTTagCollectionV1 getRESTTagParentTags(int id, Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, id, revision);

                if (tag.getParentTags() != null) {
                    return tag.getParentTags();
                }
            }

            // We need to expand the parent tags in the tag
            final String expandString = getExpansionString(RESTTagV1.PARENT_TAGS_NAME);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, revision);
            } else {
                tag.setParentTags(tempTag.getParentTags());
            }
            getRESTEntityCache().add(tag.getParentTags(), revision != null);

            return tag.getParentTags();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Parent Tags for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagParentTags(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTagParentTags(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getParentTags"))
                .build();
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTTagProperties(int id, Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, id, revision);

                if (tag.getProperties() != null) {
                    return tag.getProperties();
                }
            }

            // We need to expand the properties in the tag collection
            final String expandString = getExpansionString(RESTTagV1.PROPERTIES_NAME);

            // Load the tag from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, revision);
            } else {
                tag.setProperties(tempTag.getProperties());
            }
            getRESTEntityCache().add(tag.getProperties(), revision != null);

            return tag.getProperties();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Properties for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> getTagProperties(int id, Integer revision) {
        return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInTagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTagProperties(id, revision))
                .isRevisionCollection(revision != null)
                .entityWrapperInterface(PropertyTagInTagWrapper.class)
                .expandedEntityMethods(Arrays.asList("getProperties"))
                .build();
    }

    public RESTTagCollectionV1 getRESTTagRevisions(int id, final Integer revision) {
        try {
            RESTTagV1 tag = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTagV1.class, id, revision)) {
                tag = getRESTEntityCache().get(RESTTagV1.class, id, revision);

                if (tag.getRevisions() != null) {
                    return tag.getRevisions();
                }
            }

            // We need to expand the revisions in the tag collection
            final String expandString = getExpansionString(RESTTagV1.REVISIONS_NAME);

            // Load the tags from the REST Interface
            final RESTTagV1 tempTag = loadTag(id, revision, expandString);

            if (tag == null) {
                tag = tempTag;
                getRESTEntityCache().add(tag, revision);
            } else {
                tag.setRevisions(tempTag.getRevisions());
            }

            return tag.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Tag " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagRevisions(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTagRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }

    @Override
    public TagWrapper newTag() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTTagV1())
                .newEntity()
                .build();
    }

    @Override
    public TagInCategoryWrapper newTagInCategory(final CategoryWrapper parent) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTTagInCategoryV1())
                .newEntity()
                .parent(parent == null ? null : (RESTCategoryV1) parent.unwrap())
                .build();
    }

    @Override
    public CollectionWrapper<TagWrapper> newTagCollection() {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTTagCollectionV1())
                .build();
    }

    @Override
    public CollectionWrapper<TagInCategoryWrapper> newTagInCategoryCollection(final CategoryWrapper parent) {
        return RESTCollectionWrapperBuilder.<TagInCategoryWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTTagInCategoryCollectionV1())
                .parent(parent == null ? null : (RESTCategoryV1) parent.unwrap())
                .build();
    }
}
