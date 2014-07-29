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

package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jboss.pressgang.ccms.model.ImageFile;
import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBLanguageImageCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBLanguageImageCollectionHandler;

public class DBImageWrapper extends DBBaseEntityWrapper<ImageWrapper, ImageFile> implements ImageWrapper {
    private final DBLanguageImageCollectionHandler languageImageCollectionHandler;

    private final ImageFile image;

    public DBImageWrapper(final DBProviderFactory providerFactory, final ImageFile image, boolean isRevision) {
        super(providerFactory, isRevision, ImageFile.class);
        this.image = image;
        languageImageCollectionHandler = new DBLanguageImageCollectionHandler(image);
    }

    @Override
    protected ImageFile getEntity() {
        return image;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setImageFileId(id);
    }

    @Override
    public String getDescription() {
        return getEntity().getDescription();
    }

    @Override
    public UpdateableCollectionWrapper<LanguageImageWrapper> getLanguageImages() {
        final CollectionWrapper<LanguageImageWrapper> collection = getWrapperFactory().createCollection(getEntity().getLanguageImages(),
                LanguageImage.class, isRevisionEntity(), languageImageCollectionHandler);
        return (UpdateableCollectionWrapper<LanguageImageWrapper>) collection;
    }

    @Override
    public void setLanguageImages(UpdateableCollectionWrapper<LanguageImageWrapper> languageImages) {
        if (languageImages == null) return;
        final DBLanguageImageCollectionWrapper dbLanguageImages = (DBLanguageImageCollectionWrapper) languageImages;
        dbLanguageImages.setHandler(languageImageCollectionHandler);

        // Only bother readjusting the collection if its a different collection than the current
        if (dbLanguageImages.unwrap() != getEntity().getLanguageImages()) {
            // Add new language images and skip any existing language images
            final Set<LanguageImage> currentLanguageImages = new HashSet<LanguageImage>(getEntity().getLanguageImages());
            final Collection<LanguageImage> newLanguageImages = dbLanguageImages.unwrap();
            for (final LanguageImage languageImage : newLanguageImages) {
                if (currentLanguageImages.contains(languageImage)) {
                    currentLanguageImages.remove(languageImage);
                    continue;
                } else {
                    getEntity().addLanguageImage(languageImage);
                }
            }

            // Remove language images that should no longer exist in the collection
            for (final LanguageImage removeLanguageImage : currentLanguageImages) {
                getEntity().removeLanguageImage(removeLanguageImage);
            }
        }
    }
}
