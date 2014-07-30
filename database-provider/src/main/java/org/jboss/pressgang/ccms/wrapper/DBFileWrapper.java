/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.File;
import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageFileCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBLanguageFileCollectionHandler;

public class DBFileWrapper extends DBBaseEntityWrapper<FileWrapper, File> implements FileWrapper {
    private final DBLanguageFileCollectionHandler languageFileCollectionHandler;

    private final File file;

    public DBFileWrapper(final DBProviderFactory providerFactory, final File file, boolean isRevision) {
        super(providerFactory, isRevision, File.class);
        this.file = file;
        languageFileCollectionHandler = new DBLanguageFileCollectionHandler(file);
    }

    @Override
    protected File getEntity() {
        return file;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setFileId(id);
    }

    @Override
    public String getDescription() {
        return getEntity().getDescription();
    }

    @Override
    public void setDescription(final String description) {
        getEntity().setDescription(description);
    }

    @Override
    public String getFilePath() {
        return getEntity().getFilePath();
    }

    @Override
    public void setFilePath(final String filePath) {
        getEntity().setFilePath(filePath);
    }

    @Override
    public String getFilename() {
        return getEntity().getFileName();
    }

    @Override
    public void setFilename(final String filename) {
        getEntity().setFileName(filename);
    }

    @Override
    public boolean isExplodeArchive() {
        return getEntity().getExplodeArchive();
    }

    @Override
    public void setExplodeArchive(boolean explodeArchive) {
        getEntity().setExplodeArchive(explodeArchive);
    }

    @Override
    public UpdateableCollectionWrapper<LanguageFileWrapper> getLanguageFiles() {
        final CollectionWrapper<LanguageFileWrapper> collection = getWrapperFactory().createCollection(getEntity().getLanguageFiles(),
                LanguageFile.class, isRevisionEntity(), languageFileCollectionHandler);
        return (UpdateableCollectionWrapper<LanguageFileWrapper>) collection;
    }

    @Override
    public void setLanguageFiles(UpdateableCollectionWrapper<LanguageFileWrapper> languageFiles) {
        if (languageFiles == null) return;
        final DBLanguageFileCollectionWrapper dbLanguageFiles = (DBLanguageFileCollectionWrapper) languageFiles;
        dbLanguageFiles.setHandler(languageFileCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbLanguageFiles.unwrap() != getEntity().getLanguageFiles()) {
            // Add new language files and skip any existing language files
            final Set<LanguageFile> currentLanguageFiles = new HashSet<LanguageFile>(getEntity().getLanguageFiles());
            final Collection<LanguageFile> newLanguageFiles = dbLanguageFiles.unwrap();
            for (final LanguageFile languageFile : newLanguageFiles) {
                if (currentLanguageFiles.contains(languageFile)) {
                    currentLanguageFiles.remove(languageFile);
                    continue;
                } else {
                    getEntity().addLanguageFile(languageFile);
                }
            }

            // Remove language files that should no longer exist in the collection
            for (final LanguageFile removeLanguageFile : currentLanguageFiles) {
                getEntity().removeLanguageFile(removeLanguageFile);
            }
        }
    }
}
