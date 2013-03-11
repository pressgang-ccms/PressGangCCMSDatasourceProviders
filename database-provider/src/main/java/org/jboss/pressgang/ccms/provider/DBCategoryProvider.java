package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.CategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.DBCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagInCategoryWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class DBCategoryProvider extends DBDataProvider implements CategoryProvider {
    protected DBCategoryProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public CategoryWrapper getCategory(int id) {
        final Category category = getEntityManager().find(Category.class, id);
        return getWrapperFactory().create(category, false);
    }

    @Override
    public CategoryWrapper getCategory(int id, Integer revision) {
        if (revision == null) {
            return getCategory(id);
        } else {
            final Category dummyCategory = new Category();
            dummyCategory.setCategoryId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyCategory, revision), true);
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
        final Category category = new Category();
        category.setCategoryId(id);
        final Map<Number, Category> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), category);

        final List<Category> revisions = new ArrayList<Category>();
        for (final Map.Entry<Number, Category> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, Category.class, revision != null);
    }
}
