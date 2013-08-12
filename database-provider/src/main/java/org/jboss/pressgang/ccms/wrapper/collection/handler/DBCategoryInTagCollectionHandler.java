package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.TagToCategory;

public class DBCategoryInTagCollectionHandler implements DBUpdateableCollectionHandler<TagToCategory> {
    private Tag parent;

    public DBCategoryInTagCollectionHandler(final Tag parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<TagToCategory> items, final TagToCategory entity) {
    }

    @Override
    public void addItem(Collection<TagToCategory> items, final TagToCategory entity) {
        entity.setTag(parent);
        parent.addCategory(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<TagToCategory> items, final TagToCategory entity) {
        parent.removeCategory(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}