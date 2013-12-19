package org.jboss.pressgang.ccms.provider;

import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.provider.listener.CreateListener;
import org.jboss.pressgang.ccms.provider.listener.DeleteListener;
import org.jboss.pressgang.ccms.provider.listener.LogMessageListener;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.provider.listener.UpdateListener;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public abstract class DataProvider {

    private final DataProviderFactory providerFactory;
    private final List<ProviderListener> listeners;

    protected DataProvider(final DataProviderFactory providerFactory) {
        this(providerFactory, new ArrayList<ProviderListener>());
    }

    protected DataProvider(final DataProviderFactory providerFactory, final List<ProviderListener> listeners) {
        this.providerFactory = providerFactory;
        this.listeners = listeners;
    }

    protected DataProviderFactory getProviderFactory() {
        return providerFactory;
    }

    protected List<ProviderListener> getListeners() {
        return listeners;
    }

    protected void notifyLogMessage(LogMessageWrapper logMessage) {
        if (logMessage == null) return;

        for (final ProviderListener listener : listeners) {
            if (listener instanceof LogMessageListener) {
                ((LogMessageListener) listener).handleLogMessage(logMessage);
            }
        }
    }

    protected <T extends EntityWrapper<T>> void notifyCreateEntity(T entity) {
        for (final ProviderListener listener : listeners) {
            if (listener instanceof CreateListener) {
                ((CreateListener) listener).handleCreateEntity(entity);
            }
        }
    }

    protected <T extends EntityWrapper<T>> void notifyUpdateEntity(T entity) {
        for (final ProviderListener listener : listeners) {
            if (listener instanceof UpdateListener) {
                ((UpdateListener) listener).handleUpdateEntity(entity);
            }
        }
    }

    protected <T extends EntityWrapper<T>> void notifyDeleteEntity(Class<?> clazz, Integer id) {
        for (final ProviderListener listener : listeners) {
            if (listener instanceof DeleteListener) {
                ((DeleteListener) listener).handleDeleteEntity(clazz, id);
            }
        }
    }

    protected <T extends EntityWrapper<T>> void notifyCreateEntityCollection(CollectionWrapper<T> collection) {
        for (final ProviderListener listener : listeners) {
            if (listener instanceof CreateListener) {
                ((CreateListener) listener).handleCreateEntityCollection(collection);
            }
        }
    }

    protected <T extends EntityWrapper<T>> void notifyUpdateEntityCollection(CollectionWrapper<T> collection) {
        for (final ProviderListener listener : listeners) {
            if (listener instanceof UpdateListener) {
                ((UpdateListener) listener).handleUpdateEntityCollection(collection);
            }
        }
    }

    protected <T extends EntityWrapper<T>> void notifyDeleteEntityCollection(Class<?> clazz, List<Integer> ids) {
        for (final ProviderListener listener : listeners) {
            if (listener instanceof DeleteListener) {
                ((DeleteListener) listener).handleDeleteEntityCollection(clazz, ids);
            }
        }
    }
}
