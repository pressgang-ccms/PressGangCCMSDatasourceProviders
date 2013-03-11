package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

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
        getCollection().put(entity, UPDATE_STATE);
        getCollectionItems().put((U) entity.unwrap(), UPDATE_STATE);
    }

    @Override
    public List<T> getUpdateItems() {
        final List<T> updateItems = new ArrayList<T>();
        for (final Map.Entry<T, Integer> entity : getCollection().entrySet()) {
            if (entity.getValue() == UPDATE_STATE) {
                updateItems.add(entity.getKey());
            }
        }

        return updateItems;
    }
}
