/*
  Copyright 2011-2014 Red Hat

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

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
