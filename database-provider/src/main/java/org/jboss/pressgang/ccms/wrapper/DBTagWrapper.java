package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTagWrapper extends DBBaseWrapper<TagWrapper> implements TagWrapper {

    private final Tag tag;

    public DBTagWrapper(final DBProviderFactory providerFactory, final Tag tag, boolean isRevision) {
        super(providerFactory, isRevision);
        this.tag = tag;
    }

    protected Tag getTag() {
        return tag;
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
    public Integer getRevision() {
        return (Integer) getTag().getRevision();
    }

    @Override
    public CollectionWrapper<TagWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getTag()), Tag.class,
                isRevisionEntity());
    }

    @Override
    public Tag unwrap() {
        return tag;
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
                TagToCategory.class, isRevisionEntity());
        return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
    }

    @Override
    public PropertyTagInTagWrapper getProperty(int propertyId) {
        return getWrapperFactory().create(getTag().getProperty(propertyId), isRevisionEntity());
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
