package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.Map;

public interface DBCollectionHandler<U> {
    void addItem(Collection<U> items, U entity);

    void removeItem(Collection<U> items, U entity);
}
