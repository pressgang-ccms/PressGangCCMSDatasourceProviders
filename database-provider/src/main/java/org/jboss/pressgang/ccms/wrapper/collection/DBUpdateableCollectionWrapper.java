package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.provider.exception.BadRequestException;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.CollectionEventListener;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public abstract class DBUpdateableCollectionWrapper<T extends EntityWrapper<T>, U> extends DBCollectionWrapper<T,
        U> implements UpdateableCollectionWrapper<T> {
    private static final Integer UPDATE_STATE = 3;

    public DBUpdateableCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<U> items, boolean isRevisionList,
            Class<T> wrapperClass) {
        super(wrapperFactory, items, isRevisionList, wrapperClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addUpdateItem(T entity) {
        final U unwrappedEntity = (U) entity.unwrap();
        if (getCollectionItems().contains(unwrappedEntity)) {
            getCollection().put(entity, UPDATE_STATE);
            if (!isRevisionList()) {
                notifyOnUpdateEvent(unwrappedEntity);
            }
        } else {
            throw new BadRequestException("Update Entity is not part of the collection");
        }
    }

    @Override
    public List<T> getUpdateItems() {
        final List<T> updateItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (UPDATE_STATE.equals(entity.getValue())) {
                updateItems.add(entity.getKey());
            }
        }

        return updateItems;
    }

    public void registerEventListener(UpdateableCollectionEventListener<U> listener) {
        super.registerEventListener(listener);
    }

    private void notifyOnUpdateEvent(U entity) {
        for (final CollectionEventListener<U> listener : getEventListeners()) {
            if (listener instanceof UpdateableCollectionEventListener) {
                ((UpdateableCollectionEventListener) listener).onUpdateItem(entity);
            }
        }
    }
}
