package org.jboss.pressgang.ccms.utils;

import java.util.HashMap;

import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTEntityCache {

    private HashMap<Class<?>, HashMap<String, RESTBaseEntityV1<?, ?, ?>>> singleEntities = new HashMap<Class<?>, HashMap<String,
            RESTBaseEntityV1<?, ?, ?>>>();

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
     * @param value The collection of entities to be added to the cache.
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

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final Number id, final Number revision) {
        return containsKeyValue(clazz, id.toString(), revision);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final String id, final Number revision) {
        if (singleEntities.containsKey(clazz)) {
            final String key = buildKey(clazz, id, revision);
            return singleEntities.get(clazz).containsKey(key);
        } else {
            return false;
        }
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final Number id) {
        return containsKeyValue(clazz, id.toString(), null);
    }

    public boolean containsKeyValue(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final String id) {
        return containsKeyValue(clazz, id, null);
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
     * @param value The entity to be added to the cache.
     * @param revision The revision of the entity to be cached.
     */
    public void add(final RESTBaseEntityV1<?, ?, ?> value, final Number revision) {
        add(value, value.getId(), revision);
    }

    public void add(final RESTBaseEntityV1<?, ?, ?> value, final String id, final Number revision) {
        // Add the map if one doesn't exist for the current class
        if (!singleEntities.containsKey(value.getClass())) {
            singleEntities.put(value.getClass(), new HashMap<String, RESTBaseEntityV1<?, ?, ?>>());
        }

        // Add the entity
        final String key = buildKey(value, id, revision);
        singleEntities.get(value.getClass()).put(key, value);

        // Add any revisions to the cache
        if (value.getRevisions() != null) {
            add(value.getRevisions(), true);
        }
    }

    public <T extends RESTBaseEntityV1<T, U, ?>, U extends RESTBaseCollectionV1<T, U, ?>> U get(final Class<T> clazz,
            final Class<U> collectionClass) {
        try {
            final U values = collectionClass.newInstance();
            if (singleEntities.containsKey(clazz)) {
                for (final String key : singleEntities.get(clazz).keySet()) {
                    values.addItem(clazz.cast(singleEntities.get(clazz).get(key)));
                }
            }
            return values;
        } catch (final Exception ex) {
            return null;
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
        if (!containsKeyValue(clazz, id, revision)) return null;
        return clazz.cast(
                revision == null ? singleEntities.get(clazz).get(clazz.getSimpleName() + "-" + id) : singleEntities.get(clazz).get(
                        clazz.getSimpleName() + "-" + id + "-" + revision));
    }

    protected String buildKey(final RESTBaseEntityV1<?, ?, ?> value, final Number id, final Number revision) {
        return buildKey(value.getClass(), id.toString(), revision);
    }

    protected String buildKey(final RESTBaseEntityV1<?, ?, ?> value, final String id, final Number revision) {
        return buildKey(value.getClass(), id, revision);
    }

    protected String buildKey(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final Number id, final Number revision) {
        return buildKey(clazz, id.toString(), revision);
    }

    protected String buildKey(final Class<? extends RESTBaseEntityV1<?, ?, ?>> clazz, final String id, final Number revision) {
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
        if (singleEntities.containsKey(clazz)) {
            if (singleEntities.get(clazz).containsKey(key)) {
                singleEntities.get(clazz).remove(key);
            }
        }
    }

    public void expireByRegex(final String regex) {
        for (final Class<?> clazz : singleEntities.keySet()) {
            for (final String key : singleEntities.get(clazz).keySet()) {
                if (key.matches(regex)) singleEntities.remove(key);
            }
        }
    }
}
