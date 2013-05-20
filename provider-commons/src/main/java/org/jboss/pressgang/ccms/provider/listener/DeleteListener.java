package org.jboss.pressgang.ccms.provider.listener;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;

public interface DeleteListener extends ProviderListener {
    <T extends EntityWrapper<T>> void handleDeleteEntity(Class<?> clazz, Integer id);
    <T extends EntityWrapper<T>> void handleDeleteEntityCollection(Class<?> clazz, List<Integer> ids);
}
