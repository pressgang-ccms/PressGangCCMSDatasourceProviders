package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;

public interface DBUpdateableCollectionHandler<U> extends DBCollectionHandler<U> {
    void updateItem(Collection<U> items, U entity);
}