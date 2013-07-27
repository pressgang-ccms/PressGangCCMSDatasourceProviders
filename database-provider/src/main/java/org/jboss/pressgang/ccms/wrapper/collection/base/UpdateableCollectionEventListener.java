package org.jboss.pressgang.ccms.wrapper.collection.base;

public interface UpdateableCollectionEventListener<U> extends CollectionEventListener<U> {
    void onUpdateItem(U entity);
}