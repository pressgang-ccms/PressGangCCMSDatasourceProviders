package org.jboss.pressgang.ccms.wrapper.collection.handler;

public interface DBCollectionHandler<U> {
    void addItem(U entity);

    void removeItem(U entity);
}
