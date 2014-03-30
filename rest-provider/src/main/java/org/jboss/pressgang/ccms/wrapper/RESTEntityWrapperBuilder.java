package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTBaseEntityV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerEntitiesV1;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerSettingsV1;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerUndefinedEntityV1;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerUndefinedSettingV1;
import org.jboss.pressgang.ccms.rest.v1.elements.base.RESTBaseElementV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicSourceUrlV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslatedTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseTopicV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSInfoNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTextContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.base.RESTBaseContentSpecV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.join.RESTCSRelatedNodeV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTAssignedPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTCategoryInTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTPropertyTagInPropertyCategoryV1;
import org.jboss.pressgang.ccms.rest.v1.entities.join.RESTTagInCategoryV1;
import org.jboss.pressgang.ccms.utils.RESTWrapperCache;
import org.jboss.pressgang.ccms.utils.RESTWrapperKey;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.base.RESTBasePropertyTagV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;

public class RESTEntityWrapperBuilder {
    private static final RESTWrapperCache wrapperCache = new RESTWrapperCache();
    private static final String GENERIC_ERROR = "Failed to create a Wrapper instance as there is no wrapper available for the Entity.";

    private RESTProviderFactory providerFactory;
    private RESTBaseElementV1<?> entity;
    private boolean isRevision = false;
    private boolean isNewEntity = false;
    private Class<? extends BaseWrapper<?>> wrapperInterface;
    private RESTBaseEntityV1<?, ?, ?> parent;
    private Collection<String> expandedMethods;

    public static RESTEntityWrapperBuilder newBuilder() {
        return new RESTEntityWrapperBuilder();
    }

    protected RESTEntityWrapperBuilder() {
    }

    public RESTEntityWrapperBuilder providerFactory(final RESTProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
        return this;
    }

    public RESTEntityWrapperBuilder entity(final RESTBaseElementV1<?> entity) {
        this.entity = entity;
        return this;
    }

    public RESTEntityWrapperBuilder isRevision() {
        isRevision = true;
        return this;
    }

    public RESTEntityWrapperBuilder isRevision(boolean isRevision) {
        this.isRevision = isRevision;
        return this;
    }

    public RESTEntityWrapperBuilder newEntity() {
        isNewEntity = true;
        return this;
    }

    public <T extends BaseWrapper<T>> RESTEntityWrapperBuilder wrapperInterface(final Class<T> wrapperClass) {
        this.wrapperInterface = wrapperClass;
        return this;
    }

    public RESTEntityWrapperBuilder parent(final RESTBaseEntityV1<?, ?, ?> parent) {
        this.parent = parent;
        return this;
    }

    public RESTEntityWrapperBuilder expandedMethods(final Collection<String> methodNames) {
        expandedMethods = methodNames;
        return this;
    }

