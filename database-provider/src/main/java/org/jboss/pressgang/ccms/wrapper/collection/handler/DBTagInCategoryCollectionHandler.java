package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;

public class DBTagInCategoryCollectionHandler implements DBUpdateableCollectionHandler<TagToCategory> {
    private Category parent;

    public DBTagInCategoryCollectionHandler(final Category parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<TagToCategory> items, final TagToCategory entity) {
    }

    @Override
    public void addItem(Collection<TagToCategory> items, final TagToCategory entity) {
        entity.setCategory(parent);
        parent.addTag(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<TagToCategory> items, final TagToCategory entity) {
        parent.removeTag(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}