package org.jboss.pressgang.ccms.provider;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTCSNodeCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.contentspec.RESTTranslatedContentSpecCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.join.RESTAssignedPropertyTagCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseObjectV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.base.RESTBaseCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTContentSpecV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTContentSpecProvider extends RESTDataProvider implements ContentSpecProvider {
    private static Logger log = LoggerFactory.getLogger(RESTContentSpecProvider.class);
    private static final List<String> SUB_EXPANSION_WITH_CHILDREN = Arrays.asList(RESTCSNodeV1.NEXT_NODE_NAME, RESTCSNodeV1.RELATED_TO_NAME,
            RESTCSNodeV1.CHILDREN_NAME);
    private static final List<String> SUB_METHOD_WITH_CHILDREN = Arrays.asList("getNextNode", "getRelatedToNodes", "getChildren_OTM");

    private int count = -1000;

    protected RESTContentSpecProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTContentSpecV1 loadContentSpec(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONContentSpec(id, expandString);
        } else {
            return getRESTClient().getJSONContentSpecRevision(id, revision, expandString);
        }
    }

    public RESTContentSpecV1 getRESTContentSpec(int id) {
        return getRESTContentSpec(id, null);
    }

    @Override
    public ContentSpecWrapper getContentSpec(int id) {
        return getContentSpec(id, null);
    }

    public RESTContentSpecV1 getRESTContentSpec(int id, Integer revision) {
        try {
            final RESTContentSpecV1 contentSpec;
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);
            } else {
                contentSpec = loadContentSpec(id, revision, "");
                getRESTEntityCache().add(contentSpec, revision);
            }
            return contentSpec;
        } catch (Exception e) {
            log.debug("Failed to retrieve Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public ContentSpecWrapper getContentSpec(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTContentSpec(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTContentSpecCollectionV1 getRESTContentSpecsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        try {
            // We need to expand the all the content specs in the collection
            final String expandString = getExpansionString(RESTv1Constants.CONTENT_SPEC_EXPANSION_NAME);

            final RESTContentSpecCollectionV1 contentSpecs = getRESTClient().getJSONContentSpecsWithQuery(new PathSegmentImpl(query, false),
                    expandString);
            getRESTEntityCache().add(contentSpecs);

            return contentSpecs;
        } catch (Exception e) {
            log.debug("Failed to retrieve ContentSpecs with Query: " + query, e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> getContentSpecsWithQuery(final String query) {
        if (query == null || query.isEmpty()) return null;

        return RESTCollectionWrapperBuilder.<ContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTContentSpecsWithQuery(query))
                .build();
    }

    public RESTTagCollectionV1 getRESTContentSpecTags(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getTags() != null) {
                    return contentSpec.getTags();
                }
            }

            // We need to expand the tags in the content spec
            final String expandString = getExpansionString(RESTContentSpecV1.TAGS_NAME);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

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

    @Override
    public CollectionWrapper<TagWrapper> getContentSpecTags(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TagWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTContentSpecTags(id, revision))
                .isRevisionCollection(revision != null)
                .build();
    }

    public RESTTagCollectionV1 getRESTContentSpecBookTags(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getBookTags() != null) {
                    return contentSpec.getBookTags();
                }
            }

            // We need to expand the tags in the content spec
            final String expandString = getExpansionString(RESTContentSpecV1.BOOK_TAGS_NAME);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                getRESTEntityCache().add(contentSpec, revision);
            } else {
                contentSpec.setBookTags(tempContentSpec.getBookTags());
            }
            getRESTEntityCache().add(contentSpec.getBookTags(), revision != null);

            return contentSpec.getBookTags();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Book Tags for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public RESTCSNodeCollectionV1 getRESTContentSpecNodes(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = (RESTContentSpecV1) getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getChildren_OTM() != null) {
                    return contentSpec.getChildren_OTM();
                }
            }

            // We need to recursively expand the children in the content spec, since there is too much data to lazy load and it all is
            // likely to be needed.
            final ExpandDataTrunk expand = getExpansion(RESTContentSpecV1.CHILDREN_NAME);
            recursiveChildrenExpand(expand, SUB_EXPANSION_WITH_CHILDREN, 0, 5);
            final String expandString = getExpansionString(expand);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                getRESTEntityCache().add(contentSpec, revision);
            } else {
                contentSpec.setChildren_OTM(tempContentSpec.getChildren_OTM());
            }
            getRESTEntityCache().add(contentSpec.getChildren_OTM(), revision != null);

            return contentSpec.getChildren_OTM();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Children Nodes for Content Spec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    protected void recursiveChildrenExpand(final ExpandDataTrunk expandDataTrunk, final List<String> subExpansionNames, int depth,
            int maxDepth) {
        // Don't include the children expansion on the last recusion
        if (depth == maxDepth) {
            final List<String> subNames = new ArrayList<String>(subExpansionNames);
            subNames.remove(RESTCSNodeV1.CHILDREN_NAME);
            expandDataTrunk.setBranches(getExpansionBranches(subNames));
        } else {
            expandDataTrunk.setBranches(getExpansionBranches(subExpansionNames));
        }
        if (depth < maxDepth) {
            for (final ExpandDataTrunk expandDataTrunk1 : expandDataTrunk.getBranches()) {
                if (expandDataTrunk1.getTrunk().getName().equals(RESTCSNodeV1.CHILDREN_NAME)) {
                    recursiveChildrenExpand(expandDataTrunk1, subExpansionNames, depth + 1, maxDepth);
                }
            }
        }
    }

    @Override
    public UpdateableCollectionWrapper<CSNodeWrapper> getContentSpecNodes(int id, Integer revision) {
        return (UpdateableCollectionWrapper<CSNodeWrapper>) RESTCollectionWrapperBuilder.<CSNodeWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTContentSpecNodes(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(SUB_METHOD_WITH_CHILDREN)
                .build();
    }

    public RESTTranslatedContentSpecCollectionV1 getRESTContentSpecTranslations(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = (RESTContentSpecV1) getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getTranslatedContentSpecs() != null) {
                    return contentSpec.getTranslatedContentSpecs();
                }
            }

            // We need to expand the tags in the content spec
            final String expandString = getExpansionString(RESTContentSpecV1.TRANSLATED_CONTENT_SPECS_NAME,
                    RESTTranslatedContentSpecV1.CONTENT_SPEC_NAME);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentSpec == null) {
                contentSpec = tempContentSpec;
                getRESTEntityCache().add(contentSpec, revision);
            } else {
                contentSpec.setTranslatedContentSpecs(tempContentSpec.getTranslatedContentSpecs());
            }
            getRESTEntityCache().add(contentSpec.getTranslatedContentSpecs(), revision != null);

            return contentSpec.getTranslatedContentSpecs();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Translations for Content Spec " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<TranslatedContentSpecWrapper> getContentSpecTranslations(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<TranslatedContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTContentSpecTranslations(id, revision))
                .isRevisionCollection(revision != null)
                .expandedEntityMethods(Arrays.asList("getContentSpec"))
                .build();
    }

    public RESTContentSpecCollectionV1 getRESTContentSpecRevisions(int id, Integer revision) {
        try {
            RESTContentSpecV1 contentSpec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentSpec = getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);

                if (contentSpec.getRevisions() != null) {
                    return contentSpec.getRevisions();
                }
            }

            // We need to expand the revisions in the content spec
            final String expandString = getExpansionString(RESTContentSpecV1.REVISIONS_NAME);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

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
    public CollectionWrapper<ContentSpecWrapper> getContentSpecRevisions(int id, Integer revision) {
        return RESTCollectionWrapperBuilder.<ContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTContentSpecRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }

    public RESTAssignedPropertyTagCollectionV1 getRESTContentSpecProperties(int id, final Integer revision) {
        try {
            RESTContentSpecV1 contentspec = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTContentSpecV1.class, id, revision)) {
                contentspec = getRESTEntityCache().get(RESTContentSpecV1.class, id, revision);

                if (contentspec.getProperties() != null) {
                    return contentspec.getProperties();
                }
            }

            // We need to expand the all the properties in the content spec
            final String expandString = getExpansionString(RESTContentSpecV1.PROPERTIES_NAME);

            // Load the content spec from the REST Interface
            final RESTContentSpecV1 tempContentSpec = loadContentSpec(id, revision, expandString);

            if (contentspec == null) {
                contentspec = tempContentSpec;
                getRESTEntityCache().add(contentspec, revision);
            } else {
                contentspec.setProperties(tempContentSpec.getProperties());
            }
            getRESTEntityCache().add(tempContentSpec.getProperties(), revision != null);

            return contentspec.getProperties();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Properties for Content Spec " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> getContentSpecProperties(int id, final Integer revision,
            final ContentSpecWrapper parent) {
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) RESTCollectionWrapperBuilder.<PropertyTagInContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTContentSpecProperties(id, revision))
                .isRevisionCollection()
                .parent(parent == null ? null : (RESTContentSpecV1) parent.unwrap())
                .expandedEntityMethods(Arrays.asList("getProperties"))
                .entityWrapperInterface(PropertyTagInContentSpecWrapper.class)
                .build();
    }

    @Override
    public String getContentSpecAsString(int id) {
        return getContentSpecAsString(id, null);
    }

    @Override
    public String getContentSpecAsString(int id, Integer revision) {
        try {
            final String contentSpec;
            if (revision == null) {
                contentSpec = getRESTClient().getTEXTContentSpec(id);
            } else {
                contentSpec = getRESTClient().getTEXTContentSpecRevision(id, revision);
            }
            return contentSpec;
        } catch (Exception e) {
            log.debug("Failed to retrieve Content Spec String " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public ContentSpecWrapper createContentSpec(final ContentSpecWrapper contentSpecEntity) {
        return createContentSpec(contentSpecEntity, null);
    }

    @Override
    public ContentSpecWrapper createContentSpec(ContentSpecWrapper contentSpecEntity, LogMessageWrapper logMessage) {
        try {
            final RESTContentSpecV1 contentSpec = ((RESTContentSpecV1Wrapper) contentSpecEntity).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(contentSpec);

            final RESTContentSpecV1 createdContentSpec;
            if (logMessage != null) {
                createdContentSpec = getRESTClient().createJSONContentSpec("", contentSpec, logMessage.getMessage(), logMessage.getFlags(),
                        logMessage.getUser());
            } else {
                createdContentSpec = getRESTClient().createJSONContentSpec("", contentSpec);
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
    public ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpecEntity) {
        return updateContentSpec(contentSpecEntity, null);
    }

    @Override
    public ContentSpecWrapper updateContentSpec(ContentSpecWrapper contentSpecEntity, LogMessageWrapper logMessage) {
        try {
            final RESTContentSpecV1 contentSpec = ((RESTContentSpecV1Wrapper) contentSpecEntity).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(contentSpec);

            final RESTContentSpecV1 updatedContentSpec;
            if (logMessage != null) {
                updatedContentSpec = getRESTClient().updateJSONContentSpec("", contentSpec, logMessage.getMessage(), logMessage.getFlags(),
                        logMessage.getUser());
            } else {
                updatedContentSpec = getRESTClient().updateJSONContentSpec("", contentSpec);
            }
            if (updatedContentSpec != null) {
                getRESTEntityCache().expire(RESTContentSpecV1.class, contentSpecEntity.getId());
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
    public boolean deleteContentSpec(Integer id) {
        return deleteContentSpec(id, null);
    }

    @Override
    public boolean deleteContentSpec(Integer id, LogMessageWrapper logMessage) {
        try {
            if (logMessage != null) {
                return getRESTClient().deleteJSONContentSpec(id, logMessage.getMessage(), logMessage.getFlags(), logMessage.getUser(),
                        "") != null;
            } else {
                return getRESTClient().deleteJSONContentSpec(id, "") != null;
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public ContentSpecWrapper newContentSpec() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(new RESTContentSpecV1())
                .newEntity()
                .build();
    }

    @Override
    public CollectionWrapper<ContentSpecWrapper> newContentSpecCollection() {
        return RESTCollectionWrapperBuilder.<ContentSpecWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(new RESTContentSpecCollectionV1())
                .build();
    }

    @Override
    public void cleanEntityForSave(final RESTBaseObjectV1<?> entity) throws InvocationTargetException, IllegalAccessException {
        if (entity instanceof RESTBaseCSNodeV1) {
            final RESTCSNodeV1 node = (RESTCSNodeV1) entity;

            // Give new nodes a negative id so that serialization can track the linked list
            if (node.getId() == null) {
                node.setId(count);
                count--;
            }

            // Remove a CSNode parent element to eliminate recursive serialization issues, since it can't be explicitly set.
            if (node.getParent() != null) {
                node.setParent(null);
            }

            // If the entity is a CSNode then replace entities, that could cause recursive serialization issues, with a dummy entity.
            if (node.getContentSpec() != null && node.getContentSpec().getId() != null) {
                final RESTContentSpecV1 dummyContentSpec = new RESTContentSpecV1();
                dummyContentSpec.setId(node.getContentSpec().getId());

                node.setContentSpec(dummyContentSpec);
            }
        }

        super.cleanEntityForSave(entity);
    }
}
