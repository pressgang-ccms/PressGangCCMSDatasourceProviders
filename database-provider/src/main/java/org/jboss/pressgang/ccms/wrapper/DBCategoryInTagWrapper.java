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

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagInCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTagInCategoryCollectionHandler;

public class DBCategoryInTagWrapper extends DBBaseEntityWrapper<CategoryInTagWrapper, TagToCategory> implements CategoryInTagWrapper {
    private final DBTagInCategoryCollectionHandler tagCollectionHandler;
    private final TagToCategory tagToCategory;

    public DBCategoryInTagWrapper(final DBProviderFactory providerFactory, final TagToCategory tagToCategory, boolean isRevision) {
        super(providerFactory, isRevision, CategoryInTagWrapper.class, TagToCategory.class);
        this.tagToCategory = tagToCategory;
        tagCollectionHandler = new DBTagInCategoryCollectionHandler(tagToCategory.getCategory());
    }

    protected Category getCategory() {
        return getEntity().getCategory();
    }

    protected Tag getTag() {
        return getEntity().getTag();
    }

    @Override
    protected TagToCategory getEntity() {
        return tagToCategory;
    }

    @Override
    public Integer getId() {
        return getCategory().getId();
    }

    @Override
    public void setId(Integer id) {
        getCategory().setCategoryId(id);
    }

    @Override
    public String getName() {
        return getCategory().getCategoryName();
    }

    @Override
    public void setName(String name) {
        getCategory().setCategoryName(name);
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getTagToCategoryId();
    }

    @Override
    public Integer getRelationshipSort() {
        return getEntity().getSorting();
    }

    @Override
    public boolean isMutuallyExclusive() {
        return getCategory().isMutuallyExclusive();
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getTags() {
        final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(getCategory().getTagToCategories(),
                TagToCategory.class, isRevisionEntity(), TagInCategoryWrapper.class, tagCollectionHandler);
        return (UpdateableCollectionWrapper<TagInCategoryWrapper>) collection;
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        if (tags == null) return;
        final DBTagInCategoryCollectionWrapper dbTags = (DBTagInCategoryCollectionWrapper) tags;
        dbTags.setHandler(tagCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbTags.unwrap() != getCategory().getTagToCategories()) {
            // Add new tags and skip any existing tags
            final Set<TagToCategory> currentTags = new HashSet<TagToCategory>(getCategory().getTagToCategories());
            final Collection<TagToCategory> newTags = dbTags.unwrap();
            for (final TagToCategory tag : newTags) {
                if (currentTags.contains(tag)) {
                    currentTags.remove(tag);
                    continue;
                } else {
                    tag.setCategory(getCategory());
                    getCategory().addTag(tag);
                }
            }

            // Remove tags that should no longer exist in the collection
            for (final TagToCategory removeTag : currentTags) {
                getCategory().removeTag(removeTag);
            }
        }
    }
}
