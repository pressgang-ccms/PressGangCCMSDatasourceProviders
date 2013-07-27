package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryInTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBTagWrapper extends DBBaseWrapper<TagWrapper, Tag> implements TagWrapper {
    private final CategoryCollectionEventListener categoryCollectionEventListener = new CategoryCollectionEventListener();

    private final Tag tag;

    public DBTagWrapper(final DBProviderFactory providerFactory, final Tag tag, boolean isRevision) {
        super(providerFactory, isRevision, Tag.class);
        this.tag = tag;
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
    public Tag unwrap() {
        return tag;
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
                TagToCategory.class, isRevisionEntity(), CategoryInTagWrapper.class);
        final DBCategoryInTagCollectionWrapper dbCollection = (DBCategoryInTagCollectionWrapper) collection;
        dbCollection.registerEventListener(categoryCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories) {
        if (categories == null) return;
        final DBCategoryInTagCollectionWrapper dbCategories = (DBCategoryInTagCollectionWrapper) categories;
        dbCategories.registerEventListener(categoryCollectionEventListener);

        // Remove the current children
        final Set<TagToCategory> currentCategories = new HashSet<TagToCategory>(getEntity().getTagToCategories());
        for (final TagToCategory category : currentCategories) {
            getEntity().removeCategoryRelationship(category);
        }

        // Set the new children
        final Collection<TagToCategory> newLanguageImages = dbCategories.unwrap();
        for (final TagToCategory category : newLanguageImages) {
            getEntity().addCategoryRelationship(category);
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

    /**
     *
     */
    private class CategoryCollectionEventListener implements UpdateableCollectionEventListener<TagToCategory> {
        @Override
        public void onAddItem(final TagToCategory entity) {
            getEntity().addCategoryRelationship(entity);
        }

        @Override
        public void onRemoveItem(final TagToCategory entity) {
            getEntity().removeCategoryRelationship(entity);
        }

        @Override
        public void onUpdateItem(final TagToCategory entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CategoryCollectionEventListener;
        }
    }
}
