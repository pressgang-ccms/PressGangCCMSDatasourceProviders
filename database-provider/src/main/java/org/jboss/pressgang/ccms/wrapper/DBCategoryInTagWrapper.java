package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagInCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.base.UpdateableCollectionEventListener;

public class DBCategoryInTagWrapper extends DBBaseWrapper<CategoryInTagWrapper, TagToCategory> implements CategoryInTagWrapper {
    private final TagCollectionEventListener tagCollectionListener = new TagCollectionEventListener();
    private final TagToCategory tagToCategory;

    public DBCategoryInTagWrapper(final DBProviderFactory providerFactory, final TagToCategory category, boolean isRevision) {
        super(providerFactory, isRevision, CategoryInTagWrapper.class, TagToCategory.class);
        this.tagToCategory = category;
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
    public TagToCategory unwrap() {
        return tagToCategory;
    }

    @Override
    public boolean isRevisionEntity() {
        return getEntity().getRevision() != null;
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getTags() {
        final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(getCategory().getTagToCategories(),
                TagToCategory.class, isRevisionEntity());
        final DBTagInCategoryCollectionWrapper dbCollection = (DBTagInCategoryCollectionWrapper) collection;
        dbCollection.registerEventListener(tagCollectionListener);

        return dbCollection;
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        if (tags == null) return;
        final DBTagInCategoryCollectionWrapper dbTags = (DBTagInCategoryCollectionWrapper) tags;
        dbTags.registerEventListener(tagCollectionListener);

        // Remove the current tags
        final Set<TagToCategory> currentTags = getCategory().getTagToCategories();
        for (final TagToCategory tag : currentTags) {
            getCategory().removeTagRelationship(tag);
        }

        // Set the new tags
        final Collection<TagToCategory> newTags = dbTags.unwrap();
        for (final TagToCategory tag : newTags) {
            tag.setCategory(getCategory());
            getCategory().addTagRelationship(tag);
        }
    }

    /**
     *
     */
    private class TagCollectionEventListener implements UpdateableCollectionEventListener<TagToCategory> {
        @Override
        public void onAddItem(TagToCategory entity) {
            entity.setCategory(getCategory());
            getCategory().addTagRelationship(entity);
        }

        @Override
        public void onRemoveItem(TagToCategory entity) {
            getCategory().removeTagRelationship(entity);
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
