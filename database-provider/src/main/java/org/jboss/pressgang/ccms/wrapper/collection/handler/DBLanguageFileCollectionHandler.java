package org.jboss.pressgang.ccms.wrapper.collection.handler;

import org.jboss.pressgang.ccms.model.File;
import org.jboss.pressgang.ccms.model.LanguageFile;

public class DBLanguageFileCollectionHandler implements DBUpdateableCollectionHandler<LanguageFile> {
    private File parent;

    public DBLanguageFileCollectionHandler(final File parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(LanguageFile entity) {
    }

    @Override
    public void addItem(final LanguageFile entity) {
        parent.addLanguageFile(entity);
    }

    @Override
    public void removeItem(final LanguageFile entity) {
        parent.removeLanguageFile(entity);
    }
}
