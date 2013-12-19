package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTLanguageFileCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTFileV1;
import org.jboss.pressgang.ccms.wrapper.FileWrapper;
import org.jboss.pressgang.ccms.wrapper.LanguageFileWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTFileProvider extends RESTDataProvider implements FileProvider {
    private static Logger log = LoggerFactory.getLogger(RESTFileProvider.class);

    protected RESTFileProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTFileV1 loadFile(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONFile(id, expandString);
        } else {
            return getRESTClient().getJSONFileRevision(id, revision, expandString);
        }
    }

    public RESTFileV1 getRESTFile(int id) {
        return getRESTFile(id, null);
    }

    @Override
    public FileWrapper getFile(int id) {
        return getFile(id, null);
    }

    public RESTFileV1 getRESTFile(int id, Integer revision) {
        try {
            final RESTFileV1 file;
            if (getRESTEntityCache().containsKeyValue(RESTFileV1.class, id, revision)) {
                file = getRESTEntityCache().get(RESTFileV1.class, id, revision);
            } else {
                file = loadFile(id, revision, "");
                getRESTEntityCache().add(file, revision);
            }

            return file;
        } catch (Exception e) {
            log.debug("Failed to retrieve File " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public FileWrapper getFile(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTFile(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTLanguageFileCollectionV1 getRESTFileLanguageFiles(int id, final Integer revision) {
        try {
            RESTFileV1 file = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTFileV1.class, id, revision)) {
                file = getRESTEntityCache().get(RESTFileV1.class, id, revision);

                if (file.getLanguageFiles_OTM() != null) {
                    return file.getLanguageFiles_OTM();
                }
            }

            // We need to expand the language files in the file collection
            final String expandString = getExpansionString(RESTFileV1.LANGUAGE_FILES_NAME);

            // Load the file from the REST Interface
            final RESTFileV1 tempFile = loadFile(id, revision, expandString);

            if (file == null) {
                file = tempFile;
                getRESTEntityCache().add(file, revision);
            } else {
                file.setLanguageFiles_OTM(tempFile.getLanguageFiles_OTM());
            }
            getRESTEntityCache().add(file.getLanguageFiles_OTM(), revision != null);

            return file.getLanguageFiles_OTM();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Language Files for File " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    public CollectionWrapper<LanguageFileWrapper> getFileLanguageFiles(int id, final Integer revision, final RESTFileV1 parent) {
        return RESTCollectionWrapperBuilder.<LanguageFileWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTFileLanguageFiles(id, revision))
                .isRevisionCollection(revision != null)
                .parent(parent)
                .expandedEntityMethods(Arrays.asList("getLanguageFiles_OTM"))
                .build();
    }

    public RESTFileCollectionV1 getRESTFileRevisions(int id, final Integer revision) {
        try {
            RESTFileV1 file = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTFileV1.class, id, revision)) {
                file = getRESTEntityCache().get(RESTFileV1.class, id, revision);

                if (file.getRevisions() != null) {
                    return file.getRevisions();
                }
            }

            // We need to expand the revisions in the file collection
            final String expandString = getExpansionString(RESTFileV1.REVISIONS_NAME);

            // Load the file from the REST Interface
            final RESTFileV1 tempFile = loadFile(id, revision, expandString);

            if (file == null) {
                file = tempFile;
                getRESTEntityCache().add(file, revision);
            } else {
                file.setRevisions(tempFile.getRevisions());
            }

            return file.getRevisions();
        } catch (Exception e) {
            log.debug("Failed to retrieve the Revisions for File " + id + (revision == null ? "" : (", Revision " + revision)), e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<FileWrapper> getFileRevisions(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<FileWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTFileRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }
}
