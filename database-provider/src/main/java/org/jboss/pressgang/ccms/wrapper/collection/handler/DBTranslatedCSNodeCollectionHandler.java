package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;

public class DBTranslatedCSNodeCollectionHandler implements DBUpdateableCollectionHandler<TranslatedCSNode> {
    private TranslatedContentSpec parent;

    public DBTranslatedCSNodeCollectionHandler(final TranslatedContentSpec parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(TranslatedCSNode entity) {
    }

    @Override
    public void addItem(final TranslatedCSNode entity) {
        parent.addTranslatedNode(entity);
    }

    @Override
    public void removeItem(final TranslatedCSNode entity) {
        parent.removeTranslatedNode(entity);
    }
}
