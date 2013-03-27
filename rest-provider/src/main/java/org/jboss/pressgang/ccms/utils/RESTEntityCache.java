package org.jboss.pressgang.ccms.utils;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTEntityCache {
    private static final String CACHE_MAX_SIZE_PROPERTY = "pressgang.rest.cache.maxSize";
    private static final String CACHE_TIMEOUT_PROPERTY = "pressgang.rest.cache.timeout";
    private static final Long DEFAULT_MAX_CACHE_SIZE = 2500L;
    private static final Long DEFAULT_CACHE_TIMEOUT = 10L;

    protected final Cache<String, RESTBaseEntityV1<?, ?, ?>> entityCache;

    public RESTEntityCache() {
        // Get the max size from the system property
        Long maxSize;
        try {
            maxSize = Long.parseLong(System.getProperty(CACHE_MAX_SIZE_PROPERTY, DEFAULT_MAX_CACHE_SIZE.toString()));
        } catch (NumberFormatException e) {
            maxSize = DEFAULT_MAX_CACHE_SIZE;
        }

        // Get the timeout from the system property
        Long timeout;
        try {
            timeout = Long.parseLong(System.getProperty(CACHE_TIMEOUT_PROPERTY, DEFAULT_CACHE_TIMEOUT.toString()));
        } catch (NumberFormatException e) {
            timeout = DEFAULT_CACHE_TIMEOUT;
        }

        entityCache = CacheBuilder.newBuilder()
                .expireAfterWrite(timeout, TimeUnit.MINUTES)
                .maximumSize(maxSize)
                .build();
    }

    /**
     * Add a collection of latest available entities to the cache.
     *
     * @param value The collection of entities to be added to the cache.
     */
    public void add(final RESTBaseCollectionV1<?, ?, ?> value) {
        add(value, false);
    }

    /**
     * Add a collection of entities to the cache.
     *
     * @param value       The collection of entities to be added to the cache.
     * @param isRevisions Whether or not the collection is a collection of revisions or not.
     */
    public void add(final RESTBaseCollectionV1<?, ?, ?> value, final boolean isRevisions) {
        if (value != null && value.getItems() != null) {
            for (final RESTBaseCollectionItemV1<?, ?, ?> item : value.getItems()) {
                if (item.getItem() != null) {
                    add(item.getItem(), isRevisions);
                }
            }
        }
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final Number id) {
        return containsKeyValue(clazz, id.toString(), null);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final String id) {
        return containsKeyValue(clazz, id, null);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final Number id, final Number revision) {
        return containsKeyValue(clazz, id.toString(), revision);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final String id, final Number revision) {
        final String key = buildKey(clazz, id, revision);
        return entityCache.asMap().containsKey(key);
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value) {
        add(value, false);
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value, final Number id, final Number revision) {
        add(value, id.toString(), revision);
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value, final Number id, final boolean isRevision) {
        add(value, id.toString(), isRevision);
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value, final boolean isRevision) {
        // Add a null revision to specify it's the latest, if the entity isn't a revision
        if (!isRevision) {
            add(value, null);
        } else {
            add(value, value.getRevision());
        }
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value, final String id, final boolean isRevision) {
        // Add a null revision to specify it's the latest, if the entity isn't a revision
        if (!isRevision) {
            add(value, id, null);
        } else {
            add(value, id, value.getRevision());
        }
    }

    /**
     * Add a Entity to the cache, where the id should be extracted from the entity.
     *
     * @param value    The entity to be added to the cache.
     * @param revision The revision of the entity to be cached.
     */
    public void add(final RESTBaseEntityV1<?, ?, ?> value, final Number revision) {
        add(value, value.getId(), revision);
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value, final String id, final Number revision) {
        // Add the entity
        final String key = buildKey(value, id, revision);
        entityCache.put(key, value);

        // Add any revisions to the cache
        if (value.getRevisions() != null) {
            add(value.getRevisions(), true);
        }
    }

    public <T extends RESTBaseEntityV1<T, ?, ?>> T get(final Class<T> clazz, final Number id) {
        return get(clazz, id.toString(), null);
    }

    public <T extends RESTBaseEntityV1<T, ?, ?>> T get(final Class<T> clazz, final String id) {
        return get(clazz, id, null);
    }

    public <T extends RESTBaseEntityV1<T, ?, ?>> T get(final Class<T> clazz, final Number id, final Number revision) {
        return get(clazz, id.toString(), revision);
    }

    public <T extends RESTBaseEntityV1<T, ?, ?>> T get(final Class<T> clazz, final String id, final Number revision) {
        String key = buildKey(clazz, id, revision);
        RESTBaseEntityV1<?, ?, ?> value = entityCache.getIfPresent(key);
        return value == null ? null : clazz.cast(value);
    }

    protected String buildKey(final RESTBaseEntityV1<?, ?, ?> value, final Number id, final Number revision) {
        return buildKey(value.getClass(), id.toString(), revision);
    }

    protected String buildKey(final RESTBaseEntityV1<?, ?, ?> value, final String id, final Number revision) {
        return buildKey(value.getClass(), id, revision);
    }

    protected String buildKey(final Class<?> clazz, final Number id, final Number revision) {
        return buildKey(clazz, id.toString(), revision);
    }

    /**
     * Build the unique key to be used for caching entities.
     *
     * @param clazz The class of the entity to be cached.
     * @param id The id of the entity to be cached.
     * @param revision The revision of the entity to be cached, or null if it's the latest entity.
     * @return The unique key that can be used for caching.
     */
    protected String buildKey(final Class<?> clazz, final String id, final Number revision) {
        if (revision != null) {
            return clazz.getSimpleName() + "-" + id + "-" + revision;
        } else {
            return clazz.getSimpleName() + "-" + id;
        }
    }

    public <T extends RESTBaseEntityV1<T, ?, ?>> void expire(final Class<T> clazz, final Number id) {
        // Expire the revision entity that matches the latest entity as it points to the same object
        final T entity = get(clazz, id);
        if (entity != null) {
            expire(clazz, id, entity.getRevision());
        }
        // Expire the latest entity
        expire(clazz, id, null);
    }

    public void expire(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final Number id, final Number revision) {
        final String key = buildKey(clazz, id, revision);
        entityCache.invalidate(key);
    }

    public void expireByRegex(final String regex) {
        for (final String key : entityCache.asMap().keySet()) {
            if (key.matches(regex)) {
                entityCache.invalidate(key);
            }
        }
    }

    public void expireAll() {
        entityCache.invalidateAll();
    }
}