    public <T extends BaseWrapper<T>> T build() {
        if (entity == null) {
            return null;
        }

        // Make sure we have a provider factory
        if (providerFactory == null) {
            throw new IllegalStateException("The Provider Factory has not been registered.");
        }

        final RESTBaseElementV1<?> unwrappedEntity = getEntity(entity);

        // Create the key
        final RESTWrapperKey key = new RESTWrapperKey(unwrappedEntity);

        // Check to see if a wrapper has already been cached for the key
        final RESTBaseWrapper cachedWrapper = wrapperCache.get(key);
        if (cachedWrapper != null) {
            return (T) cachedWrapper;
        }

        final RESTBaseWrapper wrapper;

        if (entity instanceof RESTServerSettingsV1) {
            // SERVER SETTINGS
            wrapper = new RESTServerSettingsV1Wrapper(providerFactory, (RESTServerSettingsV1) unwrappedEntity);
        } else if (entity instanceof RESTServerEntitiesV1) {
            // SERVER ENTITIES
            wrapper = new RESTServerEntitiesV1Wrapper(providerFactory, (RESTServerEntitiesV1) unwrappedEntity);
        } else if (entity instanceof RESTServerUndefinedSettingV1) {
            // UNDEFINED SERVER SETTINGS
            wrapper = new RESTServerUndefinedSettingV1Wrapper(providerFactory, (RESTServerUndefinedSettingV1) unwrappedEntity);
        } else if (entity instanceof RESTServerUndefinedEntityV1) {
            // UNDEFINED SERVER SETTINGS
            wrapper = new RESTServerUndefinedEntityV1Wrapper(providerFactory, (RESTServerUndefinedEntityV1) unwrappedEntity);
        } else if (entity instanceof RESTTopicV1) {
            // TOPIC
            wrapper = new RESTTopicV1Wrapper(providerFactory, (RESTTopicV1) unwrappedEntity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTTopicSourceUrlV1) {
            // TOPIC SOURCE URL
            wrapper = getTopicSourceUrlWrapper((RESTTopicSourceUrlV1) unwrappedEntity);
        } else if (entity instanceof RESTTranslatedTopicV1) {
            // TRANSLATED TOPIC
            wrapper = new RESTTranslatedTopicV1Wrapper(providerFactory, (RESTTranslatedTopicV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTTranslatedTopicStringV1) {
            // TRANSLATED TOPIC STRING
            wrapper = getTranslatedTopicStringWrapper((RESTTranslatedTopicStringV1) unwrappedEntity);
        } else if (entity instanceof RESTTagV1) {
            // TAG
            wrapper = new RESTTagV1Wrapper(providerFactory, (RESTTagV1) unwrappedEntity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTTagInCategoryV1) {
            // TAG TO CATEGORY
            wrapper = getTagInCategoryWrapper((RESTTagInCategoryV1) unwrappedEntity);
        } else if (entity instanceof RESTCategoryV1) {
            // CATEGORY
            wrapper = new RESTCategoryV1Wrapper(providerFactory, (RESTCategoryV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTCategoryInTagV1) {
            // CATEGORY TO TAG
            wrapper = getCategoryInTagWrapper((RESTCategoryInTagV1) unwrappedEntity);
        } else if (entity instanceof RESTPropertyTagV1) {
            // PROPERTY TAGS
            wrapper = new RESTPropertyTagV1Wrapper(providerFactory, (RESTPropertyTagV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTAssignedPropertyTagV1) {
            wrapper = getPropertyTagWrapper((RESTAssignedPropertyTagV1) unwrappedEntity);
        } else if (entity instanceof RESTPropertyTagInPropertyCategoryV1) {
            // PROPERTY TAG TO PROPERTY CATEGORY
            wrapper = getPropertyTagInPropertyCategoryWrapper((RESTPropertyTagInPropertyCategoryV1) unwrappedEntity);
        } else if (entity instanceof RESTBlobConstantV1) {
            // BLOB CONSTANT
            wrapper = new RESTBlobConstantV1Wrapper(providerFactory, (RESTBlobConstantV1) entity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTStringConstantV1) {
            // STRING CONSTANT
            wrapper = new RESTStringConstantV1Wrapper(providerFactory, (RESTStringConstantV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTFileV1) {
            // FILE
            wrapper = new RESTFileV1Wrapper(providerFactory, (RESTFileV1) unwrappedEntity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTLanguageFileV1) {
            // LANGUAGE FILE
            wrapper = getLanguageFileWrapper((RESTLanguageFileV1) unwrappedEntity);
        } else if (entity instanceof RESTImageV1) {
            // IMAGE
            wrapper = new RESTImageV1Wrapper(providerFactory, (RESTImageV1) unwrappedEntity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTLanguageImageV1) {
            // LANGUAGE IMAGE
            wrapper = getLanguageImageWrapper((RESTLanguageImageV1) unwrappedEntity);
        } else if (entity instanceof RESTUserV1) {
            // USER
            wrapper = new RESTUserV1Wrapper(providerFactory, (RESTUserV1) unwrappedEntity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTContentSpecV1) {
            // CONTENT SPEC
            wrapper = new RESTContentSpecV1Wrapper(providerFactory, (RESTContentSpecV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTTextContentSpecV1) {
            // TEXT CONTENT SPEC
            wrapper = new RESTTextContentSpecV1Wrapper(providerFactory, (RESTTextContentSpecV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTCSNodeV1) {
            // CONTENT SPEC NODE
            wrapper = new RESTCSNodeV1Wrapper(providerFactory, (RESTCSNodeV1) unwrappedEntity, isRevision, isNewEntity, expandedMethods);
        } else if (entity instanceof RESTCSRelatedNodeV1) {
            wrapper = new RESTCSRelatedNodeV1Wrapper(providerFactory, (RESTCSRelatedNodeV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTCSInfoNodeV1) {
            // CONTENT SPEC INFO NODE
            wrapper = new RESTCSInfoV1NodeWrapper(providerFactory, (RESTCSInfoNodeV1) unwrappedEntity, isRevision, (RESTCSNodeV1) parent,
                    isNewEntity, expandedMethods);
        } else if (entity instanceof RESTTranslatedContentSpecV1) {
            // TRANSLATED CONTENT SPEC
            wrapper = new RESTTranslatedContentSpecV1Wrapper(providerFactory, (RESTTranslatedContentSpecV1) unwrappedEntity, isRevision,
                    isNewEntity, expandedMethods);
        } else if (entity instanceof RESTTranslatedCSNodeV1) {
            // CONTENT SPEC TRANSLATED NODE
            wrapper = new RESTTranslatedCSNodeV1Wrapper(providerFactory, (RESTTranslatedCSNodeV1) unwrappedEntity, isRevision, isNewEntity,
                    expandedMethods);
        } else if (entity instanceof RESTTranslatedCSNodeStringV1) {
            // CONTENT SPEC TRANSLATED NODE STRING
            wrapper = getTranslatedCSNodeStringWrapper((RESTTranslatedCSNodeStringV1) unwrappedEntity);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }

        wrapperCache.put(key, wrapper);

        return (T) wrapper;
    }

    /**
     * Get the non proxied version of a REST Entity.
     *
     * @param entity The possible proxied entity.
     * @return The unwrapped/non-proxy entity, or the original entity if it wasn't a proxy.
     */
    @SuppressWarnings("rawtypes")
    protected RESTBaseElementV1<?> getEntity(final Object entity) {
        if (entity instanceof ProxyObject) {
            final MethodHandler handler = ((ProxyObject) entity).getHandler();
            if (handler instanceof RESTBaseEntityV1ProxyHandler) {
                return ((RESTBaseEntityV1ProxyHandler) handler).getEntity();
            }
        }

        return (RESTBaseElementV1<?>) entity;
    }

    protected RESTTopicSourceURLV1Wrapper getTopicSourceUrlWrapper(final RESTTopicSourceUrlV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get Topic Source URLs using V1 of the REST Interface.");
        } else if (parent instanceof RESTBaseTopicV1) {
            return new RESTTopicSourceURLV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTBaseTopicV1<?, ?, ?>) parent,
                    isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected RESTTranslatedTopicStringV1Wrapper getTranslatedTopicStringWrapper(final RESTTranslatedTopicStringV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get Translated Topic Strings using V1 of the REST Interface.");
        } else if (parent instanceof RESTTranslatedTopicV1) {
            return new RESTTranslatedTopicStringV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTTranslatedTopicV1) parent,
                    isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTTranslatedCSNodeStringV1Wrapper getTranslatedCSNodeStringWrapper(final RESTTranslatedCSNodeStringV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get Translated CSNode Strings using V1 of the REST Interface.");
        } else if (parent instanceof RESTTranslatedCSNodeV1) {
            return new RESTTranslatedCSNodeStringV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTTranslatedCSNodeV1) parent,
                    isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTLanguageFileV1Wrapper getLanguageFileWrapper(final RESTLanguageFileV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get Language Files using V1 of the REST Interface.");
        } else if (parent instanceof RESTFileV1) {
            return new RESTLanguageFileV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTFileV1) parent, isNewEntity,
                    expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTLanguageImageV1Wrapper getLanguageImageWrapper(final RESTLanguageImageV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get Language Images using V1 of the REST Interface.");
        } else if (parent instanceof RESTImageV1) {
            return new RESTLanguageImageV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTImageV1) parent, isNewEntity,
                    expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTCategoryInTagV1Wrapper getCategoryInTagWrapper(final RESTCategoryInTagV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get CategoryInTags using V1 of the REST Interface.");
        } else if (parent instanceof RESTBaseTagV1) {
            return new RESTCategoryInTagV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTBaseTagV1<?, ?, ?>) parent,
                    isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTTagInCategoryV1Wrapper getTagInCategoryWrapper(final RESTTagInCategoryV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException("A parent is needed to get TagInCategories using V1 of the REST Interface.");
        } else if (parent instanceof RESTBaseCategoryV1) {
            return new RESTTagInCategoryV1Wrapper(providerFactory, unwrappedEntity, isRevision, (RESTBaseCategoryV1<?, ?, ?>) parent,
                    isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTPropertyTagInPropertyCategoryV1Wrapper getPropertyTagInPropertyCategoryWrapper(
            final RESTPropertyTagInPropertyCategoryV1 unwrappedEntity) {
        if (parent == null) {
            throw new UnsupportedOperationException(
                    "A parent is needed to get PropertyTagInPropertyCategories using V1 of the REST Interface.");
        } else if (parent instanceof RESTPropertyCategoryV1) {
            return new RESTPropertyTagInPropertyCategoryV1Wrapper(providerFactory, unwrappedEntity, isRevision,
                    (RESTPropertyCategoryV1) parent, isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }

    protected RESTBasePropertyTagV1Wrapper<?, ?> getPropertyTagWrapper(final RESTAssignedPropertyTagV1 unwrappedEntity) {
        if (wrapperInterface == null) {
            throw new UnsupportedOperationException("The wrapper return class needs to be specified as the type can't be determined.");
        } else if (wrapperInterface == PropertyTagInTopicWrapper.class) {
            return new RESTPropertyTagInTopicV1Wrapper(providerFactory, (RESTAssignedPropertyTagV1) unwrappedEntity, isRevision,
                    (RESTBaseTopicV1<?, ?, ?>) parent, isNewEntity, expandedMethods);
        } else if (wrapperInterface == PropertyTagInTagWrapper.class) {
            return new RESTPropertyTagInTagV1Wrapper(providerFactory, (RESTAssignedPropertyTagV1) unwrappedEntity, isRevision,
                    (RESTBaseTagV1<?, ?, ?>) parent, isNewEntity, expandedMethods);
        } else if (wrapperInterface == PropertyTagInContentSpecWrapper.class) {
            return new RESTPropertyTagInContentSpecV1Wrapper(providerFactory, (RESTAssignedPropertyTagV1) unwrappedEntity,
                    isRevision, (RESTBaseContentSpecV1<?, ?, ?>) parent, isNewEntity, expandedMethods);
        } else {
            throw new IllegalArgumentException(GENERIC_ERROR);
        }
    }
}
