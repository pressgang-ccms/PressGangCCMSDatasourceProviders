package org.jboss.pressgang.ccms.utils;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapper;

public class RESTWrapperCache {
    final Cache<RESTWrapperKey, RESTBaseWrapper> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .build();

    final Cache<RESTWrapperKey, RESTCollectionWrapper> collectionCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    public void put(final RESTWrapperKey key, final RESTBaseWrapper value) {
        cache.put(key, value);
    }

    public void putCollection(final RESTWrapperKey key, final RESTCollectionWrapper value) {
        collectionCache.put(key, value);
    }

    public boolean containsKey(final RESTWrapperKey key) {
        return cache.getIfPresent(key) != null;
    }

    public boolean containsCollectionKey(final RESTWrapperKey key) {
        return collectionCache.getIfPresent(key) != null;
    }

    public RESTBaseWrapper get(final RESTWrapperKey key) {
        return cache.getIfPresent(key);
    }

    public RESTCollectionWrapper getCollection(final RESTWrapperKey key) {
        return collectionCache.getIfPresent(key);
    }
}
