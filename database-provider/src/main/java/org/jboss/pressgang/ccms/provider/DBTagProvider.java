package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.CategoryInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.DBTagWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBTagProvider extends DBDataProvider implements TagProvider {
    protected DBTagProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
    }

    @Override
    public TagWrapper getTag(int id) {
        return getWrapperFactory().create(getEntity(Tag.class, id), false);
    }

    @Override
    public TagWrapper getTag(int id, Integer revision) {
        if (revision == null) {
            return getTag(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(Tag.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagsByName(final String name) {
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Tag> tags = criteriaBuilder.createQuery(Tag.class);
        final Root<Tag> from = tags.from(Tag.class);
        tags.select(from);
        tags.where(criteriaBuilder.equal(from.get("tagName"), name));

        return getWrapperFactory().createCollection(executeQuery(tags), Tag.class, false);
    }

    @Override
    public TagWrapper getTagByName(final String name) {
        final CollectionWrapper<TagWrapper> tags = getTagsByName(name);
        if (tags != null && !tags.isEmpty()) {
            for (final TagWrapper tag : tags.getItems()) {
                if (tag.getName().equals(name)) {
                    return tag;
                }
            }
        }

        return null;
    }

    @Override
    public UpdateableCollectionWrapper<CategoryInTagWrapper> getTagCategories(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            final CollectionWrapper<CategoryInTagWrapper> collection = getWrapperFactory().createCollection(
                    tag.unwrap().getTagToCategories(), TagToCategory.class, revision != null, CategoryInTagWrapper.class);
            return (UpdateableCollectionWrapper<CategoryInTagWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagChildTags(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(tag.unwrap().getChildTags(), Tag.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagParentTags(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(tag.unwrap().getParentTags(), Tag.class, revision != null);
        }
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> getTagProperties(int id, Integer revision) {
        final DBTagWrapper tag = (DBTagWrapper) getTag(id, revision);
        if (tag == null) {
            return null;
        } else {
            final CollectionWrapper<PropertyTagInTagWrapper> collection = getWrapperFactory().createCollection(
                    tag.unwrap().getTagToPropertyTags(), TagToPropertyTag.class, revision != null);
            return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<TagWrapper> getTagRevisions(int id, Integer revision) {
        final List<Tag> revisions = getRevisionList(Tag.class, id);
        return getWrapperFactory().createCollection(revisions, Tag.class, revision != null);
    }

    @Override
    public TagWrapper newTag() {
        return getWrapperFactory().create(new Tag(), false);
    }

    @Override
    public TagInCategoryWrapper newTagInCategory() {
        return getWrapperFactory().create(new TagToCategory(), false);
    }

    @Override
    public CollectionWrapper<TagWrapper> newTagCollection() {
        return getWrapperFactory().createCollection(new ArrayList<Tag>(), Tag.class, false);
    }

    @Override
    public CollectionWrapper<TagInCategoryWrapper> newTagInCategoryCollection() {
        return getWrapperFactory().createCollection(new ArrayList<TagToCategory>(), TagToCategory.class, false, TagInCategoryWrapper.class);
    }
}
