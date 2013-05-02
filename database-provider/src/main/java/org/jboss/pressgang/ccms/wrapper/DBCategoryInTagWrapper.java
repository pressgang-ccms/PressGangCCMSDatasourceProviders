package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCategoryInTagWrapper extends DBBaseWrapper<CategoryInTagWrapper, TagToCategory> implements CategoryInTagWrapper {

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
        return (UpdateableCollectionWrapper<TagInCategoryWrapper>) collection;
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        if (tags == null) return;

        final List<TagInCategoryWrapper> addTags = tags.getAddItems();
        final List<TagInCategoryWrapper> removeTags = tags.getRemoveItems();
        /*
         * There is no need to do update tags as when the original entities are alter they will automatically be updated when using
         * database entities.
         */
        //final List<TagInCategoryWrapper> updateTags = tags.getUpdateItems();

        // Add Tags
        for (final TagInCategoryWrapper addTag : addTags) {
            getCategory().addTagRelationship((TagToCategory) addTag.unwrap());
        }

        // Remove Tags
        for (final TagInCategoryWrapper removeTag : removeTags) {
            getCategory().removeTagRelationship((TagToCategory) removeTag.unwrap());
        }
    }

}
