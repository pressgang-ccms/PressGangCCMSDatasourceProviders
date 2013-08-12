package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.base.ToPropertyTag;
import org.jboss.pressgang.ccms.model.interfaces.HasProperties;

public class DBPropertyTagCollectionHandler<T extends ToPropertyTag<T>> implements DBUpdateableCollectionHandler<T> {
    private HasProperties<T> parent;

    public DBPropertyTagCollectionHandler(final HasProperties<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(T entity) {
    }

    @Override
    public void addItem(final T entity) {
        parent.addPropertyTag(entity);
    }

    @Override
    public void removeItem(final T entity) {
        parent.removePropertyTag(entity);
    }
}
