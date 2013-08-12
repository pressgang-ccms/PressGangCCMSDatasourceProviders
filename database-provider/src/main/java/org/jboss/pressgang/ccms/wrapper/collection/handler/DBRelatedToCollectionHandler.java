package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.interfaces.HasTwoWayRelationships;

public class DBRelatedToCollectionHandler<T> implements DBUpdateableCollectionHandler<T> {
    private HasTwoWayRelationships<T> parent;

    public DBRelatedToCollectionHandler(final HasTwoWayRelationships<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(T entity) {
    }

    @Override
    public void addItem(T entity) {
        parent.addRelationshipTo(entity);
    }

    @Override
    public void removeItem(T entity) {
        parent.removeRelationshipTo(entity);
    }
}
