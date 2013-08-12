package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.Category;
import org.jboss.pressgang.ccms.model.TagToCategory;

public class DBTagInCategoryCollectionHandler implements DBUpdateableCollectionHandler<TagToCategory> {
    private Category parent;

    public DBTagInCategoryCollectionHandler(final Category parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(final TagToCategory entity) {
    }

    @Override
    public void addItem(final TagToCategory entity) {
        entity.setCategory(parent);
        parent.addTag(entity);
    }

    @Override
    public void removeItem(final TagToCategory entity) {
        parent.removeTag(entity);
    }

    public void setParent(final Category parent) {
        this.parent = parent;
    }
}