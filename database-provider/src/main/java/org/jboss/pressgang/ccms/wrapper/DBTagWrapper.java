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

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryInTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBCategoryInTagCollectionHandler;

public class DBTagWrapper extends DBBaseEntityWrapper<TagWrapper, Tag> implements TagWrapper {
    private final DBCategoryInTagCollectionHandler categoryCollectionHandler;
    private final Tag tag;

    public DBTagWrapper(final DBProviderFactory providerFactory, final Tag tag, boolean isRevision) {
        super(providerFactory, isRevision, Tag.class);
        this.tag = tag;
        categoryCollectionHandler = new DBCategoryInTagCollectionHandler(tag);
    }

    @Override
    protected Tag getEntity() {
        return tag;
    }

    @Override
    public Integer getId() {
        return getEntity().getId();
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTagId(id);
    }

    @Override
    public String getName() {
        return getEntity().getTagName();
    }

    @Override
    public CollectionWrapper<TagWrapper> getParentTags() {
        return getWrapperFactory().createCollection(getEntity().getParentTags(), Tag.class, isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TagWrapper> getChildTags() {
        return getWrapperFactory().createCollection(getEntity().getChildTags(), Tag.class, isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getCategories() {
        final CollectionWrapper<CategoryInTagWrapper> collection = getWrapperFactory().createCollection(getEntity().getTagToCategories(),
                TagToCategory.class, isRevisionEntity(), CategoryInTagWrapper.class, categoryCollectionHandler);
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
    }

    @Override
    public void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories) {
        if (categories == null) return;
        final DBCategoryInTagCollectionWrapper dbCategories = (DBCategoryInTagCollectionWrapper) categories;
        dbCategories.setHandler(categoryCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbCategories.unwrap() != getEntity().getTagToCategories()) {
            // Add new categories and skip any existing categories
            final Set<TagToCategory> currentCategories = new HashSet<TagToCategory>(getEntity().getTagToCategories());
            final Collection<TagToCategory> newCategories = dbCategories.unwrap();
            for (final TagToCategory category : newCategories) {
                if (currentCategories.contains(category)) {
                    currentCategories.remove(category);
                    continue;
                } else {
                    category.setTag(getEntity());
                    getEntity().addCategory(category);
                }
            }

            // Remove categories that should no longer exist in the collection
            for (final TagToCategory removeCategory : currentCategories) {
                getEntity().removeCategory(removeCategory);
            }
        }
    }

    @Override
    public PropertyTagInTagWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getEntity().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public boolean containedInCategory(int categoryId) {
        return getEntity().isInCategory(categoryId);
    }

    @Override
    public boolean containedInCategories(List<Integer> categoryIds) {
        if (categoryIds == null) return false;

        for (final Integer categoryId : categoryIds) {
            if (getEntity().isInCategory(categoryId)) {
                return true;
            }
        }

        return false;
    }
}
