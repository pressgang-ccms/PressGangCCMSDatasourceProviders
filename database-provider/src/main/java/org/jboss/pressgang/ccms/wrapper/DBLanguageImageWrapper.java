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

import org.jboss.pressgang.ccms.model.LanguageImage;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBLanguageImageWrapper extends DBBaseEntityWrapper<LanguageImageWrapper, LanguageImage> implements LanguageImageWrapper {

    private final LanguageImage languageImage;

    public DBLanguageImageWrapper(final DBProviderFactory providerFactory, final LanguageImage languageImage, boolean isRevision) {
        super(providerFactory, isRevision, LanguageImage.class);
        this.languageImage = languageImage;
    }

    @Override
    protected LanguageImage getEntity() {
        return languageImage;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setLanguageImageId(id);
    }

    @Override
    public String getFilename() {
        return getEntity().getOriginalFileName();
    }

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public byte[] getImageData() {
        return getEntity().getImageData();
    }

    @Override
    public byte[] getImageDataBase64() {
        return getEntity().getImageDataBase64();
    }

    @Override
    public byte[] getThumbnail() {
        return getEntity().getThumbnailData();
    }

}
