package org.jboss.pressgang.ccms.provider.listener;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface UpdateListener extends ProviderListener {
    <T extends EntityWrapper<T>> void handleUpdateEntity(T entity);
    <T extends EntityWrapper<T>> void handleUpdateEntityCollection(CollectionWrapper<T> collection);
}
