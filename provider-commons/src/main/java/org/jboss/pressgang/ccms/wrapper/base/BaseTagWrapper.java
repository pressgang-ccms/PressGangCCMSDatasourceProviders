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

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface BaseTagWrapper<T extends BaseTagWrapper<T>> extends EntityWrapper<T> {
    String getName();

    CollectionWrapper<TagWrapper> getParentTags();

    CollectionWrapper<TagWrapper> getChildTags();

    UpdateableCollectionWrapper<CategoryInTagWrapper> getCategories();

    void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories);

    PropertyTagInTagWrapper getProperty(final int propertyId);

    boolean containedInCategory(int categoryId);

    boolean containedInCategories(List<Integer> categoryIds);
}
