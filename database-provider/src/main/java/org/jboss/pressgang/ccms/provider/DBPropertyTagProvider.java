package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;
import org.jboss.pressgang.ccms.model.TagToPropertyTag;
import org.jboss.pressgang.ccms.model.TopicToPropertyTag;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.wrapper.ContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInPropertyCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTagWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagInTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.wrapper.base.BaseTopicWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBPropertyTagProvider extends DBDataProvider implements PropertyTagProvider {
    protected DBPropertyTagProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public PropertyTagWrapper getPropertyTag(int id) {
        return getWrapperFactory().create(getEntity(PropertyTag.class, id), false);
    }

    @Override
    public PropertyTagWrapper getPropertyTag(int id, Integer revision) {
        if (revision == null) {
            return getPropertyTag(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(PropertyTag.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> getPropertyTagRevisions(int id, Integer revision) {
        final List<PropertyTag> revisions = getRevisionList(PropertyTag.class, id);
        return getWrapperFactory().createCollection(revisions, PropertyTag.class, revision != null);
    }

    @Override
    public PropertyTagWrapper newPropertyTag() {
        return getWrapperFactory().create(new PropertyTag(), false);
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final BaseTopicWrapper<?> topic) {
        return getWrapperFactory().create(new TopicToPropertyTag(), false);
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final TagWrapper tag) {
        return getWrapperFactory().create(new TagToPropertyTag(), false);
    }

    @Override
    public PropertyTagInTopicWrapper newPropertyTagInTopic(final PropertyTagWrapper propertyTag, final BaseTopicWrapper<?> topic) {
        final TopicToPropertyTag ttp = new TopicToPropertyTag();
        ttp.setPropertyTag((PropertyTag) propertyTag.unwrap());
        return getWrapperFactory().create(ttp, false);
    }

    @Override
    public PropertyTagInTagWrapper newPropertyTagInTag(final PropertyTagWrapper propertyTag, final TagWrapper tag) {
        final TagToPropertyTag ttp = new TagToPropertyTag();
        ttp.setPropertyTag((PropertyTag) propertyTag.unwrap());
        return getWrapperFactory().create(ttp, false);
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(final ContentSpecWrapper contentSpec) {
        return getWrapperFactory().create(new ContentSpecToPropertyTag(), false);
    }

    @Override
    public PropertyTagInContentSpecWrapper newPropertyTagInContentSpec(PropertyTagWrapper propertyTag,
            final ContentSpecWrapper contentSpec) {
        final ContentSpecToPropertyTag cstp = new ContentSpecToPropertyTag();
        cstp.setPropertyTag((PropertyTag) propertyTag.unwrap());
        return getWrapperFactory().create(cstp, false);
    }

    @Override
    public PropertyTagInPropertyCategoryWrapper newPropertyTagInPropertyCategory() {
        return getWrapperFactory().create(new PropertyTagToPropertyTagCategory(), false);
    }

    @Override
    public CollectionWrapper<PropertyTagWrapper> newPropertyTagCollection() {
        return getWrapperFactory().createCollection(new ArrayList<PropertyTag>(), PropertyTag.class, false);
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTopicWrapper> newPropertyTagInTopicCollection(final BaseTopicWrapper<?> topic) {
        final CollectionWrapper<PropertyTagInTopicWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TopicToPropertyTag>(), TopicToPropertyTag.class, false);
        return (UpdateableCollectionWrapper<PropertyTagInTopicWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInTagWrapper> newPropertyTagInTagCollection(final TagWrapper tag) {
        final CollectionWrapper<PropertyTagInTagWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<TagToPropertyTag>(), TagToPropertyTag.class, false);
        return (UpdateableCollectionWrapper<PropertyTagInTagWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper> newPropertyTagInContentSpecCollection(
            final ContentSpecWrapper contentSpec) {
        final CollectionWrapper<PropertyTagInContentSpecWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<ContentSpecToPropertyTag>(), ContentSpecToPropertyTag.class, false);
        return (UpdateableCollectionWrapper<PropertyTagInContentSpecWrapper>) collection;
    }

    @Override
    public UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper> newPropertyTagInPropertyCategoryCollection() {
        final CollectionWrapper<PropertyTagInPropertyCategoryWrapper> collection = getWrapperFactory().createCollection(
                new ArrayList<PropertyTagToPropertyTagCategory>(), PropertyTagToPropertyTagCategory.class, false);
        return (UpdateableCollectionWrapper<PropertyTagInPropertyCategoryWrapper>) collection;
    }
}
