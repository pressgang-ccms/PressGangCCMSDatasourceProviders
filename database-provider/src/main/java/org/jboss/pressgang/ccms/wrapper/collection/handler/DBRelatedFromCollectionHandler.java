package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.interfaces.HasTwoWayRelationships;

public class DBRelatedFromCollectionHandler<T> implements DBUpdateableCollectionHandler<T> {
    private HasTwoWayRelationships<T> parent;

    public DBRelatedFromCollectionHandler(final HasTwoWayRelationships<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<T> items, T entity) {
    }

    @Override
    public void addItem(Collection<T> items, T entity) {
        parent.addRelationshipFrom(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<T> items, T entity) {
        parent.removeRelationshipFrom(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
