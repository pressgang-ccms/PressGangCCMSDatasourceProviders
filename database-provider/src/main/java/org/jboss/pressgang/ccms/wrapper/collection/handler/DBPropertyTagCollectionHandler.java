package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.base.ToPropertyTag;
import org.jboss.pressgang.ccms.model.interfaces.HasProperties;

public class DBPropertyTagCollectionHandler<T extends ToPropertyTag<T>> implements DBUpdateableCollectionHandler<T> {
    private HasProperties<T> parent;

    public DBPropertyTagCollectionHandler(final HasProperties<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<T> items, T entity) {
    }

    @Override
    public void addItem(Collection<T> items, final T entity) {
        parent.addPropertyTag(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<T> items, final T entity) {
        parent.removePropertyTag(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
