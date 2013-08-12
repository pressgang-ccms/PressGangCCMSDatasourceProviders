package org.jboss.pressgang.ccms.wrapper.collection.handler;

public interface DBUpdateableCollectionHandler<U> extends DBCollectionHandler<U> {
    void updateItem(U entity);
}