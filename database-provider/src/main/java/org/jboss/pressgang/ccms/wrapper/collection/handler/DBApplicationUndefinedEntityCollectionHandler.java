package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.model.config.EntitiesConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedEntity;

public class DBApplicationUndefinedEntityCollectionHandler implements DBUpdateableCollectionHandler<UndefinedEntity> {
    final EntitiesConfig entitiesConfig;

    public DBApplicationUndefinedEntityCollectionHandler(final EntitiesConfig entitiesConfig) {
        this.entitiesConfig = entitiesConfig;
    }

    @Override
    public void updateItem(Collection<UndefinedEntity> items, final UndefinedEntity entity) {
        try {
            entitiesConfig.addUndefinedProperty(entity.getKey(), entity.getValue());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addItem(Collection<UndefinedEntity> items, final UndefinedEntity entity) {
        try {
            entitiesConfig.addUndefinedProperty(entity.getKey(), entity.getValue());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<UndefinedEntity> items, final UndefinedEntity entity) {
        entitiesConfig.removeProperty(entity.getKey());
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}