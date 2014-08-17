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

package org.jboss.pressgang.ccms.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.File;
import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;
import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TopicSourceUrl;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;
import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.model.TranslationServer;
import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.model.config.EntitiesConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedEntity;
import org.jboss.pressgang.ccms.model.config.UndefinedSetting;
import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslationDetail;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBBlobConstantCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSInfoNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSRelatedNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSTranslationDetailCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryInTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBContentSpecCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBContentSpecToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBFileCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBImageCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageFileCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageImageCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLocaleCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBPropertyTagInPropertyCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBServerUndefinedEntityCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBServerUndefinedSettingCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBStringConstantCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagInCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicSourceURLCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTopicToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedCSNodeStringCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedTopicDataCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslatedTopicStringCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslationServerCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTranslationServerExtendedCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBUserCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.structures.DBWrapperCache;
import org.jboss.pressgang.ccms.wrapper.structures.DBWrapperKey;

public class DBWrapperFactory {
    private final DBWrapperCache wrapperCache = new DBWrapperCache();
    private DBProviderFactory providerFactory;

    public DBWrapperFactory() {
    }

    public DBWrapperFactory(final DBProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    protected DBProviderFactory getProviderFactory() {
        if (providerFactory == null) {
            throw new IllegalStateException("The Provider Factory has not been registered.");
        }
        return providerFactory;
    }

    public void setProviderFactory(final DBProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    /**
     * Create a wrapper around a specific entity.
     *
     * @param entity     The entity to be wrapped.
     * @param isRevision Whether the entity is a revision or not.
     * @param <T>        The wrapper class that is returned.
     * @return The Wrapper around the entity.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseWrapper<T>> T create(final Object entity, boolean isRevision) {
        if (entity == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(entity);

        // Check to see if a wrapper has already been cached for the key
        final BaseWrapper cachedWrapper = wrapperCache.get(key);
        if (cachedWrapper != null) {
            return (T) cachedWrapper;
        }

        final DBBaseWrapper wrapper;

        if (entity instanceof ApplicationConfig) {
            // SERVER SETTINGS
            wrapper = new DBServerSettingsWrapper(getProviderFactory(), (ApplicationConfig) entity);
        } else if (entity instanceof EntitiesConfig) {
            // SERVER ENTITIES
            wrapper = new DBServerEntitiesWrapper(getProviderFactory(), (EntitiesConfig) entity);
        } else if (entity instanceof UndefinedEntity) {
            // UNDEFINED ENTITY
            wrapper = new DBServerUndefinedEntityWrapper(getProviderFactory(), (UndefinedEntity) entity);
        } else if (entity instanceof UndefinedSetting) {
            // UNDEFINED SETTING
            wrapper = new DBServerUndefinedSettingWrapper(getProviderFactory(), (UndefinedSetting) entity);
        } else if (entity instanceof Locale) {
            // Locale
            wrapper = new DBLocaleWrapper(getProviderFactory(), (Locale) entity);
        } else if (entity instanceof TranslationServer) {
            // Translation server
            throw new UnsupportedOperationException("A return class needs to be specified for TranslationServer entities.");
        } else if (entity instanceof Topic) {
            // TOPIC
            wrapper = new DBTopicWrapper(getProviderFactory(), (Topic) entity, isRevision);
        } else if (entity instanceof TopicSourceUrl) {
            // TOPIC SOURCE URL
            wrapper = new DBTopicSourceURLWrapper(getProviderFactory(), (TopicSourceUrl) entity, isRevision);
        } else if (entity instanceof TranslatedTopicData) {
            // TRANSLATED TOPIC
            wrapper = new DBTranslatedTopicDataWrapper(getProviderFactory(), (TranslatedTopicData) entity, isRevision);
        } else if (entity instanceof TranslatedTopicString) {
            // TRANSLATED TOPIC STRING
            wrapper = new DBTranslatedTopicStringWrapper(getProviderFactory(), (TranslatedTopicString) entity, isRevision);
        } else if (entity instanceof Tag) {
            // TAG
            wrapper = new DBTagWrapper(getProviderFactory(), (Tag) entity, isRevision);
        } else if (entity instanceof TagToCategory) {
            throw new UnsupportedOperationException("A return class needs to be specified for TagToCategory entities.");
        } else if (entity instanceof Category) {
            // CATEGORY
            wrapper = new DBCategoryWrapper(getProviderFactory(), (Category) entity, isRevision);
        } else if (entity instanceof PropertyTag) {
            // PROPERTY TAGS
            wrapper = new DBPropertyTagWrapper(getProviderFactory(), (PropertyTag) entity, isRevision);
        } else if (entity instanceof TopicToPropertyTag) {
            wrapper = new DBTopicToPropertyTagWrapper(getProviderFactory(), (TopicToPropertyTag) entity, isRevision);
        } else if (entity instanceof TagToPropertyTag) {
            wrapper = new DBTagToPropertyTagWrapper(getProviderFactory(), (TagToPropertyTag) entity, isRevision);
        } else if (entity instanceof ContentSpecToPropertyTag) {
            wrapper = new DBContentSpecToPropertyTagWrapper(getProviderFactory(), (ContentSpecToPropertyTag) entity, isRevision);
        } else if (entity instanceof PropertyTagToPropertyTagCategory) {
            wrapper = new DBPropertyTagToPropertyTagCategoryWrapper(getProviderFactory(), (PropertyTagToPropertyTagCategory) entity,
                    isRevision);
        } else if (entity instanceof BlobConstants) {
            // BLOB CONSTANT
            wrapper = new DBBlobConstantWrapper(getProviderFactory(), (BlobConstants) entity, isRevision);
        } else if (entity instanceof StringConstants) {
            // STRING CONSTANT
            wrapper = new DBStringConstantWrapper(getProviderFactory(), (StringConstants) entity, isRevision);
        } else if (entity instanceof File) {
            // FILE
            wrapper = new DBFileWrapper(getProviderFactory(), (File) entity, isRevision);
        } else if (entity instanceof LanguageFile) {
            // LANGUAGE FILE
            wrapper = new DBLanguageFileWrapper(getProviderFactory(), (LanguageFile) entity, isRevision);
        } else if (entity instanceof ImageFile) {
            // IMAGE
            wrapper = new DBImageWrapper(getProviderFactory(), (ImageFile) entity, isRevision);
        } else if (entity instanceof LanguageImage) {
            // LANGUAGE IMAGE
            wrapper = new DBLanguageImageWrapper(getProviderFactory(), (LanguageImage) entity, isRevision);
        } else if (entity instanceof User) {
            // USER
            wrapper = new DBUserWrapper(getProviderFactory(), (User) entity, isRevision);
        } else if (entity instanceof ContentSpec) {
            // CONTENT SPEC
            throw new UnsupportedOperationException("A return class needs to be specified for ContentSpec entities.");
        } else if (entity instanceof CSNode) {
            // CONTENT SPEC NODE
            wrapper = new DBCSNodeWrapper(getProviderFactory(), (CSNode) entity, isRevision);
        } else if (entity instanceof CSNodeToCSNode) {
            wrapper = new DBCSRelatedNodeWrapper(getProviderFactory(), (CSNodeToCSNode) entity, isRevision);
        } else if (entity instanceof CSInfoNode) {
            // CONTENT SPEC INFO NODE
            wrapper = new DBCSInfoNodeWrapper(getProviderFactory(), (CSInfoNode) entity, isRevision);
        } else if (entity instanceof TranslatedCSNode) {
            // CONTENT SPEC TRANSLATED NODE
            wrapper = new DBTranslatedCSNodeWrapper(getProviderFactory(), (TranslatedCSNode) entity, isRevision);
        } else if (entity instanceof TranslatedCSNodeString) {
            // CONTENT SPEC TRANSLATED NODE STRING
            wrapper = new DBTranslatedCSNodeStringWrapper(getProviderFactory(), (TranslatedCSNodeString) entity, isRevision);
        } else if (entity instanceof CSTranslationDetail) {
            // CONTENT SPEC TRANSLATION DETAIL
            wrapper = new DBCSTranslationDetailWrapper(getProviderFactory(), (CSTranslationDetail) entity);
        } else {
            throw new IllegalArgumentException("Failed to create a Wrapper instance as there is no wrapper available for the Entity.");
        }

        // Add the wrapper to the cache
        wrapperCache.put(key, wrapper);

        return (T) wrapper;
    }

    /**
     * Create a wrapper around a collection of entities.
     *
     * @param collection           The collection to be wrapped.
     * @param entityClass          The class of the entity that the collection contains.
     * @param isRevisionCollection Whether or not the collection is a collection of revision entities.
     * @param <T>                  The wrapper class that is returned.
     * @return The Wrapper around the collection of entities.
     */
    public <T extends BaseWrapper<T>> CollectionWrapper<T> createCollection(final Object collection, final Class<?> entityClass,
            boolean isRevisionCollection) {
        return createCollection((Collection) collection, entityClass, isRevisionCollection);
    }

    /**
     * Create a wrapper around a collection of entities.
     *
     * @param collection           The collection to be wrapped.
     * @param entityClass          The class of the entity that the collection contains.
     * @param isRevisionCollection Whether or not the collection is a collection of revision entities.
     * @param <T>                  The wrapper class that is returned.
     * @return The Wrapper around the collection of entities.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BaseWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
            boolean isRevisionCollection) {
        if (collection == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(collection, entityClass);

        // Check to see if a wrapper has already been cached for the key
        final DBCollectionWrapper cachedWrapper = wrapperCache.getCollection(key);
        if (cachedWrapper != null) {
            return cachedWrapper;
        }

        final DBCollectionWrapper wrapper;

        if (entityClass == Locale.class) {
            // LOCALE
            wrapper = new DBLocaleCollectionWrapper(this, (Collection<Locale>) collection, isRevisionCollection);
        } else if (entityClass == TranslationServer.class) {
            // TRANSLATION SERVER
            throw new UnsupportedOperationException("A return class needs to be specified for TranslationServer entities.");
        } else if (entityClass == Topic.class) {
            // TOPIC
            wrapper = new DBTopicCollectionWrapper(this, (Collection<Topic>) collection, isRevisionCollection);
        } else if (entityClass == TopicSourceUrl.class) {
            // TOPIC SOURCE URL
            wrapper = new DBTopicSourceURLCollectionWrapper(this, (Collection<TopicSourceUrl>) collection, isRevisionCollection);
        } else if (entityClass == TranslatedTopicData.class) {
            // TRANSLATED TOPIC
            wrapper = new DBTranslatedTopicDataCollectionWrapper(this, (Collection<TranslatedTopicData>) collection, isRevisionCollection);
        } else if (entityClass == TranslatedTopicString.class) {
            // TRANSLATED TOPIC STRING
            wrapper = new DBTranslatedTopicStringCollectionWrapper(this, (Collection<TranslatedTopicString>) collection,
                    isRevisionCollection);
        } else if (entityClass == Tag.class) {
            // TAG
            wrapper = new DBTagCollectionWrapper(this, (Collection<Tag>) collection, isRevisionCollection);
        } else if (entityClass == Category.class) {
            // CATEGORY
            wrapper = new DBCategoryCollectionWrapper(this, (Collection<Category>) collection, isRevisionCollection);
        } else if (entityClass == TagToCategory.class) {
            throw new UnsupportedOperationException("A return class needs to be specified for TagToCategory entities.");
        } else if (entityClass == PropertyTagToPropertyTagCategory.class) {
            // PROPERTY TAGS
            wrapper = new DBPropertyTagInPropertyCategoryCollectionWrapper(this, (Collection<PropertyTagToPropertyTagCategory>) collection,
                    isRevisionCollection);
        } else if (entityClass == PropertyTag.class) {
            wrapper = new DBPropertyTagCollectionWrapper(this, (Collection<PropertyTag>) collection, isRevisionCollection);
        } else if (entityClass == TopicToPropertyTag.class) {
            wrapper = new DBTopicToPropertyTagCollectionWrapper(this, (Collection<TopicToPropertyTag>) collection, isRevisionCollection);
        } else if (entityClass == TagToPropertyTag.class) {
            wrapper = new DBTagToPropertyTagCollectionWrapper(this, (Collection<TagToPropertyTag>) collection, isRevisionCollection);
        } else if (entityClass == ContentSpecToPropertyTag.class) {
            wrapper = new DBContentSpecToPropertyTagCollectionWrapper(this, (Collection<ContentSpecToPropertyTag>) collection,
                    isRevisionCollection);
        } else if (entityClass == BlobConstants.class) {
            // BLOB CONSTANT
            wrapper = new DBBlobConstantCollectionWrapper(this, (Collection<BlobConstants>) collection, isRevisionCollection);
        } else if (entityClass == StringConstants.class) {
            // STRING CONSTANT
            wrapper = new DBStringConstantCollectionWrapper(this, (Collection<StringConstants>) collection, isRevisionCollection);
        } else if (entityClass == File.class) {
            // FILE
            wrapper = new DBFileCollectionWrapper(this, (Collection<File>) collection, isRevisionCollection);
        } else if (entityClass == LanguageFile.class) {
            // LANGUAGE IMAGE
            wrapper = new DBLanguageFileCollectionWrapper(this, (Collection<LanguageFile>) collection, isRevisionCollection);
        } else if (entityClass == ImageFile.class) {
            // IMAGE
            wrapper = new DBImageCollectionWrapper(this, (Collection<ImageFile>) collection, isRevisionCollection);
        } else if (entityClass == LanguageImage.class) {
            // LANGUAGE IMAGE
            wrapper = new DBLanguageImageCollectionWrapper(this, (Collection<LanguageImage>) collection, isRevisionCollection);
        } else if (entityClass == User.class) {
            // USER
            wrapper = new DBUserCollectionWrapper(this, (Collection<User>) collection, isRevisionCollection);
        } else if (entityClass == ContentSpec.class) {
            // CONTENT SPEC
            wrapper = new DBContentSpecCollectionWrapper(this, (Collection<ContentSpec>) collection, isRevisionCollection);
        } else if (entityClass == CSNode.class) {
            // CONTENT SPEC NODE
            wrapper = new DBCSNodeCollectionWrapper(this, (Collection<CSNode>) collection, isRevisionCollection);
        } else if (entityClass == CSNodeToCSNode.class) {
            wrapper = new DBCSRelatedNodeCollectionWrapper(this, (Collection<CSNodeToCSNode>) collection, isRevisionCollection);
        } else if (entityClass == CSNode.class) {
            // CONTENT SPEC INFO NODE
            wrapper = new DBCSInfoNodeCollectionWrapper(this, (Collection<CSInfoNode>) collection, isRevisionCollection);
        } else if (entityClass == TranslatedCSNode.class) {
            // CONTENT SPEC TRANSLATED NODE
            wrapper = new DBTranslatedCSNodeCollectionWrapper(this, (Collection<TranslatedCSNode>) collection, isRevisionCollection);
        } else if (entityClass == TranslatedCSNodeString.class) {
            // CONTENT SPEC TRANSLATED NODE STRING
            wrapper = new DBTranslatedCSNodeStringCollectionWrapper(this, (Collection<TranslatedCSNodeString>) collection,
                    isRevisionCollection);
        } else if (entityClass == CSTranslationDetail.class) {
            // CONTENT SPEC TRANSLATION DETAIL
            wrapper = new DBCSTranslationDetailCollectionWrapper(this, (Collection<CSTranslationDetail>) collection,
                    isRevisionCollection);
        } else if (entityClass == UndefinedEntity.class) {
            // UNDEFINED APPLICATION ENTITY
            wrapper = new DBServerUndefinedEntityCollectionWrapper(this, (Collection<UndefinedEntity>) collection,
                    isRevisionCollection);
        } else if (entityClass == UndefinedSetting.class) {
            // UNDEFINED APPLICATION SETTING
            wrapper = new DBServerUndefinedSettingCollectionWrapper(this, (Collection<UndefinedSetting>) collection,
                    isRevisionCollection);
        } else {
            throw new IllegalArgumentException(
                    "Failed to create a Collection Wrapper instance as there is no wrapper available for the Collection.");
        }

        // Add the wrapper to the cache
        wrapperCache.putCollection(key, wrapper);

        return wrapper;
    }

    /**
     * Create a wrapper around a specific entity.
     *
     * @param entity       The entity to be wrapped.
     * @param isRevision   Whether the entity is a revision or not.
     * @param wrapperClass The return wrapper class, incase the entities wrapper can't be determined by it's class.
     * @param <T>          The wrapper class that is returned.
     * @return The Wrapper around the entity.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BaseWrapper<T>> T create(final Object entity, boolean isRevision, final Class<T> wrapperClass) {
        if (entity == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(entity, wrapperClass);

        // Check to see if a wrapper has already been cached for the key
        final DBBaseWrapper cachedWrapper = wrapperCache.get(key);
        if (cachedWrapper != null) {
            return (T) cachedWrapper;
        }

        final DBBaseWrapper wrapper;
        boolean local = true;

        if (entity instanceof TagToCategory && wrapperClass == TagInCategoryWrapper.class) {
            wrapper = new DBTagInCategoryWrapper(getProviderFactory(), (TagToCategory) entity, isRevision);
        } else if (entity instanceof TagToCategory && wrapperClass == CategoryInTagWrapper.class) {
            wrapper = new DBCategoryInTagWrapper(getProviderFactory(), (TagToCategory) entity, isRevision);
        } else if (entity instanceof ContentSpec && wrapperClass == ContentSpecWrapper.class) {
            // CONTENT SPEC
            wrapper = new DBContentSpecWrapper(getProviderFactory(), (ContentSpec) entity, isRevision);
        } else if (entity instanceof ContentSpec && wrapperClass == TextContentSpecWrapper.class) {
            // TEXT CONTENT SPEC
            wrapper = new DBTextContentSpecWrapper(getProviderFactory(), (ContentSpec) entity, isRevision);
        } else if (entity instanceof TranslationServer && wrapperClass == TranslationServerWrapper.class) {
            // TRANSLATION SERVER
            wrapper = new DBTranslationServerWrapper(getProviderFactory(), (TranslationServer) entity);
        } else if (entity instanceof TranslationServer && wrapperClass == TranslationServerWrapper.class) {
            // TRANSLATION SERVER EXTENDED
            wrapper = new DBTranslationServerWrapper(getProviderFactory(), (TranslationServer) entity);
        } else {
            wrapper = create(entity, isRevision);
            local = false;
        }

        // Add the wrapper to the cache if it was found in this method
        if (local) {
            wrapperCache.put(key, wrapper);
        }

        return (T) wrapper;
    }

    /**
     * Create a wrapper around a collection of entities.
     *
     * @param collection           The collection to be wrapped.
     * @param entityClass          The class of the entity that the collection contains.
     * @param isRevisionCollection Whether or not the collection is a collection of revision entities.
     * @param wrapperClass         The return wrapper class, incase the entities wrapper can't be determined by it's class.
     * @param <T>                  The wrapper class that is returned.
     * @return The Wrapper around the collection of entities.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BaseWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
            boolean isRevisionCollection, final Class<T> wrapperClass) {
        if (collection == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(collection, entityClass, wrapperClass);

        // Check to see if a wrapper has already been cached for the key
        final DBCollectionWrapper cachedWrapper = wrapperCache.getCollection(key);
        if (cachedWrapper != null) {
            return cachedWrapper;
        }

        final DBCollectionWrapper wrapper;
        boolean local = true;

        if (entityClass == TagToCategory.class && wrapperClass == TagInCategoryWrapper.class) {
            wrapper = new DBTagInCategoryCollectionWrapper(this, (Collection<TagToCategory>) collection, isRevisionCollection);
        } else if (entityClass == TagToCategory.class && wrapperClass == CategoryInTagWrapper.class) {
            wrapper = new DBCategoryInTagCollectionWrapper(this, (Collection<TagToCategory>) collection, isRevisionCollection);
        } else if (entityClass == TranslationServer.class && wrapperClass == TranslationServerWrapper.class) {
            // TRANSLATION SERVER
            wrapper = new DBTranslationServerCollectionWrapper(this, (Collection<TranslationServer>) collection, isRevisionCollection);
        } else if (entityClass == TranslationServer.class && wrapperClass == TranslationServerExtendedWrapper.class) {
            // TRANSLATION SERVER EXTENDED
            wrapper = new DBTranslationServerExtendedCollectionWrapper(this, (Collection<TranslationServer>) collection,
                    isRevisionCollection);
        } else {
            wrapper = (DBCollectionWrapper) createCollection(collection, entityClass, isRevisionCollection);
            local = false;
        }

        // Add the wrapper to the cache if it was found in this method
        if (local) {
            wrapperCache.putCollection(key, wrapper);
        }

        return wrapper;
    }

    /**
     * Create a wrapper around a collection of entities.
     *
     * @param collection           The collection to be wrapped.
     * @param entityClass          The class of the entity that the collection contains.
     * @param isRevisionCollection Whether or not the collection is a collection of revision entities.
     * @param handler
     * @param <T>                  The wrapper class that is returned.
     * @return The Wrapper around the collection of entities.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BaseWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
            boolean isRevisionCollection, final DBCollectionHandler<U> handler) {
        if (collection == null) {
            return null;
        }

        final DBCollectionWrapper wrapper = (DBCollectionWrapper) createCollection(collection, entityClass, isRevisionCollection);
        wrapper.setHandler(handler);

        return wrapper;
    }

    /**
     * Create a wrapper around a collection of entities.
     *
     * @param collection           The collection to be wrapped.
     * @param entityClass          The class of the entity that the collection contains.
     * @param isRevisionCollection Whether or not the collection is a collection of revision entities.
     * @param wrapperClass         The return wrapper class, incase the entities wrapper can't be determined by it's class.
     * @param handler
     * @param <T>                  The wrapper class that is returned.
     * @param <U>
     * @return The Wrapper around the collection of entities.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends BaseWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
            boolean isRevisionCollection, final Class<T> wrapperClass, final DBCollectionHandler<U> handler) {
        if (collection == null) {
            return null;
        }

        final DBCollectionWrapper wrapper = (DBCollectionWrapper) createCollection(collection, entityClass, isRevisionCollection,
                wrapperClass);
        wrapper.setHandler(handler);

        return wrapper;
    }

    /**
     * Create a list of wrapped entities.
     *
     * @param entities       The collection of entities to wrap.
     * @param isRevisionList Whether or not the collection is a collection of revision entities.
     * @param <T>            The wrapper class that is returned.
     * @return An ArrayList of wrapped entities.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseWrapper<T>> List<T> createList(final Collection<?> entities, boolean isRevisionList) {
        final List<T> retValue = new ArrayList<T>();
        for (final Object object : entities) {
            retValue.add((T) create(object, isRevisionList));
        }

        return retValue;
    }

    /**
     * Create a list of wrapped entities.
     *
     * @param entities       The collection of entities to wrap.
     * @param isRevisionList Whether or not the collection is a collection of revision entities.
     * @param wrapperClass   The return wrapper class, incase the entities wrapper can't be determined by it's class.
     * @param <T>            The wrapper class that is returned.
     * @return An ArrayList of wrapped entities.
     */
    public <T extends BaseWrapper<T>, U> List<T> createList(final Collection<U> entities, boolean isRevisionList,
            final Class<T> wrapperClass) {
        final List<T> retValue = new ArrayList<T>();
        for (final Object object : entities) {
            retValue.add((T) create(object, isRevisionList, wrapperClass));
        }

        return retValue;
    }
}
