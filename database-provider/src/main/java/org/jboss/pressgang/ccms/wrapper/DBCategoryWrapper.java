package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCategoryWrapper extends DBBaseWrapper<CategoryWrapper, Category> implements CategoryWrapper {
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
            getEntity().addTagRelationship((TagToCategory) addTag.unwrap());
        }

        // Remove Tags
        for (final TagInCategoryWrapper removeTag : removeTags) {
            getEntity().removeTagRelationship((TagToCategory) removeTag.unwrap());
        }
    }
}
