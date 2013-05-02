package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTagInCategoryWrapper extends DBBaseWrapper<TagInCategoryWrapper, TagToCategory> implements TagInCategoryWrapper {

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
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
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
