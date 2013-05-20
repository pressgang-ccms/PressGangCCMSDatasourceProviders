package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.DBCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCategoryProvider extends DBDataProvider implements CategoryProvider {
    protected DBCategoryProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
    }

    @Override
    public CategoryWrapper getCategory(int id) {
        return getWrapperFactory().create(getEntity(Category.class, id), false);
    }

    @Override
    public CategoryWrapper getCategory(int id, Integer revision) {
        if (revision == null) {
            return getCategory(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(Category.class, id, revision), true);
        }
    }

    @Override
    public UpdateableCollectionWrapper<TagInCategoryWrapper> getCategoryTags(int id, Integer revision) {
        final DBCategoryWrapper category = (DBCategoryWrapper) getCategory(id, revision);
        if (category == null) {
            return null;
        } else {
            final CollectionWrapper<TagInCategoryWrapper> collection = getWrapperFactory().createCollection(
                    category.unwrap().getTagToCategories(), TagToCategory.class, revision != null, TagInCategoryWrapper.class);
            return (UpdateableCollectionWrapper<TagInCategoryWrapper>) collection;
        }
    }

    @Override
    public CollectionWrapper<CategoryWrapper> getCategoryRevisions(int id, Integer revision) {
        final List<Category> revisions = getRevisionList(Category.class, id);
        return getWrapperFactory().createCollection(revisions, Category.class, revision != null);
    }
}
