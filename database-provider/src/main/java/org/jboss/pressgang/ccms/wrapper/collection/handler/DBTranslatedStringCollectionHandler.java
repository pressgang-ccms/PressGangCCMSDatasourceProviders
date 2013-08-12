package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.interfaces.HasTranslatedStrings;

public class DBTranslatedStringCollectionHandler<T> implements DBUpdateableCollectionHandler<T> {
    private HasTranslatedStrings<T> parent;

    public DBTranslatedStringCollectionHandler(final HasTranslatedStrings<T> parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<T> items, T entity) {
    }

    @Override
    public void addItem(Collection<T> items, final T entity) {
        parent.addTranslatedString(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<T> items, final T entity) {
        parent.removeTranslatedString(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
