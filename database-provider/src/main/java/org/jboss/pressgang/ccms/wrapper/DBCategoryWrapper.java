package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagInCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBCategoryWrapper extends DBBaseWrapper<CategoryWrapper, Category> implements CategoryWrapper {
    private final TagCollectionEventListener tagCollectionEventListener = new TagCollectionEventListener();
    private final Category category;

    public DBCategoryWrapper(final DBProviderFactory providerFactory, final Category category, boolean isRevision) {
        super(providerFactory, isRevision, Category.class);
        this.category = category;
    }

    @Override
    protected Category getEntity() {
        return category;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setCategoryId(id);
    }

    @Override
    public String getName() {
        return getEntity().getCategoryName();
    }

    @Override
    public void setName(String name) {
        getEntity().setCategoryName(name);
    }

    @Override
    public boolean isMutuallyExclusive() {
        return getEntity().isMutuallyExclusive();
    }

    @Override
    public Category unwrap() {
        return category;
    }

    @Override
    public boolean isRevisionEntity() {
        return getEntity().getRevision() != null;
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getTags() {
        final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(getEntity().getTagToCategories(),
                TagToCategory.class, isRevisionEntity());
        final DBTagInCategoryCollectionWrapper dbCollection = (DBTagInCategoryCollectionWrapper) collection;
        dbCollection.registerEventListener(tagCollectionEventListener);
        return dbCollection;
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        if (tags == null) return;
        final DBTagInCategoryCollectionWrapper dbTags = (DBTagInCategoryCollectionWrapper) tags;
        dbTags.registerEventListener(tagCollectionEventListener);

        // Remove the current tags
        final Set<TagToCategory> currentTags = new HashSet<TagToCategory>(getEntity().getTagToCategories());
        for (final TagToCategory tag : currentTags) {
            getEntity().removeTagRelationship(tag);
        }

        // Set the new tags
        final Collection<TagToCategory> newTags = dbTags.unwrap();
        for (final TagToCategory tag : newTags) {
            tag.setCategory(getEntity());
            getEntity().addTagRelationship(tag);
        }
    }

    /**
     *
     */
    private class TagCollectionEventListener implements UpdateableCollectionEventListener<TagToCategory> {
        @Override
        public void onAddItem(TagToCategory entity) {
            entity.setCategory(getEntity());
            getEntity().addTagRelationship(entity);
        }

        @Override
        public void onRemoveItem(TagToCategory entity) {
            getEntity().removeTagRelationship(entity);
        }

        @Override
        public void onUpdateItem(TagToCategory entity) {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof TagCollectionEventListener;
        }
    }
}
