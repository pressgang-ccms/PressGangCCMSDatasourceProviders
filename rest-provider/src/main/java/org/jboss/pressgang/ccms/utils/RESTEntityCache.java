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

package org.jboss.pressgang.ccms.utils;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseAuditedEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTEntityCache {
    private static final String CACHE_MAX_SIZE_PROPERTY = "pressgang.rest.cache.maxSize";
    private static final String CACHE_TIMEOUT_PROPERTY = "pressgang.rest.cache.timeout";
    private static final Long DEFAULT_MAX_CACHE_SIZE = 2500L;
    private static final Long DEFAULT_CACHE_TIMEOUT = 5L;

    protected final Cache<String, RESTBaseEntityV1<?>> entityCache;

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
    public void add(final RESTBaseEntityCollectionV1<?, ?, ?> value) {
        add(value, false);
    }

    /**
     * Add a collection of entities to the cache.
     *
     * @param value       The collection of entities to be added to the cache.
     * @param isRevisions Whether or not the collection is a collection of revisions or not.
     */
    public void add(final RESTBaseEntityCollectionV1<?, ?, ?> value, final boolean isRevisions) {
        if (value != null && value.getItems() != null) {
            for (final RESTBaseEntityCollectionItemV1<?, ?, ?> item : value.getItems()) {
                if (item.getItem() != null) {
                    add(item.getItem(), isRevisions);
                }
            }
        }
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?>> clazz, final Number id) {
        return containsKeyValue(clazz, id.toString(), null);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?>> clazz, final String id) {
        return containsKeyValue(clazz, id, null);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?>> clazz, final Number id, final Number revision) {
        return containsKeyValue(clazz, id.toString(), revision);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?>> clazz, final String id, final Number revision) {
        final String key = buildKey(clazz, id, revision);
        return entityCache.asMap().containsKey(key);
    }

    public void add(final RESTBaseEntityV1<?> value) {
        add(value, false);
    }

    public void add(final RESTBaseEntityV1<?> value, final Number id, final Number revision) {
        add(value, id.toString(), revision);
    }

    public void add(final RESTBaseEntityV1<?> value, final Number id, final boolean isRevision) {
        add(value, id.toString(), isRevision);
    }

    public void add(final RESTBaseEntityV1<?> value, final boolean isRevision) {
        if (value == null) return;

        // Add a null revision to specify it's the latest, if the entity isn't a revision
        if (value instanceof RESTBaseAuditedEntityV1) {
            final RESTBaseAuditedEntityV1<?, ?, ?> auditedEntity = (RESTBaseAuditedEntityV1<?, ?, ?>) value;
            add(value, isRevision ? auditedEntity.getRevision() : null);
        } else if (!isRevision) {
            add(value, null);
        }
    }

    public void add(final RESTBaseEntityV1<?> value, final String id, final boolean isRevision) {
        if (value == null) return;

        // Add a null revision to specify it's the latest, if the entity isn't a revision
        if (value instanceof RESTBaseAuditedEntityV1) {
            final RESTBaseAuditedEntityV1<?, ?, ?> auditedEntity = (RESTBaseAuditedEntityV1<?, ?, ?>) value;
            add(value, id, isRevision ? auditedEntity.getRevision() : null);
        } else if (!isRevision) {
            add(value, id, null);
        }
    }

    /**
     * Add a Entity to the cache, where the id should be extracted from the entity.
     *
     * @param value    The entity to be added to the cache.
     * @param revision The revision of the entity to be cached.
     */
    public void add(final RESTBaseEntityV1<?> value, final Number revision) {
        add(value, value.getId(), revision);
    }

    public void add(final RESTBaseEntityV1<?> value, final String id, final Number revision) {
        if (value == null) return;

        // Add the entity
        final String key = buildKey(value, id, revision);
        if (entityCache.getIfPresent(key) == null) {
            entityCache.put(key, value);
        }

        // Add any revisions to the cache
        if (value instanceof RESTBaseAuditedEntityV1) {
            final RESTBaseAuditedEntityV1<?, ?, ?> auditedEntity = (RESTBaseAuditedEntityV1<?, ?, ?>) value;
            if (auditedEntity.getRevisions() != null) {
                add(auditedEntity.getRevisions(), true);
            }
        }
    }

    public <T extends RESTBaseEntityV1<T>> T get(final Class<T> clazz, final Number id) {
        return get(clazz, id.toString(), null);
    }

    public <T extends RESTBaseEntityV1<T>> T get(final Class<T> clazz, final String id) {
        return get(clazz, id, null);
    }

    public <T extends RESTBaseEntityV1<T>> T get(final Class<T> clazz, final Number id, final Number revision) {
        return get(clazz, id.toString(), revision);
    }

    public <T extends RESTBaseEntityV1<T>> T get(final Class<T> clazz, final String id, final Number revision) {
        String key = buildKey(clazz, id, revision);
        RESTBaseEntityV1<?> value = entityCache.getIfPresent(key);
        return value == null ? null : clazz.cast(value);
    }

    protected String buildKey(final RESTBaseEntityV1<?> value, final Number id, final Number revision) {
        return buildKey(value.getClass(), id.toString(), revision);
    }

    protected String buildKey(final RESTBaseEntityV1<?> value, final String id, final Number revision) {
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

    public <T extends RESTBaseEntityV1<T>> void expire(final Class<T> clazz, final Number id) {
        // Expire the revision entity that matches the latest entity as it points to the same object
        final T entity = get(clazz, id);
        if (entity != null && entity instanceof RESTBaseAuditedEntityV1) {
            expire(clazz, id, ((RESTBaseAuditedEntityV1) entity).getRevision());
        }
        // Expire the latest entity
        expire(clazz, id, null);
    }

    public void expire(final Class<? extends RESTBaseEntityV1<?>> clazz, final Number id, final Number revision) {
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
