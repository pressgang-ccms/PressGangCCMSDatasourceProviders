package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;

public class DBLanguageImageCollectionHandler implements DBUpdateableCollectionHandler<LanguageImage> {
    private ImageFile parent;

    public DBLanguageImageCollectionHandler(final ImageFile parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<LanguageImage> items, LanguageImage entity) {
    }

    @Override
    public void addItem(Collection<LanguageImage> items, final LanguageImage entity) {
        parent.addLanguageImage(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<LanguageImage> items, final LanguageImage entity) {
        parent.removeLanguageImage(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
