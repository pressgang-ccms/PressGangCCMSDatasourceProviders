/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.model.config.EntitiesConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedEntity;

public class DBServerUndefinedEntityCollectionHandler implements DBUpdateableCollectionHandler<UndefinedEntity> {
    final EntitiesConfig entitiesConfig;

    public DBServerUndefinedEntityCollectionHandler(final EntitiesConfig entitiesConfig) {
        this.entitiesConfig = entitiesConfig;
    }

    @Override
    public void updateItem(Collection<UndefinedEntity> items, final UndefinedEntity entity) {
        try {
            entitiesConfig.addUndefinedEntity(entity.getKey(), entity.getValue());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addItem(Collection<UndefinedEntity> items, final UndefinedEntity entity) {
        try {
            entitiesConfig.addUndefinedEntity(entity.getKey(), entity.getValue());
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
