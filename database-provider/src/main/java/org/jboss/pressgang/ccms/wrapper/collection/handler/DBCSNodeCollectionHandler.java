package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.interfaces.HasCSNodes;

public class DBCSNodeCollectionHandler implements DBUpdateableCollectionHandler<CSNode> {
    private HasCSNodes parent;

    public DBCSNodeCollectionHandler(final HasCSNodes parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<CSNode> items, final CSNode entity) {
    }

    @Override
    public void addItem(Collection<CSNode> items, final CSNode entity) {
        parent.addChild(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<CSNode> items, final CSNode entity) {
        parent.removeChild(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}