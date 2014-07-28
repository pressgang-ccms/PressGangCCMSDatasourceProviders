/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.File;
import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBFileWrapper;
import org.jboss.pressgang.ccms.wrapper.FileWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBFileProvider extends DBDataProvider implements FileProvider {
    protected DBFileProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public FileWrapper getFile(int id) {
        return getWrapperFactory().create(getEntity(File.class, id), false);
    }

    @Override
    public FileWrapper getFile(int id, Integer revision) {
        if (revision == null) {
            return getFile(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(File.class, id, revision), true);
        }
    }

    public CollectionWrapper<LanguageFileWrapper> getFileLanguageFiles(int id, Integer revision) {
        final DBFileWrapper file = (DBFileWrapper) getFile(id, revision);
        if (file == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(file.unwrap().getLanguageFiles(), LanguageFile.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<FileWrapper> getFileRevisions(int id, Integer revision) {
        final List<File> revisions = getRevisionList(File.class, id);
        return getWrapperFactory().createCollection(revisions, File.class, revision != null);
    }
}
