package org.jboss.pressgang.ccms.wrapper.collection.handler;

import java.util.Collection;
import java.util.List;

import org.jboss.pressgang.ccms.model.File;
import org.jboss.pressgang.ccms.model.LanguageFile;

public class DBLanguageFileCollectionHandler implements DBUpdateableCollectionHandler<LanguageFile> {
    private File parent;

    public DBLanguageFileCollectionHandler(final File parent) {
        this.parent = parent;
    }

    @Override
    public void updateItem(Collection<LanguageFile> items, LanguageFile entity) {
    }

    @Override
    public void addItem(Collection<LanguageFile> items, final LanguageFile entity) {
        parent.addLanguageFile(entity);
        if (items instanceof List) {
            ((List) items).add(entity);
        }
    }

    @Override
    public void removeItem(Collection<LanguageFile> items, final LanguageFile entity) {
        parent.removeLanguageFile(entity);
        if (items instanceof List) {
            ((List) items).remove(entity);
        }
    }
}
