package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;

public class DBLanguageImageCollectionHandler implements DBUpdateableCollectionHandler<LanguageImage> {
    private ImageFile parent;

    public DBLanguageImageCollectionHandler(final ImageFile parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(LanguageImage entity) {
    }

    @Override
    public void addItem(final LanguageImage entity) {
        parent.addLanguageImage(entity);
    }

    @Override
    public void removeItem(final LanguageImage entity) {
        parent.removeLanguageImage(entity);
    }
}
