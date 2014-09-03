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
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseAuditedEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTFileV1Wrapper extends RESTBaseAuditedEntityWrapper<FileWrapper, RESTFileV1> implements FileWrapper {

    protected RESTFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTFileV1 file, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, file, isRevision, isNewEntity);
    }

    protected RESTFileV1Wrapper(final RESTProviderFactory providerFactory, final RESTFileV1 file, boolean isRevision,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, file, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public RESTFileV1Wrapper clone(boolean deepCopy) {
        return new RESTFileV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity(),
                getProxyProcessedMethodNames());
    }

    @Override
    public String getDescription() {
        return getProxyEntity().getDescription();
    }

    @Override
    public void setDescription(final String description) {
        getEntity().explicitSetDescription(description);
    }

    @Override
    public String getFilePath() {
        return getProxyEntity().getFilePath();
    }

    @Override
    public void setFilePath(final String filePath) {
        getEntity().explicitSetFilePath(filePath);
    }

    @Override
    public String getFilename() {
        return getProxyEntity().getFileName();
    }

    @Override
    public void setFilename(final String filename) {
        getEntity().explicitSetFileName(filename);
    }

    @Override
    public boolean isExplodeArchive() {
        return getProxyEntity().getExplodeArchive();
    }

    @Override
    public void setExplodeArchive(boolean explodeArchive) {
        getEntity().explicitSetExplodeArchive(explodeArchive);
    }

    @Override
    public UpdateableCollectionWrapper<LanguageFileWrapper> getLanguageFiles() {
        return (UpdateableCollectionWrapper<LanguageFileWrapper>) RESTCollectionWrapperBuilder.<LanguageFileWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getProxyEntity().getLanguageFiles_OTM())
                .isRevisionCollection(isRevisionEntity())
                .parent(getProxyEntity())
                .build();
    }

    @Override
    public void setLanguageFiles(UpdateableCollectionWrapper<LanguageFileWrapper> languageFiles) {
        getEntity().explicitSetLanguageFiles_OTM(languageFiles == null ? null : (RESTLanguageFileCollectionV1) languageFiles.unwrap());
    }
}
