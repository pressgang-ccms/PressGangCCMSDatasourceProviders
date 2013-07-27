package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBCategoryInTagCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBTagInCategoryWrapper extends DBBaseWrapper<TagInCategoryWrapper, TagToCategory> implements TagInCategoryWrapper {
    private final CategoryCollectionEventListener categoryCollectionEventListener = new CategoryCollectionEventListener();

    private final TagToCategory tagToCategory;

    public DBTagInCategoryWrapper(final DBProviderFactory providerFactory, final TagToCategory tagToCategory, boolean isRevision) {
        super(providerFactory, isRevision, TagInCategoryWrapper.class, TagToCategory.class);
        this.tagToCategory = tagToCategory;
    }

    @Override
    protected TagToCategory getEntity() {
        return tagToCategory;
    }

    protected Tag getTag() {
        return getEntity().getTag();
    }

    @Override
    public Integer getId() {
        return getTag().getId();
    }

    @Override
    public void setId(Integer id) {
        getTag().setTagId(id);
    }

    @Override
    public TagToCategory unwrap() {
        return tagToCategory;
    }

    @Override
    public String getName() {
        return getTag().getTagName();
    }

    @Override
    public CollectionWrapper<TagWrapper> getParentTags() {
        return getWrapperFactory().createCollection(getTag().getParentTags(), Tag.class, isRevisionEntity());
    }

    @Override
    public CollectionWrapper<TagWrapper> getChildTags() {
        return getWrapperFactory().createCollection(getTag().getChildTags(), Tag.class, isRevisionEntity());
    }

    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getCategories() {
        final CollectionWrapper<CategoryInTagWrapper> collection = getWrapperFactory().createCollection(getTag().getTagToCategories(),
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
        final Set<TagToCategory> currentCategories = getTag().getTagToCategories();
        for (final TagToCategory category : currentCategories) {
            getTag().removeCategoryRelationship(category);
        }

        // Set the new children
        final Collection<TagToCategory> newLanguageImages = dbCategories.unwrap();
        for (final TagToCategory category : newLanguageImages) {
            getTag().addCategoryRelationship(category);
        }
    }

    @Override
    public PropertyTagInTagWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getTag().getProperty(propertyId), isRevisionEntity());
    }

    @Override
    public Integer getInCategorySort() {
        return getEntity().getSorting();
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }

    @Override
    public boolean containedInCategory(int categoryId) {
        return getTag().isInCategory(categoryId);
    }

    @Override
    public boolean containedInCategories(List<Integer> categoryIds) {
        if (categoryIds == null) return false;

        for (final Integer categoryId : categoryIds) {
            if (getTag().isInCategory(categoryId)) {
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
            getTag().addCategoryRelationship(entity);
        }

        @Override
        public void onRemoveItem(final TagToCategory entity) {
            getTag().removeCategoryRelationship(entity);
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
