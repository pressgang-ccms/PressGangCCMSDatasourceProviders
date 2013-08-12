package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;

public class DBCSBookTagCollectionHandler implements DBCollectionHandler<Tag> {
    private ContentSpec parent;

    public DBCSBookTagCollectionHandler(final ContentSpec parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(Collection<Tag> items, final Tag entity) {
        parent.addBookTag(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<Tag> items, final Tag entity) {
        parent.removeBookTag(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
