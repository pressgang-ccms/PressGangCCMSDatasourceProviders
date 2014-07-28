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

import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBLanguageFileProvider extends DBDataProvider implements LanguageFileProvider {

    protected DBLanguageFileProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public LanguageFileWrapper getLanguageFile(int id, Integer revision) {
        if (revision == null) {
            return getWrapperFactory().create(getEntity(LanguageFile.class, id), false);
        } else {
            return getWrapperFactory().create(getRevisionEntity(LanguageFile.class, id, revision), true);
        }
    }

    @Override
    public byte[] getLanguageFileData(int id, Integer revision) {
        return getLanguageFile(id, revision).getFileData();
    }

    @Override
    public CollectionWrapper<LanguageFileWrapper> getLanguageFileRevisions(int id, Integer revision) {
        final List<LanguageFile> revisions = getRevisionList(LanguageFile.class, id);
        return getWrapperFactory().createCollection(revisions, LanguageFile.class, revision != null);
    }
}
