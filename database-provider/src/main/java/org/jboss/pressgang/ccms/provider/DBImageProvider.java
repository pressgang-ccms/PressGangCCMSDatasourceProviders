/*
  Copyright 2011-2014 Red Hat

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

package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBImageWrapper;
import org.jboss.pressgang.ccms.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBImageProvider extends DBDataProvider implements ImageProvider {
    protected DBImageProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public ImageWrapper getImage(int id) {
        return getWrapperFactory().create(getEntity(ImageFile.class, id), false);
    }

    @Override
    public ImageWrapper getImage(int id, Integer revision) {
        if (revision == null) {
            return getImage(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(ImageFile.class, id, revision), true);
        }
    }

    public CollectionWrapper<LanguageImageWrapper> getImageLanguageImages(int id, Integer revision) {
        final DBImageWrapper image = (DBImageWrapper) getImage(id, revision);
        if (image == null) {
            return null;
        } else {
            return getWrapperFactory().createCollection(image.unwrap().getLanguageImages(), LanguageImage.class, revision != null);
        }
    }

    @Override
    public CollectionWrapper<ImageWrapper> getImageRevisions(int id, Integer revision) {
        final List<ImageFile> revisions = getRevisionList(ImageFile.class, id);
        return getWrapperFactory().createCollection(revisions, ImageFile.class, revision != null);
    }
}
