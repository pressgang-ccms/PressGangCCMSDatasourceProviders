package org.jboss.pressgang.ccms.wrapper.structures;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCollectionWrapper;

public class DBWrapperCache {
    final Cache<DBWrapperKey, DBBaseWrapper> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .build();

    final Cache<DBWrapperKey, DBCollectionWrapper> collectionCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    public void put(final DBWrapperKey key, final DBBaseWrapper value) {
        cache.put(key, value);
    }

    public void putCollection(final DBWrapperKey key, final DBCollectionWrapper value) {
        collectionCache.put(key, value);
    }

    public boolean containsKey(final DBWrapperKey key) {
        return cache.getIfPresent(key) != null;
    }

    public boolean containsCollectionKey(final DBWrapperKey key) {
        return collectionCache.getIfPresent(key) != null;
    }

    public DBBaseWrapper get(final DBWrapperKey key) {
        return cache.getIfPresent(key);
    }

    public DBCollectionWrapper getCollection(final DBWrapperKey key) {
        return collectionCache.getIfPresent(key);
    }
}
