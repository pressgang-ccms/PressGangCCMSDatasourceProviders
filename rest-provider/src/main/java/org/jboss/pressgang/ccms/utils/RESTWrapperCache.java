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
