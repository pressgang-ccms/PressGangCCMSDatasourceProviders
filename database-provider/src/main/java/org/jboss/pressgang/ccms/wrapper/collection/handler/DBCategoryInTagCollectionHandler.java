package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;

public class DBCategoryInTagCollectionHandler implements DBUpdateableCollectionHandler<TagToCategory> {
    private Tag parent;

    public DBCategoryInTagCollectionHandler(final Tag parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(final TagToCategory entity) {
    }

    @Override
    public void addItem(final TagToCategory entity) {
        entity.setTag(parent);
        parent.addCategory(entity);
    }

    @Override
    public void removeItem(final TagToCategory entity) {
        parent.removeCategory(entity);
    }
}