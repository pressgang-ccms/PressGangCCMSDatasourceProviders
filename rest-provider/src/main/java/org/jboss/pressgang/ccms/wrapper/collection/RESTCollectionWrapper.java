package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseEntityCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseObjectV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;

public abstract class RESTCollectionWrapper<T extends BaseWrapper<T>, U extends RESTBaseObjectV1<U>,
        V extends RESTCollectionV1<U, ?>> implements CollectionWrapper<T> {
    private final RESTProviderFactory providerFactory;
    private final RESTWrapperFactory wrapperFactory;
    private final Map<T, Integer> entities = new HashMap<T, Integer>();
    private final V collection;

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
        this.collection = collection;
        if (collection.getItems() != null) {
            for (final RESTCollectionItemV1<U, ?> item : collection.getItems()) {
                entities.put((T) getWrapperFactory().create(item.getItem(), isRevisionCollection), item.getState());
            }
        }
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final Class<T> wrapperClass) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
        this.collection = collection;

        if (collection.getItems() != null) {
            for (final RESTCollectionItemV1<U, ?> item : collection.getItems()) {
                entities.put(getWrapperFactory().create(item.getItem(), isRevisionCollection, wrapperClass), item.getState());
            }
        }
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
        this.collection = collection;
        for (final RESTCollectionItemV1<U, ?> item : collection.getItems()) {
            entities.put((T) getWrapperFactory().create(item.getItem(), isRevisionCollection, parent), item.getState());
        }
    }

    public RESTCollectionWrapper(final RESTProviderFactory providerFactory, final V collection, boolean isRevisionCollection,
            final RESTBaseEntityV1<?, ?, ?> parent, final Class<T> wrapperClass) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
        this.collection = collection;
        for (final RESTCollectionItemV1<U, ?> item : collection.getItems()) {
            entities.put(getWrapperFactory().create(item.getItem(), isRevisionCollection, parent, wrapperClass), item.getState());
        }
    }

    protected RESTWrapperFactory getWrapperFactory() {
        return wrapperFactory;
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
        final V collection = (baseCollection instanceof ProxyObject) ? (V)((RESTCollectionV1ProxyHandler<?, ?,
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
