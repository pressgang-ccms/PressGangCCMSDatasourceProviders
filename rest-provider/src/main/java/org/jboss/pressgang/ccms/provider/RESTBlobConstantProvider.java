package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTBlobConstantCollectionV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTBlobConstantProvider extends RESTDataProvider implements BlobConstantProvider {
    private static Logger log = LoggerFactory.getLogger(RESTBlobConstantProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    public RESTBlobConstantProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
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
            if (entityCache.containsKeyValue(RESTBlobConstantV1.class, id, revision)) {
                blobConstant = entityCache.get(RESTBlobConstantV1.class, id, revision);
            } else {
                if (revision == null) {
                    blobConstant = client.getJSONBlobConstant(id, "");
                    entityCache.add(blobConstant);
                } else {
                    blobConstant = client.getJSONBlobConstantRevision(id, revision, "");
                    entityCache.add(blobConstant, revision);
                }
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
            if (entityCache.containsKeyValue(RESTBlobConstantV1.class, id, revision)) {
                blobConstant = entityCache.get(RESTBlobConstantV1.class, id, revision);
                // check if the cached copy has the value
                if (blobConstant.getValue() != null) {
                    return blobConstant.getValue();
                }
            }

            // We need to expand the tags in the blobconstant value
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandValue = new ExpandDataTrunk(new ExpandDataDetails(RESTBlobConstantV1.VALUE_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandValue));
            final String expandString = mapper.writeValueAsString(expand);

            final RESTBlobConstantV1 tempBlobConstant;
            if (revision == null) {
                tempBlobConstant = client.getJSONBlobConstant(id, expandString);
            } else {
                tempBlobConstant = client.getJSONBlobConstantRevision(id, revision, expandString);
            }

            // If the Blob Constant has been saved, or has been evicted then re-add it to the cache.
            if (blobConstant == null) {
                blobConstant = tempBlobConstant;
                entityCache.add(tempBlobConstant);
                if (revision == null) {
                    entityCache.add(blobConstant);
                } else {
                    entityCache.add(blobConstant, revision);
                }
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
            if (entityCache.containsKeyValue(RESTBlobConstantV1.class, id, revision)) {
                blobConstant = entityCache.get(RESTBlobConstantV1.class, id, revision);

                if (blobConstant.getRevisions() != null) {
                    return blobConstant.getRevisions();
                }
            }
            // We need to expand the revisions in the blob constant collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTBlobConstantV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the blob constant from the REST Interface
            final RESTBlobConstantV1 tempBlobConstant;
            if (revision == null) {
                tempBlobConstant = client.getJSONBlobConstant(id, expandString);
            } else {
                tempBlobConstant = client.getJSONBlobConstantRevision(id, revision, expandString);
            }

            if (blobConstant == null) {
                blobConstant = tempBlobConstant;
                if (revision == null) {
                    entityCache.add(blobConstant);
                } else {
                    entityCache.add(blobConstant, revision);
                }
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
