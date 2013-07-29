package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.DBTagWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTagProvider extends DBDataProvider implements TagProvider {
    private TagCache tagCache = new TagCache();

    protected DBTagProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
    }

    @Override
    public TagWrapper getTag(int id) {
        return getWrapperFactory().create(getEntity(Tag.class, id), false);
    }

    @Override
    public TagWrapper getTag(int id, Integer revision) {
        if (revision == null) {
            return getTag(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(Tag.class, id, revision), true);
        }
    }

    @Override
    public TagWrapper getTagByName(final String name) {
        // Find a matching tag
        Tag result = tagCache.get(name);
        if (result == null) {
            final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            final CriteriaQuery<Tag> tags = criteriaBuilder.createQuery(Tag.class);
            final Root<Tag> from = tags.from(Tag.class);
            tags.select(from);
            tags.where(criteriaBuilder.equal(from.get("tagName"), name));

            final List<Tag> tagsResult = executeQuery(tags);
            if (tagsResult != null && !tagsResult.isEmpty()) {
                result = tagsResult.get(0);
            }
        }

        // Return the result if one exists
        if (result != null) {
            tagCache.put(result);

            return getWrapperFactory().create(result, false, TagWrapper.class);
        } else {
            return null;
        }
    }

    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getTagCategories(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            final CollectionWrapper<CategoryInTagWrapper> collection = getWrapperFactory().createCollection(
                    tag.unwrap().getTagToCategories(), TagToCategory.class, revision != null, CategoryInTagWrapper.class);
            return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagChildTags(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(tag.unwrap().getChildTags(), Tag.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagParentTags(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(tag.unwrap().getParentTags(), Tag.class, revision != null);
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> getTagProperties(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            final CollectionWrapper<PropertyTagInTagWrapper> collection = getWrapperFactory().createCollection(
                    tag.unwrap().getTagToPropertyTags(), TagToPropertyTag.class, revision != null);
            return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagRevisions(int id, Integer revision) {
        final List<Tag> revisions = getRevisionList(Tag.class, id);
        return getWrapperFactory().createCollection(revisions, Tag.class, revision != null);
    }

    @Override
    public TagWrapper newTag() {
        return getWrapperFactory().create(new Tag(), false);
    }

    @Override
    public TagInCategoryWrapper newTagInCategory() {
        return getWrapperFactory().create(new TagToCategory(), false);
    }

    @Override
    public CollectionWrapper<TagWrapper> newTagCollection() {
        return getWrapperFactory().createCollection(new ArrayList<Tag>(), Tag.class, false);
    }

    @Override
    public CollectionWrapper<TagInCategoryWrapper> newTagInCategoryCollection() {
        return getWrapperFactory().createCollection(new ArrayList<TagToCategory>(), TagToCategory.class, false, TagInCategoryWrapper.class);
    }
}

class TagCache {
    private static final Long DEFAULT_MAX_CACHE_SIZE = 100L;
    private static final Long DEFAULT_CACHE_TIMEOUT = 30L;

    private final Cache<String, Tag> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(DEFAULT_CACHE_TIMEOUT, TimeUnit.SECONDS)
            .maximumSize(DEFAULT_MAX_CACHE_SIZE)
            .build();

    public boolean containsKey(final String key) {
        return cache.getIfPresent(key) != null;
    }

    public Tag get(final String key) {
        return cache.getIfPresent(key);
    }

    public void put(final List<Tag> values) {
        for (final Tag tag : values) {
            put(tag);
        }
    }

    public void put(final Tag value) {
        put(value.getTagName(), value);
    }

    public void put(final String key, final Tag value) {
        cache.put(key, value);
    }
}
