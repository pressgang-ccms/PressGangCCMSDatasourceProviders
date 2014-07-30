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
import java.util.List;
import java.util.Map;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTCollectionV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.elements.base.RESTBaseElementV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public abstract class RESTCollectionWrapper<T extends BaseWrapper<T>, U extends RESTBaseElementV1<U>, V extends RESTCollectionV1<U,
        ?>> implements CollectionWrapper<T> {
    private final RESTProviderFactory providerFactory;
    private final Map<T, Integer> entities = new HashMap<T, Integer>();
    private final V collection;

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection) {
        this(providerFactory, collection, isRevisionCollection, (Collection<String>) null);
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Collection<String> expandedEntityMethods) {
        this(providerFactory, collection, isRevisionCollection, null, null, expandedEntityMethods);
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Class<T> wrapperClass) {
        this(providerFactory, collection, isRevisionCollection, wrapperClass, (Collection<String>) null);
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Class<T> wrapperClass, final Collection<String> expandedEntityMethods) {
        this(providerFactory, collection, isRevisionCollection, null, wrapperClass, expandedEntityMethods);
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent) {
        this(providerFactory, collection, isRevisionCollection, parent, (Collection<String>) null);
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Collection<String> expandedEntityMethods) {
        this(providerFactory, collection, isRevisionCollection, parent, null, expandedEntityMethods);
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Class<T> wrapperClass) {
        this(providerFactory, collection, isRevisionCollection, parent, wrapperClass, null);
    }

    @SuppressWarnings("unchecked")
    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Class<T> wrapperClass, final Collection<String> expandedEntityMethods) {
        this.providerFactory = providerFactory;
        this.collection = collection;
        for (final RESTCollectionItemV1<U, ?> item : collection.getItems()) {
            final T wrappedEntity = RESTEntityWrapperBuilder.newBuilder()
                    .providerFactory(providerFactory)
                    .entity(item.getItem())
                    .isRevision(isRevisionCollection)
                    .parent(parent)
                    .wrapperInterface(wrapperClass)
                    .expandedMethods(expandedEntityMethods)
                    .build();
            entities.put(wrappedEntity, item.getState());
        }
    }

    protected RESTProviderFactory getProviderFactory() {
        return providerFactory;
    }

    protected V getCollection() {
        return collection;
    }

    protected Map<T, Integer> getEntities() {
        return entities;
    }

    public List<T> getItems() {
        return new ArrayList<T>(entities.keySet());
    }

    @Override
    public List<T> getUnchangedItems() {
        final List<T> unchangedItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : entities.entrySet()) {
            if (RESTBaseEntityCollectionItemV1.UNCHANGED_STATE.equals(entity.getValue())) {
                unchangedItems.add(entity.getKey());
            }
        }

        return unchangedItems;
    }

    @Override
    public List<T> getAddItems() {
        final List<T> newItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : entities.entrySet()) {
            if (RESTBaseEntityCollectionItemV1.ADD_STATE.equals(entity.getValue())) {
                newItems.add(entity.getKey());
            }
        }

        return newItems;
    }

    @Override
    public List<T> getRemoveItems() {
        final List<T> removeItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : entities.entrySet()) {
            if (RESTBaseEntityCollectionItemV1.REMOVE_STATE.equals(entity.getValue())) {
                removeItems.add(entity.getKey());
            }
        }

        return removeItems;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addItem(T entity) {
        getCollection().addItem(getEntity(entity));
        getEntities().put(entity, RESTBaseEntityCollectionItemV1.UNCHANGED_STATE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addNewItem(T entity) {
        getCollection().addNewItem(getEntity(entity));
        getEntities().put(entity, RESTBaseEntityCollectionItemV1.ADD_STATE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addRemoveItem(T entity) {
        getCollection().addRemoveItem(getEntity(entity));
        getEntities().put(entity, RESTBaseEntityCollectionItemV1.REMOVE_STATE);
    }

    @Override
    public V unwrap() {
        // Get the original non proxied collection
        final V baseCollection = getCollection();
        final V collection = (baseCollection instanceof ProxyObject) ? (V) ((RESTCollectionV1ProxyHandler<?, ?,
                        ?>) ((ProxyObject) baseCollection).getHandler()).getCollection() : baseCollection;
        return collection;
    }

    @SuppressWarnings("unchecked")
    protected U getEntity(T entity) {
        return (U) entity.unwrap();
    }

    @Override
    public int size() {
        return getCollection().getItems() == null ? 0 : getCollection().getItems().size();
    }

    @Override
    public boolean isEmpty() {
        return getCollection().getItems() == null ? true : getCollection().getItems().isEmpty();
    }

    @Override
    public void remove(T entity) {
        if (entity == null) return;
        final Object unwrappedEntity = entity.unwrap();
        // Get the original non proxied collection
        final RESTCollectionV1<U, ?> baseCollection = getCollection();
        final RESTCollectionV1<?, ?> collection = (baseCollection instanceof ProxyObject) ? ((RESTCollectionV1ProxyHandler<?, ?,
                ?>) ((ProxyObject) baseCollection).getHandler()).getCollection() : baseCollection;
        // Remove the item if it's found
        final List<? extends RESTCollectionItemV1> originalItems = CollectionUtilities.toArrayList(collection.getItems());
        for (final RESTCollectionItemV1<?, ?> item : originalItems) {
            if (unwrappedEntity.equals(item.getItem())) {
                collection.getItems().remove(item);
            }
        }

        getEntities().remove(entity);
    }
}
