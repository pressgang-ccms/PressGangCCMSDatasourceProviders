/*
  Copyright 2011-2014 Red Hat

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
import java.util.List;

import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.RESTTranslatedContentSpecV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.RESTTranslatedContentSpecCollectionV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslatedContentSpecProvider extends RESTDataProvider implements TranslatedContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTTranslatedContentSpecProvider.class);
    private static final List<String> DEFAULT_EXPANSION_WITH_CHILDREN = Arrays.asList(RESTTranslatedContentSpecV1.CONTENT_SPEC_NAME,
            RESTTranslatedContentSpecV1.TRANSLATED_NODES_NAME);
    private static final List<String> DEFAULT_METHOD_WITH_CHILDREN = Arrays.asList("getContentSpec", "getTranslatedNodes_OTM");

    protected RESTTranslatedContentSpecProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    public RESTTranslatedContentSpecV1 getRESTTranslatedContentSpec(int id) {
        return getRESTTranslatedContentSpec(id, null);
    }

    protected RESTTranslatedContentSpecV1 loadTranslatedContentSpec(int id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONTranslatedContentSpec(id, expandString);
        } else {
            return getRESTClient().getJSONTranslatedContentSpecRevision(id, revision, expandString);
        }
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec(int id) {
        return getTranslatedContentSpec(id, null);
    }

    public RESTTranslatedContentSpecV1 getRESTTranslatedContentSpec(int id, Integer revision) {
        try {
            final RESTTranslatedContentSpecV1 node;
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedContentSpecV1.class, id, revision)) {
                node = getRESTEntityCache().get(RESTTranslatedContentSpecV1.class, id, revision);
            } else {
                final String expandString = getExpansionString(DEFAULT_EXPANSION_WITH_CHILDREN);
                node = loadTranslatedContentSpec(id, revision, expandString);
                getRESTEntityCache().add(node, revision);
            }
            return node;
        } catch (Exception e) {
            log.debug("Failed to retrieve Translated Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public TranslatedContentSpecWrapper getTranslatedContentSpec(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTTranslatedContentSpec(id, revision))
                .expandedMethods(DEFAULT_METHOD_WITH_CHILDREN)
                .isRevision(revision != null)
                .build();
    }

    public RESTTranslatedCSNodeCollectionV1 getRESTTranslatedNodes(int id, Integer revision) {
        try {
            RESTTranslatedContentSpecV1 translatedContentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedContentSpecV1.class, id, revision)) {
                translatedContentSpec = getRESTEntityCache().get(RESTTranslatedContentSpecV1.class, id, revision);

                if (translatedContentSpec.getTranslatedNodes_OTM() != null) {
                    return translatedContentSpec.getTranslatedNodes_OTM();
                }
            }

            // We need to expand the all the translated nodes in the translated content collection
            final String expandString = getExpansionString(RESTTranslatedContentSpecV1.TRANSLATED_NODES_NAME);

            // Load the translated content spec node from the REST Interface
            final RESTTranslatedContentSpecV1 tempTranslatedContentSpec = loadTranslatedContentSpec(id, revision, expandString);

            if (translatedContentSpec == null) {
                translatedContentSpec = tempTranslatedContentSpec;
                getRESTEntityCache().add(translatedContentSpec, revision);
            } else {
                translatedContentSpec.setTranslatedNodes_OTM(tempTranslatedContentSpec.getTranslatedNodes_OTM());
            }

            return translatedContentSpec.getTranslatedNodes_OTM();
        } catch (Exception e) {
            log.error(
                    "Unable to retrieve the Translated ContentSpec Nodes for Translated ContentSpec " + id + (revision == null ? "" : ("," +
                            " Revision" + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes(int id, Integer revision) {
        return (UpdateableCollectionWrapper<TranslatedCSNodeWrapper>) RESTCollectionWrapperBuilder.<TranslatedCSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedNodes(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getTranslatedNodes_OTM"))
                .build();
    }

    public RESTTranslatedContentSpecCollectionV1 getRESTTranslatedContentSpecRevisions(int id, Integer revision) {
        try {
            RESTTranslatedContentSpecV1 translatedContentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTTranslatedContentSpecV1.class, id, revision)) {
                translatedContentSpec = getRESTEntityCache().get(RESTTranslatedContentSpecV1.class, id, revision);

                if (translatedContentSpec.getRevisions() != null) {
                    return translatedContentSpec.getRevisions();
                }
            }

            // We need to expand the revisions in the content spec translated node collection
            final String expandString = getExpansionString(RESTTranslatedContentSpecV1.REVISIONS_NAME);

            // Load the content spec translated node from the REST Interface
            final RESTTranslatedContentSpecV1 tempTranslatedContentSpec = loadTranslatedContentSpec(id, revision, expandString);

            if (translatedContentSpec == null) {
                translatedContentSpec = tempTranslatedContentSpec;
                getRESTEntityCache().add(translatedContentSpec, revision);
            } else {
                translatedContentSpec.setRevisions(tempTranslatedContentSpec.getRevisions());
            }

            return translatedContentSpec.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Translated Content Spec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecRevisions(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TranslatedContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedContentSpecRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }

    public RESTTranslatedContentSpecCollectionV1 getRESTTranslatedContentSpecsWithQuery(String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            // We need to expand the all the Translated Content Specs in the collection
            final String expandString = getExpansionString(RESTv1Constants.TRANSLATED_CONTENT_SPEC_EXPANSION_NAME,
                    DEFAULT_EXPANSION_WITH_CHILDREN);

            final RESTTranslatedContentSpecCollectionV1 contentSpecs = getRESTClient().getJSONTranslatedContentSpecsWithQuery(
                    new PathSegmentImpl(query, false), expandString);
            getRESTEntityCache().add(contentSpecs);

            return contentSpecs;
        } catch (Exception e) {
            log.debug("Failed to retrieve Translated Content Specs with a query", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecsWithQuery(String query) {
        if (query == null || query.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<TranslatedContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTTranslatedContentSpecsWithQuery(query))
                .expandedEntityMethods(DEFAULT_METHOD_WITH_CHILDREN)
                .build();
    }

    @Override
    public TranslatedContentSpecWrapper createTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpecEntity) {
        try {
            final RESTTranslatedContentSpecV1 translatedContentSpec = ((RESTTranslatedContentSpecV1Wrapper) translatedContentSpecEntity)
                    .unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(translatedContentSpec);

            final String expandString = getExpansionString(DEFAULT_EXPANSION_WITH_CHILDREN);

            final RESTTranslatedContentSpecV1 createdTranslatedContentSpec = getRESTClient().createJSONTranslatedContentSpec(expandString,
                    translatedContentSpec);
            if (createdTranslatedContentSpec != null) {
                // Expire any content specs as it's translated content spec collection will now be invalid
                getRESTEntityCache().expire(RESTContentSpecV1.class, createdTranslatedContentSpec.getContentSpecId());
                getRESTEntityCache().expire(RESTContentSpecV1.class, createdTranslatedContentSpec.getContentSpecId(),
                        createdTranslatedContentSpec.getContentSpecRevision());
                getRESTEntityCache().add(createdTranslatedContentSpec);
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(createdTranslatedContentSpec)
                        .expandedMethods(DEFAULT_METHOD_WITH_CHILDREN)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public TranslatedContentSpecWrapper updateTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpecEntity) {
        try {
            final RESTTranslatedContentSpecV1 translatedContentSpec = ((RESTTranslatedContentSpecV1Wrapper) translatedContentSpecEntity)
                    .unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(translatedContentSpec);

            final String expandString = getExpansionString(DEFAULT_EXPANSION_WITH_CHILDREN);
            final RESTTranslatedContentSpecV1 updatedTranslatedContentSpec = getRESTClient().updateJSONTranslatedContentSpec(expandString,
                    translatedContentSpec);
            if (updatedTranslatedContentSpec != null) {
                getRESTEntityCache().expire(RESTTranslatedContentSpecV1.class, updatedTranslatedContentSpec.getId());
                getRESTEntityCache().add(updatedTranslatedContentSpec);
                // Expire any content specs as it's translated content spec collection will now be invalid
                getRESTEntityCache().expire(RESTContentSpecV1.class, updatedTranslatedContentSpec.getContentSpecId());
                getRESTEntityCache().expire(RESTContentSpecV1.class, updatedTranslatedContentSpec.getContentSpecId(),
                        updatedTranslatedContentSpec.getContentSpecRevision());
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(updatedTranslatedContentSpec)
                        .expandedMethods(DEFAULT_METHOD_WITH_CHILDREN)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> createTranslatedContentSpecs(
            CollectionWrapper<TranslatedContentSpecWrapper> translatedNodes) {
        try {
            final RESTTranslatedContentSpecCollectionV1 unwrappedNodes = ((RESTTranslatedContentSpecCollectionV1Wrapper) translatedNodes)
                    .unwrap();

            final String expandString = getExpansionString(RESTv1Constants.TRANSLATED_CONTENT_SPEC_EXPANSION_NAME,
                    DEFAULT_EXPANSION_WITH_CHILDREN);
            final RESTTranslatedContentSpecCollectionV1 createdTranslations = getRESTClient().createJSONTranslatedContentSpecs(expandString,
                    unwrappedNodes);
            if (createdTranslations != null) {
                // Expire any content specs as it's translated content spec collection will now be invalid
                for (final RESTTranslatedContentSpecV1 translatedContentSpec : createdTranslations.returnItems()) {
                    getRESTEntityCache().expire(RESTContentSpecV1.class, translatedContentSpec.getContentSpecId());
                    getRESTEntityCache().expire(RESTContentSpecV1.class, translatedContentSpec.getContentSpecId(),
                            translatedContentSpec.getContentSpecRevision());
                }
                getRESTEntityCache().add(createdTranslations, false);
                return RESTCollectionWrapperBuilder.<TranslatedContentSpecWrapper>newBuilder()
                        .providerFactory(getProviderFactory())
                        .collection(createdTranslations)
                        .expandedEntityMethods(DEFAULT_METHOD_WITH_CHILDREN)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public TranslatedContentSpecWrapper newTranslatedContentSpec() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTTranslatedContentSpecV1())
                .newEntity()
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<TranslatedContentSpecWrapper> newTranslatedContentSpecCollection() {
        return (UpdateableCollectionWrapper<TranslatedContentSpecWrapper>) RESTCollectionWrapperBuilder.<TranslatedContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTTranslatedContentSpecCollectionV1())
                .build();
    }
}
