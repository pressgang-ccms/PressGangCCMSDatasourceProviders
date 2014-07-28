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

import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBLanguageImageProvider extends DBDataProvider implements LanguageImageProvider {

    protected DBLanguageImageProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public LanguageImageWrapper getLanguageImage(int id, Integer revision) {
        if (revision == null) {
            return getWrapperFactory().create(getEntity(LanguageImage.class, id), false);
        } else {
            return getWrapperFactory().create(getRevisionEntity(LanguageImage.class, id, revision), true);
        }
    }

    @Override
    public byte[] getLanguageImageData(int id, Integer revision) {
        return getLanguageImage(id, revision).getImageData();
    }

    @Override
    public byte[] getLanguageImageDataBase64(int id, Integer revision) {
        return getLanguageImage(id, revision).getImageDataBase64();
    }

    @Override
    public byte[] getLanguageImageThumbnail(int id, Integer revision) {
        return getLanguageImage(id, revision).getThumbnail();
    }

    @Override
    public CollectionWrapper<LanguageImageWrapper> getLanguageImageRevisions(int id, Integer revision) {
        final List<LanguageImage> revisions = getRevisionList(LanguageImage.class, id);
        return getWrapperFactory().createCollection(revisions, LanguageImage.class, revision != null);
    }
}
