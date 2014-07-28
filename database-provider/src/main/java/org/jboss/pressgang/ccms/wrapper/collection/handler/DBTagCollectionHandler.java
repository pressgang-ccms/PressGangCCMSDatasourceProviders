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

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.interfaces.HasTags;

public class DBTagCollectionHandler implements DBCollectionHandler<Tag> {
    private HasTags parent;

    public DBTagCollectionHandler(final HasTags parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Tag> items, final Tag entity) {
        parent.addTag(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Tag> items, final Tag entity) {
        parent.removeTag(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
