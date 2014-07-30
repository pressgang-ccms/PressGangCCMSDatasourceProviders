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
import java.util.Set;

import com.google.common.collect.Sets;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTextContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextCSProcessingOptionsV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.RESTTextCSProcessingOptionsV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.RESTTextContentSpecV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.TextCSProcessingOptionsWrapper;
import org.jboss.pressgang.ccms.wrapper.TextContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTextContentSpecProvider extends RESTDataProvider implements TextContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTextContentSpecProvider.class);
    private boolean expandProperties = false;

    protected RESTTextContentSpecProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    public void setExpandProperties(final boolean expandProperties) {
        this.expandProperties = expandProperties;
    }

    protected Set<String> getDefaultContentSpecMethodList() {
        if (expandProperties) {
            return Sets.newHashSet("getText", "getProperties");
        } else {
            return Sets.newHashSet("getText");
        }
    }

    protected Set<String> getDefaultContentSpecExpansionList() {
        if (expandProperties) {
            return Sets.newHashSet(RESTTextContentSpecV1.TEXT_NAME, RESTTextContentSpecV1.PROPERTIES_NAME);
        } else {
            return Sets.newHashSet(RESTTextContentSpecV1.TEXT_NAME);
        }
    }

    protected RESTTextContentSpecV1 loadContentSpec(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTextContentSpec(id, expandString);
        } else {
            return getRESTClient().getJSONTextContentSpecRevision(id, revision, expandString);
        }
    }

    public RESTTextContentSpecV1 getRESTTextContentSpec(int id) {
        return getRESTTextContentSpec(id, null);
    }

    @Override
    public TextContentSpecWrapper getTextContentSpec(int id) {
        return getTextContentSpec(id, null);
    }

    public RESTTextContentSpecV1 getRESTTextContentSpec(int id, Integer revision) {
        try {
            final RESTTextContentSpecV1 contentSpec;
            if (getRESTEntityCache().containsKeyValue(RESTTextContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTTextContentSpecV1.class, id, revision);
            } else {
                final String expandString = getExpansionString(getDefaultContentSpecExpansionList());
                contentSpec = loadContentSpec(id, revision, expandString);
                getRESTEntityCache().add(contentSpec, revision);
            }
            return contentSpec;
        } catch (Exception e) {
            log.debug("Failed to retrieve Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TextContentSpecWrapper getTextContentSpec(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTTextContentSpec(id, revision))
                .expandedMethods(getDefaultContentSpecMethodList())
                .isRevision(revision != null)
                .build();
    }

    public RESTTextContentSpecCollectionV1 getRESTTextContentSpecsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            // We need to expand the all the content specs in the collection
            final Set<String> subCollection = getDefaultContentSpecExpansionList();
            subCollection.remove(RESTTextContentSpecV1.TEXT_NAME);
            final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_EXPANSION_NAME, subCollection);

            final RESTTextContentSpecCollectionV1 contentSpecs = getRESTClient().getJSONTextContentSpecsWithQuery(
                    new PathSegmentImpl(query, false), expandString);
            getRESTEntityCache().add(contentSpecs);

            return contentSpecs;
        } catch (Exception e) {
            log.debug("Failed to retrieve ContentSpecs with Query: " + query, e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TextContentSpecWrapper> getTextContentSpecsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<TextContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTextContentSpecsWithQuery(query))
                .expandedEntityMethods(expandProperties ? Arrays.asList("getProperties") : null)
                .build();
    }

    public RESTTagCollectionV1 getRESTTextContentSpecTags(int id, Integer revision) {
        try {
            RESTTextContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTextContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTTextContentSpecV1.class, id, revision);

                if (contentSpec.getTags() != null) {
                    return contentSpec.getTags();
                }
            }

            // We need to expand the tags in the content spec
            final String expandString = getExpansionString(RESTTextContentSpecV1.TAGS_NAME);

            // Load the content spec from the REST Interface
            final RESTTextContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                getRESTEntityCache().add(contentSpec, revision);
            } else {
                contentSpec.setTags(tempContentSpec.getTags());
            }
            getRESTEntityCache().add(contentSpec.getTags(), revision != null);

            return contentSpec.getTags();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Tags for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTTextContentSpecCollectionV1 getRESTTextContentSpecRevisions(int id, Integer revision) {
        try {
            RESTTextContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTextContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTTextContentSpecV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return contentSpec.getRevisions();
                }
            }

            // We need to expand the revisions in the content spec
            final String expandString = getExpansionString(RESTTextContentSpecV1.REVISIONS_NAME);

            // Load the content spec from the REST Interface
            final RESTTextContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                getRESTEntityCache().add(contentSpec, revision);
            } else {
                contentSpec.setRevisions(tempContentSpec.getRevisions());
            }

            return contentSpec.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TextContentSpecWrapper> getTextContentSpecRevisions(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TextContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTextContentSpecRevisions(id, revision))
                .isRevisionCollection()
                .build();
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTContentSpecProperties(int id, final Integer revision) {
        try {
            RESTTextContentSpecV1 contentspec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTextContentSpecV1.class, id, revision)) {
                contentspec = getRESTEntityCache().get(RESTTextContentSpecV1.class, id, revision);

                if (contentspec.getProperties() != null) {
                    return contentspec.getProperties();
                }
            }

            // We need to expand the all the properties in the content spec
            final String expandString = getExpansionString(RESTTextContentSpecV1.PROPERTIES_NAME);

            // Load the content spec from the REST Interface
            final RESTTextContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentspec == null) {
                contentspec = tempContentSpec;
                getRESTEntityCache().add(contentspec, revision);
            } else {
                contentspec.setProperties(tempContentSpec.getProperties());
            }
            getRESTEntityCache().add(contentspec.getProperties(), revision != null);

            return contentspec.getProperties();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Properties for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TextContentSpecWrapper createTextContentSpec(final TextContentSpecWrapper contentSpecEntity) {
        return createTextContentSpec(contentSpecEntity, null, null);
    }

    @Override
    public TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpecEntity,
            TextCSProcessingOptionsWrapper processingOptions) {
        return createTextContentSpec(contentSpecEntity, processingOptions, null);
    }

    @Override
    public TextContentSpecWrapper createTextContentSpec(TextContentSpecWrapper contentSpecEntity,
            TextCSProcessingOptionsWrapper processingOptions, LogMessageWrapper logMessage) {
        try {
            final RESTTextContentSpecV1 contentSpec = ((RESTTextContentSpecV1Wrapper) contentSpecEntity).unwrap();
            if (processingOptions != null) {
                contentSpec.setProcessingOptions(((RESTTextCSProcessingOptionsV1Wrapper) processingOptions).unwrap());
            }

            // Expand the text
            final String expandString = getExpansionString(RESTTextContentSpecV1.TEXT_NAME);

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(contentSpec);

            final RESTTextContentSpecV1 createdContentSpec;
            if (logMessage != null) {
                createdContentSpec = getRESTClient().createJSONTextContentSpec(expandString, contentSpec, logMessage.getMessage(),
                        logMessage.getFlags(), logMessage.getUser());
            } else {
                createdContentSpec = getRESTClient().createJSONTextContentSpec(expandString, contentSpec);
            }
            if (createdContentSpec != null) {
                getRESTEntityCache().add(createdContentSpec);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(createdContentSpec)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpecEntity) {
        return updateTextContentSpec(contentSpecEntity, null, null);
    }

    @Override
    public TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpecEntity,
            TextCSProcessingOptionsWrapper processingOptions) {
        return updateTextContentSpec(contentSpecEntity, processingOptions, null);
    }

    @Override
    public TextContentSpecWrapper updateTextContentSpec(TextContentSpecWrapper contentSpecEntity,
            TextCSProcessingOptionsWrapper processingOptions, LogMessageWrapper logMessage) {
        try {
            final RESTTextContentSpecV1 contentSpec = ((RESTTextContentSpecV1Wrapper) contentSpecEntity).unwrap();
            if (processingOptions != null) {
                contentSpec.setProcessingOptions(((RESTTextCSProcessingOptionsV1Wrapper) processingOptions).unwrap());
            }

            // Expand the text
            final String expandString = getExpansionString(RESTTextContentSpecV1.TEXT_NAME);

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(contentSpec);

            final RESTTextContentSpecV1 updatedContentSpec;
            if (logMessage != null) {
                updatedContentSpec = getRESTClient().updateJSONTextContentSpec(expandString, contentSpec, logMessage.getMessage(),
                        logMessage.getFlags(), logMessage.getUser());
            } else {
                updatedContentSpec = getRESTClient().updateJSONTextContentSpec(expandString, contentSpec);
            }
            if (updatedContentSpec != null) {
                getRESTEntityCache().expire(RESTTextContentSpecV1.class, contentSpecEntity.getId());
                getRESTEntityCache().add(updatedContentSpec);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(updatedContentSpec)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public TextContentSpecWrapper newTextContentSpec() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTTextContentSpecV1())
                .newEntity()
                .build();
    }

    @Override
    public TextCSProcessingOptionsWrapper newTextProcessingOptions() {
        return new RESTTextCSProcessingOptionsV1Wrapper(new RESTTextCSProcessingOptionsV1());
    }
}
