package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTBlobConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;
import org.jboss.pressgang.ccms.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTBlobConstantProvider extends RESTDataProvider implements BlobConstantProvider {
    private static Logger log = LoggerFactory.getLogger(RESTBlobConstantProvider.class);

    public RESTBlobConstantProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTBlobConstantV1 loadBlobConstant(Integer id, Integer revision, final String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONBlobConstant(id, expandString);
        } else {
            return getRESTClient().getJSONBlobConstantRevision(id, revision, expandString);
        }
    }

    public RESTBlobConstantV1 getRESTBlobConstant(int id) {
        return getRESTBlobConstant(id, null);
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id) {
        return getBlobConstant(id, null);
    }

    public RESTBlobConstantV1 getRESTBlobConstant(int id, Integer revision) {
        try {
            final RESTBlobConstantV1 blobConstant;
            if (getRESTEntityCache().containsKeyValue(RESTBlobConstantV1.class, id, revision)) {
                blobConstant = getRESTEntityCache().get(RESTBlobConstantV1.class, id, revision);
            } else {
                blobConstant = getRESTClient().getJSONBlobConstantRevision(id, revision, "");
                getRESTEntityCache().add(blobConstant, revision);
            }
            return blobConstant;
        } catch (Exception e) {
            log.error("Failed to retrieve Blob Constant " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public BlobConstantWrapper getBlobConstant(int id, Integer revision) {
        return getWrapperFactory().create(getRESTBlobConstant(id, revision), revision != null);
    }

    public byte[] getBlobConstantValue(int id, Integer revision) {
        try {
            RESTBlobConstantV1 blobConstant = null;
            if (getRESTEntityCache().containsKeyValue(RESTBlobConstantV1.class, id, revision)) {
                blobConstant = getRESTEntityCache().get(RESTBlobConstantV1.class, id, revision);
                // check if the cached copy has the value
                if (blobConstant.getValue() != null) {
                    return blobConstant.getValue();
                }
            }

            // We need to expand the value in the blobconstant
            final String expandString = getExpansionString(RESTBlobConstantV1.VALUE_NAME);

            // Load the blob constant from the REST Interface
            final RESTBlobConstantV1 tempBlobConstant = loadBlobConstant(id, revision, expandString);

            // If the Blob Constant has been saved, or has been evicted then re-add it to the cache.
            if (blobConstant == null) {
                blobConstant = tempBlobConstant;
                getRESTEntityCache().add(blobConstant, revision);
            } else {
                blobConstant.setValue(tempBlobConstant.getValue());
            }

            return blobConstant.getValue();
        } catch (Exception e) {
            log.error("Failed to retrieve Blob Constant " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    public RESTBlobConstantCollectionV1 getRESTBlobConstantRevisions(int id, Integer revision) {
        try {
            RESTBlobConstantV1 blobConstant = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTBlobConstantV1.class, id, revision)) {
                blobConstant = getRESTEntityCache().get(RESTBlobConstantV1.class, id, revision);

                if (blobConstant.getRevisions() != null) {
                    return blobConstant.getRevisions();
                }
            }
            // We need to expand the revisions in the blob constant collection
            final String expandString = getExpansionString(RESTBlobConstantV1.REVISIONS_NAME);

            // Load the blob constant from the REST Interface
            final RESTBlobConstantV1 tempBlobConstant = loadBlobConstant(id, revision, expandString);

            if (blobConstant == null) {
                blobConstant = tempBlobConstant;
                getRESTEntityCache().add(blobConstant, revision);
            } else {
                blobConstant.setRevisions(tempBlobConstant.getRevisions());
            }

            return blobConstant.getRevisions();
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for Blob Constant " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<BlobConstantWrapper> getBlobConstantRevisions(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTBlobConstantRevisions(id, revision), RESTBlobConstantV1.class, true);
    }
}
