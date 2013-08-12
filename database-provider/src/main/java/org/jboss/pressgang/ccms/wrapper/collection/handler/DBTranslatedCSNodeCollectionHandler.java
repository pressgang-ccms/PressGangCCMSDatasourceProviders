package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.model.contentspec.TranslatedContentSpec;

public class DBTranslatedCSNodeCollectionHandler implements DBUpdateableCollectionHandler<TranslatedCSNode> {
    private TranslatedContentSpec parent;

    public DBTranslatedCSNodeCollectionHandler(final TranslatedContentSpec parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<TranslatedCSNode> items, TranslatedCSNode entity) {
    }

    @Override
    public void addItem(Collection<TranslatedCSNode> items, final TranslatedCSNode entity) {
        parent.addTranslatedNode(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<TranslatedCSNode> items, final TranslatedCSNode entity) {
        parent.removeTranslatedNode(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
