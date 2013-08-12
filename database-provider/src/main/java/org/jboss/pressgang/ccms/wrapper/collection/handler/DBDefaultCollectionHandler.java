package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;

public class DBDefaultCollectionHandler<U> implements DBCollectionHandler<U> {

    @Override
    public void addItem(Collection<U> items, U entity) {
        items.add(entity);
    }

    @Override
    public void removeItem(Collection<U> items, U entity) {
        items.remove(entity);
    }
}