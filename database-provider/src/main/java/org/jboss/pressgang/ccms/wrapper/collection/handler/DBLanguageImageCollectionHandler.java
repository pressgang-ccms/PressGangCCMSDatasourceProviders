/*
  Copyright 2011-2014 Red Hat, Inc

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

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;

public class DBLanguageImageCollectionHandler implements DBUpdateableCollectionHandler<LanguageImage> {
    private ImageFile parent;

    public DBLanguageImageCollectionHandler(final ImageFile parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<LanguageImage> items, LanguageImage entity) {
    }

    @Override
    public void addItem(Collection<LanguageImage> items, final LanguageImage entity) {
        parent.addLanguageImage(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<LanguageImage> items, final LanguageImage entity) {
        parent.removeLanguageImage(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
