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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.LanguageFile;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBLanguageFileWrapper extends DBBaseEntityWrapper<LanguageFileWrapper, LanguageFile> implements LanguageFileWrapper {

    private final LanguageFile languageFile;

    public DBLanguageFileWrapper(final DBProviderFactory providerFactory, final LanguageFile languageFile, boolean isRevision) {
        super(providerFactory, isRevision, LanguageFile.class);
        this.languageFile = languageFile;
    }

    @Override
    protected LanguageFile getEntity() {
        return languageFile;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setLanguageFileId(id);
    }

    @Override
    public String getOriginalFilename() {
        return getEntity().getOriginalFileName();
    }

    @Override
    public void setOriginalFilename(final String filename) {
        getEntity().setOriginalFileName(filename);
    }

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public void setLocale(final String locale) {
        getEntity().setLocale(locale);
    }

    @Override
    public byte[] getFileData() {
        return getEntity().getFileData();
    }

    @Override
    public void setFileData(byte[] fileData) {
        getEntity().setFileData(fileData);
    }
}
