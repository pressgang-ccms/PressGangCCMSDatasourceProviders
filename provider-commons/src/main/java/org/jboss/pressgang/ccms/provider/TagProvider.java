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

package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TagProvider {
    TagWrapper getTag(final int id);

    TagWrapper getTag(final int id, Integer revision);

    TagWrapper getTagByName(final String name);

    CollectionWrapper<TagWrapper> getTagChildTags(int id, Integer revision);

    CollectionWrapper<TagWrapper> getTagParentTags(int id, Integer revision);

    UpdateableCollectionWrapper<PropertyTagInTagWrapper> getTagProperties(int id, Integer revision);

    CollectionWrapper<TagWrapper> getTagRevisions(int id, Integer revision);

    TagWrapper newTag();

    TagInCategoryWrapper newTagInCategory(CategoryWrapper parent);

    CollectionWrapper<TagWrapper> newTagCollection();

    CollectionWrapper<TagInCategoryWrapper> newTagInCategoryCollection(CategoryWrapper parent);
}
