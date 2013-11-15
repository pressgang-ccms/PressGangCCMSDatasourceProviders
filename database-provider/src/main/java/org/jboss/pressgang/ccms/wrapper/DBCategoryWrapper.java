package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBTagInCategoryCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBTagInCategoryCollectionHandler;

public class DBCategoryWrapper extends DBBaseEntityWrapper<CategoryWrapper, Category> implements CategoryWrapper {
    private final DBTagInCategoryCollectionHandler tagCollectionHandler;
    private final Category category;

    public DBCategoryWrapper(final DBProviderFactory providerFactory, final Category category, boolean isRevision) {
        super(providerFactory, isRevision, Category.class);
        this.category = category;
        tagCollectionHandler = new DBTagInCategoryCollectionHandler(category);
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
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getTags() {
        final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(getEntity().getTagToCategories(),
                TagToCategory.class, isRevisionEntity(), TagInCategoryWrapper.class, tagCollectionHandler);
        return (UpdateableCollectionWrapper<TagInCategoryWrapper>) collection;
    }

    @Override
    public void setTags(UpdateableCollectionWrapper<TagInCategoryWrapper> tags) {
        if (tags == null) return;
        final DBTagInCategoryCollectionWrapper dbTags = (DBTagInCategoryCollectionWrapper) tags;
        dbTags.setHandler(tagCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbTags.unwrap() != getEntity().getTagToCategories()) {
            // Add new tags and skip any existing tags
            final Set<TagToCategory> currentTags = new HashSet<TagToCategory>(getEntity().getTagToCategories());
            final Collection<TagToCategory> newTags = dbTags.unwrap();
            for (final TagToCategory tag : newTags) {
                if (currentTags.contains(tag)) {
                    currentTags.remove(tag);
                    continue;
                } else {
                    tag.setCategory(getEntity());
                    getEntity().addTag(tag);
                }
            }

            // Remove tags that should no longer exist in the collection
            for (final TagToCategory removeTag : currentTags) {
                getEntity().removeTag(removeTag);
            }
        }
    }
}
