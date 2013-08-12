package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.interfaces.HasTranslatedStrings;

public class DBTranslatedStringCollectionHandler<T> implements DBUpdateableCollectionHandler<T> {
    private HasTranslatedStrings<T> parent;

    public DBTranslatedStringCollectionHandler(final HasTranslatedStrings<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(T entity) {
    }

    @Override
    public void addItem(final T entity) {
        parent.addTranslatedString(entity);
    }

    @Override
    public void removeItem(final T entity) {
        parent.removeTranslatedString(entity);
    }
}
