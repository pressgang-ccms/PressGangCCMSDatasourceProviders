package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.interfaces.HasTags;

public class DBTagCollectionHandler implements DBCollectionHandler<Tag> {
    private HasTags parent;

    public DBTagCollectionHandler(final HasTags parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Tag> items, final Tag entity) {
        parent.addTag(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Tag> items, final Tag entity) {
        parent.removeTag(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
