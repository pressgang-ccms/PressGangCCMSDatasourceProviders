package org.jboss.pressgang.ccms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.utils.common.StringUtilities;

public class RESTCollectionCache {
    private static final String CACHE_MAX_SIZE_PROPERTY = "pressgang.rest.cache.collection.maxSize";
    private static final String CACHE_TIMEOUT_PROPERTY = "pressgang.rest.cache.collection.timeout";
    private static final Long DEFAULT_MAX_CACHE_SIZE = 100L;
    private static final Long DEFAULT_CACHE_TIMEOUT = 5L;

    protected final RESTEntityCache entityCache;
    protected final Cache<String, RESTBaseEntityCollectionV1<?, ?, ?>> collectionCache;

    public RESTCollectionCache(final RESTEntityCache entityCache) {
        this.entityCache = entityCache;

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

        collectionCache = CacheBuilder.newBuilder()
                .expireAfterWrite(timeout, TimeUnit.MINUTES)
                .maximumSize(maxSize)
                .build();
    }

    public <T extends RESTBaseEntityV1<T, U, ?>, U extends RESTBaseEntityCollectionV1<T, U, ?>> void add(final Class<T> clazz, final U value) {
        add(clazz, value, null);
    }

    public <T extends RESTBaseEntityV1<T, U, ?>, U extends RESTBaseEntityCollectionV1<T, U, ?>> void add(final Class<T> clazz, final U value,
            final List<String> additionalKeys) {
        add(clazz, value, additionalKeys, false);
    }

    public <T extends RESTBaseEntityV1<T, U, ?>, U extends RESTBaseEntityCollectionV1<T, U, ?>> void add(final Class<T> clazz, final U value,
            final List<String> additionalKeys, final boolean isRevisions) {
        String key = buildKey(clazz, additionalKeys);
        entityCache.add(value, isRevisions);
        collectionCache.put(key, value);
    }

    public boolean containsKey(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz) {
        return containsKey(clazz, null);
    }

    public boolean containsKey(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final List<String> additionalKeys) {
        String key = buildKey(clazz, additionalKeys);
        return collectionCache.asMap().containsKey(key);
    }

    public <T extends RESTBaseEntityV1<T, U, ?>, U extends RESTBaseEntityCollectionV1<T, U, ?>> U get(final Class<T> clazz,
            final Class<U> containerClass) {
        return get(clazz, containerClass, new ArrayList<String>());
    }

    @SuppressWarnings("unchecked")
    public <T extends RESTBaseEntityV1<T, U, ?>, U extends RESTBaseEntityCollectionV1<T, U, ?>> U get(final Class<T> clazz,
            final Class<U> containerClass, final List<String> additionalKeys) {
        try {
            String key = buildKey(clazz, additionalKeys);
            RESTBaseEntityCollectionV1<?, ?, ?> value = collectionCache.getIfPresent(key);
            return (U) (value == null ? containerClass.newInstance() : value);
        } catch (final Exception ex) {
            return null;
        }
    }

    public void expire(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz) {
        collectionCache.invalidate(clazz.getSimpleName());
    }

    public void expire(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final List<String> additionalKeys) {
        collectionCache.invalidate(buildKey(clazz, additionalKeys));
    }

    public void expireByRegex(final String regex) {
        for (final String key : collectionCache.asMap().keySet()) {
            if (key.matches(regex)) {
                collectionCache.invalidate(key);
            }
        }
    }

    public void expireAll() {
        collectionCache.invalidateAll();
    }

    protected String buildKey(final RESTBaseEntityV1<?, ?, ?> value, final List<String> additionalKeys) {
        return buildKey(value.getClass(), additionalKeys);
    }

    protected String buildKey(final Class<?> clazz, final List<String> additionalKeys) {
        String key = clazz.getSimpleName();
        if (additionalKeys != null && !additionalKeys.isEmpty()) {
            key += "-" + StringUtilities.buildString(additionalKeys.toArray(new String[additionalKeys.size()]), "-");
        }
        return key;
    }
}
