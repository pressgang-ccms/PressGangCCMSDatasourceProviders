package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.model.interfaces.HasTags;

public class DBTagCollectionHandler implements DBCollectionHandler<Tag> {
    private HasTags parent;

    public DBTagCollectionHandler(final HasTags parent) {
        this.parent = parent;
    }

    @Override
    public void addItem(final Tag entity) {
        parent.addTag(entity);
    }

    @Override
    public void removeItem(final Tag entity) {
        parent.removeTag(entity);
    }

    public void setParent(final HasTags parent) {
        this.parent = parent;
    }
}
