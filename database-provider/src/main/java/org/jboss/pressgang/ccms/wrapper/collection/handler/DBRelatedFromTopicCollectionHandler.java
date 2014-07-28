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

import org.jboss.pressgang.ccms.model.RelationshipTag;
import org.jboss.pressgang.ccms.model.Topic;

public class DBRelatedFromTopicCollectionHandler implements DBCollectionHandler<Topic> {
    private final static RelationshipTag dummyRelationshipTag = new RelationshipTag();
    static {
        dummyRelationshipTag.setRelationshipTagId(1);
    }

    private Topic parent;

    public DBRelatedFromTopicCollectionHandler(final Topic parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Topic> items, Topic entity) {
        parent.addRelationshipFrom(entity, dummyRelationshipTag);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Topic> items, Topic entity) {
        parent.removeRelationshipFrom(entity, dummyRelationshipTag);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
