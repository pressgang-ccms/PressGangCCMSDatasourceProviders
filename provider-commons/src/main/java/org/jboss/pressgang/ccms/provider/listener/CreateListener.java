package org.jboss.pressgang.ccms.provider.listener;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface CreateListener extends ProviderListener {
    <T extends EntityWrapper<T>> void handleCreateEntity(T entity);
    <T extends EntityWrapper<T>> void handleCreateEntityCollection(CollectionWrapper<T> collection);
}
