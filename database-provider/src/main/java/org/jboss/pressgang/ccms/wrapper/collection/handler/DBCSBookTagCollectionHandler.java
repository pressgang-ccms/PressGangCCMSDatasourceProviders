package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.contentspec.ContentSpec;

public class DBCSBookTagCollectionHandler implements DBCollectionHandler<Tag> {
    private ContentSpec parent;

    public DBCSBookTagCollectionHandler(final ContentSpec parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(final Tag entity) {
        parent.addBookTag(entity);
    }

    @Override
    public void removeItem(final Tag entity) {
        parent.removeBookTag(entity);
    }
}
