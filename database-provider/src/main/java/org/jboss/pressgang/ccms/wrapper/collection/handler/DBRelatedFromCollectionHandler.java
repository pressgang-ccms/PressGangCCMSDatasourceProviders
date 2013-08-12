package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.interfaces.HasTwoWayRelationships;

public class DBRelatedFromCollectionHandler<T> implements DBUpdateableCollectionHandler<T> {
    private HasTwoWayRelationships<T> parent;

    public DBRelatedFromCollectionHandler(final HasTwoWayRelationships<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(T entity) {
    }

    @Override
    public void addItem(T entity) {
        parent.addRelationshipFrom(entity);
    }

    @Override
    public void removeItem(T entity) {
        parent.removeRelationshipFrom(entity);
    }
}
