package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTagWrapper extends DBBaseWrapper<TagWrapper, Tag> implements TagWrapper {

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
                TagToCategory.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
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
