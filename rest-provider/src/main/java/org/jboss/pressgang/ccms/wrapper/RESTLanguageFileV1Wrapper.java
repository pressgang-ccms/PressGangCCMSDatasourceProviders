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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLocaleV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseAuditedEntityWrapper;

public class RESTLanguageFileV1Wrapper extends RESTBaseAuditedEntityWrapper<LanguageFileWrapper, RESTLanguageFileV1> implements LanguageFileWrapper {

    protected RESTLanguageFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileV1 file, boolean isRevision,
            final RESTFileV1 parent, boolean isNewEntity) {
        super(providerFactory, file, isRevision, parent, isNewEntity);
    }

    protected RESTLanguageFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageFileV1 file, boolean isRevision,
            final RESTFileV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, file, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTFileV1 getParentEntity() {
        return (RESTFileV1) super.getParentEntity();
    }

    @Override
    public LanguageFileWrapper clone(boolean deepCopy) {
        return new RESTLanguageFileV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                (deepCopy ? getParentEntity().clone(deepCopy) : getParentEntity()), isNewEntity(), getProxyProcessedMethodNames());
    }

    @Override
    public String getOriginalFilename() {
        return getProxyEntity().getFilename();
    }

    @Override
    public void setOriginalFilename(final String filename) {
        getEntity().explicitSetFilename(filename);

    }

    @Override
    public LocaleWrapper getLocale() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getProxyEntity().getLocale())
                .isRevision(isRevisionEntity())
                .build();
    }

    @Override
    public void setLocale(final LocaleWrapper locale) {
        getEntity().explicitSetLocale(locale == null ? null : (RESTLocaleV1) locale.unwrap());
    }

    @Override
    public byte[] getFileData() {
        return getProxyEntity().getFileData();
    }

    @Override
    public void setFileData(byte[] fileData) {
        getEntity().explicitSetFileData(fileData);
    }
}
