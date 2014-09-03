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

package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;
import java.util.List;

import javassist.util.proxy.ProxyObject;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.proxy.RESTFileV1ProxyHandler;
import org.jboss.pressgang.ccms.proxy.RESTLanguageFileV1ProxyHandler;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.items.RESTLanguageFileCollectionItemV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageFileV1;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTLanguageFileProvider extends RESTDataProvider implements LanguageFileProvider {
    private static Logger log = LoggerFactory.getLogger(RESTLanguageFileProvider.class);

    protected RESTLanguageFileProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTFileV1 loadFile(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONFile(id, expandString);
        } else {
            return getRESTClient().getJSONFileRevision(id, revision, expandString);
        }
    }

    @Override
    public LanguageFileWrapper getLanguageFile(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Files using V1 of the REST Interface.");
    }

    public RESTLanguageFileV1 getRESTLanguageFile(int id, final Integer revision, final RESTFileV1 parent) {
        try {
            if (getRESTEntityCache().containsKeyValue(RESTLanguageFileV1.class, id, revision)) {
                return getRESTEntityCache().get(RESTLanguageFileV1.class, id, revision);
            } else {
                final RESTLanguageFileCollectionV1 languageFiles = parent.getLanguageFiles_OTM();

                final List<RESTLanguageFileV1> languageFileItems = languageFiles.returnItems();
                for (final RESTLanguageFileV1 file : languageFileItems) {
                    if (file.getId().equals(id) && (revision == null || file.getRevision().equals(revision))) {
                        if (file instanceof ProxyObject) {
                            final RESTLanguageFileV1ProxyHandler proxy = (RESTLanguageFileV1ProxyHandler) ((ProxyObject) file).getHandler();
                            return proxy.getEntity();
                        } else {
                            return file;
                        }
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve Language File " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public LanguageFileWrapper getLanguageFile(int id, final Integer revision, final RESTFileV1 parent) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTLanguageFile(id, revision, parent))
                .isRevision(revision != null)
                .parent(parent)
                .build();
    }

    @Override
    public byte[] getLanguageFileData(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Files using V1 of the REST Interface.");
    }

    public byte[] getLanguageFileData(int id, final Integer revision, final RESTFileV1 parent) {
        final RESTFileV1ProxyHandler fileProxy = (RESTFileV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer fileId = parent.getId();
        final Integer fileRevision = fileProxy.getEntityRevision();

        try {
            final RESTLanguageFileV1 languageFile = getRESTLanguageFile(id, revision, parent);
            if (languageFile == null) {
                throw new NotFoundException();
            } else if (languageFile.getFileData() != null) {
                return languageFile.getFileData();
            } else {
                RESTFileV1 file = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTFileV1.class, fileId, fileRevision)) {
                    file = getRESTEntityCache().get(RESTFileV1.class, fileId, fileRevision);
                }

                // We need to expand the all the language files with their data in the file
                final String expandString = getExpansionString(RESTFileV1.LANGUAGE_FILES_NAME, RESTLanguageFileV1.FILE_DATA_NAME);

                // Load the file from the REST Interface
                final RESTFileV1 tempFile = loadFile(fileId, fileRevision, expandString);

                if (file == null) {
                    file = tempFile;
                    getRESTEntityCache().add(file, fileRevision);
                } else if (file.getLanguageFiles_OTM() == null) {
                    file.setLanguageFiles_OTM(tempFile.getLanguageFiles_OTM());
                } else {
                    /*
                     * Iterate over the current and old language files and add any missing language files. Also add the file
                     * data to any language files that don't have it set.
                     */
                    final List<RESTLanguageFileV1> langFiles = file.getLanguageFiles_OTM().returnItems();
                    final List<RESTLanguageFileV1> newLangFiles = tempFile.getLanguageFiles_OTM().returnItems();
                    for (final RESTLanguageFileV1 newLangFile : newLangFiles) {
                        boolean found = false;

                        for (final RESTLanguageFileV1 langFile : langFiles) {
                            if (langFile.getId().equals(newLangFile.getId())) {
                                langFile.setFileData(newLangFile.getFileData());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            file.getLanguageFiles_OTM().addItem(newLangFile);
                        }
                    }
                }

                // Find the matching language file and return the file data
                for (final RESTLanguageFileCollectionItemV1 fileItem : file.getLanguageFiles_OTM().getItems()) {
                    final RESTLanguageFileV1 langFile = fileItem.getItem();

                    if (langFile.getId() == id && (revision == null || langFile.getRevision().equals(revision))) {
                        return langFile.getFileData();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Data for Language File " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<LanguageFileWrapper> getLanguageFileRevisions(int id, final Integer revision) {
        throw new UnsupportedOperationException("A parent is needed to get Language Files using V1 of the REST Interface.");
    }

    public RESTLanguageFileCollectionV1 getRESTLanguageFileRevisions(int id, final Integer revision, final RESTFileV1 parent) {
        final RESTFileV1ProxyHandler fileProxy = (RESTFileV1ProxyHandler) ((ProxyObject) parent).getHandler();
        final Integer fileId = parent.getId();
        final Integer fileRevision = fileProxy.getEntityRevision();

        try {
            final RESTLanguageFileV1 languageFile = getRESTLanguageFile(id, revision, parent);
            if (languageFile == null) {
                throw new NotFoundException();
            } else if (languageFile.getRevisions() != null) {
                return languageFile.getRevisions();
            } else {
                RESTFileV1 file = null;
                // Check the cache first
                if (getRESTEntityCache().containsKeyValue(RESTFileV1.class, fileId, fileRevision)) {
                    file = getRESTEntityCache().get(RESTFileV1.class, fileId, fileRevision);
                }

                // We need to expand the all the language file revisions in the file
                final String expandString = getExpansionString(RESTFileV1.LANGUAGE_FILES_NAME, RESTLanguageFileV1.REVISIONS_NAME);

                // Load the file from the REST Interface
                final RESTFileV1 tempFile = loadFile(fileId, fileRevision, expandString);

                if (file == null) {
                    file = tempFile;
                    getRESTEntityCache().add(file, fileRevision);
                } else if (file.getLanguageFiles_OTM() == null) {
                    file.setLanguageFiles_OTM(tempFile.getLanguageFiles_OTM());
                } else {
                    /*
                     * Iterate over the current and old language files and add any missing language files. Also add the file
                     * data to any language files that don't have it set.
                     */
                    final List<RESTLanguageFileV1> langFiles = file.getLanguageFiles_OTM().returnItems();
                    final List<RESTLanguageFileV1> newLangFiles = tempFile.getLanguageFiles_OTM().returnItems();
                    for (final RESTLanguageFileV1 newLangFile : newLangFiles) {
                        boolean found = false;

                        for (final RESTLanguageFileV1 langFile : langFiles) {
                            if (langFile.getId().equals(newLangFile.getId())) {
                                langFile.setRevisions(newLangFile.getRevisions());

                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            file.getLanguageFiles_OTM().addItem(newLangFile);
                        }
                    }
                }

                for (final RESTLanguageFileCollectionItemV1 fileItem : file.getLanguageFiles_OTM().getItems()) {
                    final RESTLanguageFileV1 langFile = fileItem.getItem();

                    if (langFile.getId() == id && (revision == null || langFile.getRevision().equals(revision))) {
                        return languageFile.getRevisions();
                    }
                }

                throw new NotFoundException();
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for Language File " + id + (revision == null ? "" : (", " +
                    "Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<LanguageFileWrapper> getLanguageFileRevisions(int id, final Integer revision, final RESTFileV1 parent) {
        return RESTCollectionWrapperBuilder.<LanguageFileWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTLanguageFileRevisions(id, revision, parent))
                .isRevisionCollection()
                .parent(parent)
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }
}
