package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public abstract class DBCollectionWrapper<T extends EntityWrapper<T>, U> implements CollectionWrapper<T> {
    private static final Integer NO_STATE = 0;
    private static final Integer ADD_STATE = 1;
    private static final Integer REMOVE_STATE = 2;

    private final Map<T, Integer> wrapperItems = new HashMap<T, Integer>();
    private final Map<U, Integer> items = new HashMap<U, Integer>();
    private final DBWrapperFactory wrapperFactory;
    private final boolean isRevisionList;

    protected DBCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<U> items, boolean isRevisionList,
            final Class<T> wrapperClass) {
        this.wrapperFactory = wrapperFactory;
        for (final U item : items) {
            wrapperItems.put((T) wrapperFactory.create(item, isRevisionList, wrapperClass), NO_STATE);
            this.items.put(item, NO_STATE);
        }
        this.isRevisionList = isRevisionList;
    }

    protected DBWrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    protected Map<T, Integer> getCollection() {
        return wrapperItems;
    }

    protected Map<U, Integer> getCollectionItems() {
        return items;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addItem(T entity) {
        getCollection().put(entity, NO_STATE);
        getCollectionItems().put((U) entity.unwrap(), NO_STATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addNewItem(T entity) {
        getCollection().put(entity, ADD_STATE);
        getCollectionItems().put((U) entity.unwrap(), ADD_STATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addRemoveItem(T entity) {
        getCollection().put(entity, REMOVE_STATE);
        getCollectionItems().put((U) entity.unwrap(), REMOVE_STATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void remove(T entity) {
        getCollection().remove(entity);
        getCollectionItems().remove((U) entity.unwrap());
    }

    public List<T> getItems() {
        return new ArrayList<T>(getCollection().keySet());
    }

    @Override
    public List<T> getUnchangedItems() {
        final List<T> unchangedItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (entity.getValue() == NO_STATE) {
                unchangedItems.add(entity.getKey());
            }
        }

        return unchangedItems;
    }

    @Override
    public List<T> getAddItems() {
        final List<T> newItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (entity.getValue() == ADD_STATE) {
                newItems.add(entity.getKey());
            }
        }

        return newItems;
    }

    @Override
    public List<T> getRemoveItems() {
        final List<T> removeItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (entity.getValue() == REMOVE_STATE) {
                removeItems.add(entity.getKey());
            }
        }

        return removeItems;
    }

    @Override
    public Map<U, Integer> unwrap() {
        return items;
    }

    @Override
    public int size() {
        return wrapperItems.size();
    }

    @Override
    public boolean isEmpty() {
        return wrapperItems.isEmpty();
    }

    protected boolean isRevisionList() {
        return isRevisionList;
    }
}
