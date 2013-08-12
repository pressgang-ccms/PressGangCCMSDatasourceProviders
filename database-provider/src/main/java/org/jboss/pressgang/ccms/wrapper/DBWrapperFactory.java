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
import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.provider.DataProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBBlobConstantCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCSRelatedNodeCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryInTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBContentSpecCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBContentSpecToPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBFileCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBImageCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageFileCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageImageCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBPropertyTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBPropertyTagInPropertyCategoryCollectionWrapper;
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
import org.jboss.pressgang.ccms.wrapper.collection.DBUserCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.structures.DBWrapperCache;
import org.jboss.pressgang.ccms.wrapper.structures.DBWrapperKey;

public class DBWrapperFactory extends WrapperFactory {
    private final DBWrapperCache wrapperCache = new DBWrapperCache();

    public DBWrapperFactory() {
    }

    public DBWrapperFactory(final DataProviderFactory providerFactory) {
        super(providerFactory);
    }

    @Override
    protected DBProviderFactory getProviderFactory() {
        return (DBProviderFactory) super.getProviderFactory();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EntityWrapper<T>> T create(final Object entity, boolean isRevision) {
        if (entity == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(entity);

        // Check to see if a wrapper has already been cached for the key
        final DBBaseWrapper cachedWrapper = wrapperCache.get(key);
        if (cachedWrapper != null) {
            return (T) cachedWrapper;
        }

        final DBBaseWrapper wrapper;

        if (entity instanceof Topic) {
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
            wrapper = new DBContentSpecWrapper(getProviderFactory(), (ContentSpec) entity, isRevision);
        } else if (entity instanceof CSNode) {
            // CONTENT SPEC NODE
            wrapper = new DBCSNodeWrapper(getProviderFactory(), (CSNode) entity, isRevision);
        } else if (entity instanceof CSNodeToCSNode) {
            wrapper = new DBCSRelatedNodeWrapper(getProviderFactory(), (CSNodeToCSNode) entity, isRevision);
        } else if (entity instanceof TranslatedCSNode) {
            // CONTENT SPEC TRANSLATED NODE
            wrapper = new DBTranslatedCSNodeWrapper(getProviderFactory(), (TranslatedCSNode) entity, isRevision);
        } else if (entity instanceof TranslatedCSNodeString) {
            // CONTENT SPEC TRANSLATED NODE STRING
            wrapper = new DBTranslatedCSNodeStringWrapper(getProviderFactory(), (TranslatedCSNodeString) entity, isRevision);
        } else {
            throw new IllegalArgumentException("Failed to create a Wrapper instance as there is no wrapper available for the Entity.");
        }

        // Add the wrapper to the cache
        wrapperCache.put(key, wrapper);

        return (T) wrapper;
    }

    @Override
    public <T extends EntityWrapper<T>> CollectionWrapper<T> createCollection(final Object collection, final Class<?> entityClass,
            boolean isRevisionCollection) {
        return createCollection((Collection) collection, entityClass, isRevisionCollection);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends EntityWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
            boolean isRevisionCollection) {
        if (collection == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(collection);

        // Check to see if a wrapper has already been cached for the key
        final DBCollectionWrapper cachedWrapper = wrapperCache.getCollection(key);
        if (cachedWrapper != null) {
            return cachedWrapper;
        }

        final DBCollectionWrapper wrapper;

        if (entityClass == Topic.class) {
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
        } else if (entityClass == TranslatedCSNode.class) {
            // CONTENT SPEC TRANSLATED NODE
            wrapper = new DBTranslatedCSNodeCollectionWrapper(this, (Collection<TranslatedCSNode>) collection, isRevisionCollection);
        } else if (entityClass == TranslatedCSNodeString.class) {
            // CONTENT SPEC TRANSLATED NODE STRING
            wrapper = new DBTranslatedCSNodeStringCollectionWrapper(this, (Collection<TranslatedCSNodeString>) collection,
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
    public <T extends EntityWrapper<T>> T create(final Object entity, boolean isRevision, final Class<T> wrapperClass) {
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

        if (entity instanceof TagToCategory && wrapperClass == TagInCategoryWrapper.class) {
            wrapper = new DBTagInCategoryWrapper(getProviderFactory(), (TagToCategory) entity, isRevision);

            // Add the wrapper to the cache
            wrapperCache.put(key, wrapper);
        } else if (entity instanceof TagToCategory && wrapperClass == CategoryInTagWrapper.class) {
            wrapper = new DBCategoryInTagWrapper(getProviderFactory(), (TagToCategory) entity, isRevision);

            // Add the wrapper to the cache
            wrapperCache.put(key, wrapper);
        } else {
            wrapper = create(entity, isRevision);
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
    public <T extends EntityWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
            boolean isRevisionCollection, final Class<T> wrapperClass) {
        if (collection == null) {
            return null;
        }

        // Create the key
        final DBWrapperKey key = new DBWrapperKey(collection);

        // Check to see if a wrapper has already been cached for the key
        final DBCollectionWrapper cachedWrapper = wrapperCache.getCollection(key);
        if (cachedWrapper != null) {
            return cachedWrapper;
        }

        final DBCollectionWrapper wrapper;

        if (entityClass == TagToCategory.class && wrapperClass == TagInCategoryWrapper.class) {
            wrapper = new DBTagInCategoryCollectionWrapper(this, (Collection<TagToCategory>) collection, isRevisionCollection);

            // Add the wrapper to the cache
            wrapperCache.putCollection(key, wrapper);
        } else if (entityClass == TagToCategory.class && wrapperClass == CategoryInTagWrapper.class) {
            wrapper = new DBCategoryInTagCollectionWrapper(this, (Collection<TagToCategory>) collection, isRevisionCollection);

            // Add the wrapper to the cache
            wrapperCache.putCollection(key, wrapper);
        } else {
            wrapper = (DBCollectionWrapper) createCollection(collection, entityClass, isRevisionCollection);
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
    public <T extends EntityWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
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
    public <T extends EntityWrapper<T>, U> CollectionWrapper<T> createCollection(final Collection<U> collection, final Class<U> entityClass,
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
     * @param wrapperClass   The return wrapper class, incase the entities wrapper can't be determined by it's class.
     * @param <T>            The wrapper class that is returned.
     * @return An ArrayList of wrapped entities.
     */
    public <T extends EntityWrapper<T>, U> List<T> createList(final Collection<U> entities, boolean isRevisionList,
            final Class<T> wrapperClass) {
        final List<T> retValue = new ArrayList<T>();
        for (final Object object : entities) {
            retValue.add((T) create(object, isRevisionList, wrapperClass));
        }

        return retValue;
    }
}
