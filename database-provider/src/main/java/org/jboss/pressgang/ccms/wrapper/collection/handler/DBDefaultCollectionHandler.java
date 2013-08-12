package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;

public class DBDefaultCollectionHandler<U> implements DBCollectionHandler<U> {

    private final Collection<U> items;

    public DBDefaultCollectionHandler(final Collection<U> items) {
        this.items = items;
    }

    @Override
    public void addItem(U entity) {
        items.add(entity);
    }

    @Override
    public void removeItem(U entity) {
        items.remove(entity);
    }
}