package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.model.interfaces.HasCSNodes;

public class DBCSNodeCollectionHandler implements DBUpdateableCollectionHandler<CSNode> {
    private HasCSNodes parent;

    public DBCSNodeCollectionHandler(final HasCSNodes parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(final CSNode entity) {
    }

    @Override
    public void addItem(final CSNode entity) {
        parent.addChild(entity);
    }

    @Override
    public void removeItem(final CSNode entity) {
        parent.removeChild(entity);
    }
}