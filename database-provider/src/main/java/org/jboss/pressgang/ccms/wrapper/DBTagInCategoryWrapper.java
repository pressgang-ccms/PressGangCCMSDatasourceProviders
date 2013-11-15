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

public class DBTagInCategoryWrapper extends DBBaseEntityWrapper<TagInCategoryWrapper, TagToCategory> implements TagInCategoryWrapper {
    private final DBCategoryInTagCollectionHandler categoryCollectionHandler;

    private final TagToCategory tagToCategory;

    public DBTagInCategoryWrapper(final DBProviderFactory providerFactory, final TagToCategory tagToCategory, boolean isRevision) {
        super(providerFactory, isRevision, TagInCategoryWrapper.class, TagToCategory.class);
        this.tagToCategory = tagToCategory;
        categoryCollectionHandler = new DBCategoryInTagCollectionHandler(tagToCategory.getTag());
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
                TagToCategory.class, isRevisionEntity(), CategoryInTagWrapper.class, categoryCollectionHandler);
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
    }

    @Override
    public void setCategories(UpdateableCollectionWrapper<CategoryInTagWrapper> categories) {
        if (categories == null) return;
        final DBCategoryInTagCollectionWrapper dbCategories = (DBCategoryInTagCollectionWrapper) categories;
        dbCategories.setHandler(categoryCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbCategories.unwrap() != getTag().getTagToCategories()) {
            // Add new categories and skip any existing categories
            final Set<TagToCategory> currentCategories = new HashSet<TagToCategory>(getTag().getTagToCategories());
            final Collection<TagToCategory> newCategories = dbCategories.unwrap();
            for (final TagToCategory category : newCategories) {
                if (currentCategories.contains(category)) {
                    currentCategories.remove(category);
                    continue;
                } else {
                    category.setTag(getTag());
                    getTag().addCategory(category);
                }
            }

            // Remove categories that should no longer exist in the collection
            for (final TagToCategory removeCategory : currentCategories) {
                getTag().removeCategory(removeCategory);
            }
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
}
