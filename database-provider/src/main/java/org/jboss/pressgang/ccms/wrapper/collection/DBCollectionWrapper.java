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

package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.CollectionEventListener;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCollectionHandler;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBDefaultCollectionHandler;

public abstract class DBCollectionWrapper<T extends BaseWrapper<T>, U> implements CollectionWrapper<T> {
    private static final Integer NO_STATE = 0;
    private static final Integer ADD_STATE = 1;
    private static final Integer REMOVE_STATE = 2;

    private final Map<T, Integer> wrapperItems = new HashMap<T, Integer>();
    private final Collection<U> items;
    private final DBWrapperFactory wrapperFactory;
    private final boolean isRevisionList;
    private DBCollectionHandler<U> handler;
    private final Set<CollectionEventListener<U>> listeners = new HashSet<CollectionEventListener<U>>();

    protected DBCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<U> items, boolean isRevisionList,
            final Class<T> wrapperClass) {
        this(wrapperFactory, items, isRevisionList, wrapperClass, new DBDefaultCollectionHandler<U>());
    }

    protected DBCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<U> items, boolean isRevisionList,
            final Class<T> wrapperClass, final DBCollectionHandler<U> handler) {
        this.wrapperFactory = wrapperFactory;
        for (final U item : items) {
            wrapperItems.put((T) wrapperFactory.create(item, isRevisionList, wrapperClass), NO_STATE);
        }
        this.items = items;
        this.isRevisionList = isRevisionList;
        this.handler = handler;
    }

    protected DBWrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    protected Map<T, Integer> getCollection() {
        return wrapperItems;
    }

    protected Collection<U> getCollectionItems() {
        return items;
    }

    public DBCollectionHandler<U> getHandler() {
        return handler;
    }

    public void setHandler(final DBCollectionHandler<U> handler) {
        this.handler = handler;
    }

    public void registerEventListener(CollectionEventListener<U> listener) {
        listeners.add(listener);
    }

    public void resetEventListeners() {
        listeners.clear();
    }

    protected Set<CollectionEventListener<U>> getEventListeners() {
        return listeners;
    }

    @Override
    public void addItem(T entity) {
        getCollection().put(entity, NO_STATE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addNewItem(T entity) {
        getCollection().put(entity, ADD_STATE);
        if (!isRevisionList()) {
            final U unwrappedEntity = (U) entity.unwrap();
            if (!getCollectionItems().contains(unwrappedEntity)) {
                notifyOnAddEvent(unwrappedEntity);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addRemoveItem(T entity) {
        getCollection().put(entity, REMOVE_STATE);
        if (!isRevisionList()) {
            final U unwrappedEntity = (U) entity.unwrap();
            notifyOnRemoveEvent(unwrappedEntity);
        }
    }

    @Override
    public void remove(T entity) {
        getCollection().remove(entity);
    }

    public List<T> getItems() {
        return new ArrayList<T>(getCollection().keySet());
    }

    @Override
    public List<T> getUnchangedItems() {
        final List<T> unchangedItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (NO_STATE.equals(entity.getValue())) {
                unchangedItems.add(entity.getKey());
            }
        }

        return unchangedItems;
    }

    @Override
    public List<T> getAddItems() {
        final List<T> newItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (ADD_STATE.equals(entity.getValue())) {
                newItems.add(entity.getKey());
            }
        }

        return newItems;
    }

    @Override
    public List<T> getRemoveItems() {
        final List<T> removeItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (REMOVE_STATE.equals(entity.getValue())) {
                removeItems.add(entity.getKey());
            }
        }

        return removeItems;
    }

    @Override
    public Collection<U> unwrap() {
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

    private void notifyOnAddEvent(U entity) {
        handler.addItem(items, entity);
        for (final CollectionEventListener<U> listener : getEventListeners()) {
            listener.onAddItem(entity);
        }
    }

    private void notifyOnRemoveEvent(U entity) {
        handler.removeItem(items, entity);
        for (final CollectionEventListener<U> listener : getEventListeners()) {
            listener.onRemoveItem(entity);
        }
    }
}
