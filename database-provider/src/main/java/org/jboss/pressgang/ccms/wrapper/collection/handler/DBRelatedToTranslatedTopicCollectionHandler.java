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

import javax.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Topic;
import org.jboss.pressgang.ccms.model.TranslatedTopicData;

public class DBRelatedToTranslatedTopicCollectionHandler implements DBCollectionHandler<TranslatedTopicData> {
    private final EntityManager entityManager;
    private Topic parent;

    public DBRelatedToTranslatedTopicCollectionHandler(final Topic parent, final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<TranslatedTopicData> items, TranslatedTopicData entity) {
        parent.addRelationshipTo(entityManager, entity.getTranslatedTopic().getTopicId(), 1);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<TranslatedTopicData> items, TranslatedTopicData entity) {
        parent.removeRelationshipTo(entity.getTranslatedTopic().getTopicId(), 1);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
