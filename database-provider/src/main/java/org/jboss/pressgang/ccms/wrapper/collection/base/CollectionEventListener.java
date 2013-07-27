package org.jboss.pressgang.ccms.wrapper.collection.base;

public interface CollectionEventListener<U> {
    void onAddItem(U entity);
    void onRemoveItem(U entity);
}