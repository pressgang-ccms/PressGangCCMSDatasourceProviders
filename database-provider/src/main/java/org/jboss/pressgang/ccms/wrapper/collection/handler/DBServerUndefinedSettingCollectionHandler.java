package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedSetting;

public class DBServerUndefinedSettingCollectionHandler implements DBUpdateableCollectionHandler<UndefinedSetting> {
    final ApplicationConfig applicationConfig;

    public DBServerUndefinedSettingCollectionHandler(final ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void updateItem(Collection<UndefinedSetting> items, final UndefinedSetting entity) {
        try {
            applicationConfig.addUndefinedSetting(entity.getKey(), entity.getValue());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addItem(Collection<UndefinedSetting> items, final UndefinedSetting entity) {
        try {
            applicationConfig.addUndefinedSetting(entity.getKey(), entity.getValue());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<UndefinedSetting> items, final UndefinedSetting entity) {
        applicationConfig.removeProperty(entity.getKey());
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}