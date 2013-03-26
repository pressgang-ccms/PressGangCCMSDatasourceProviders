package org.jboss.pressgang.ccms.provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTStringConstantCollectionV1;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTStringConstantProvider extends RESTDataProvider implements StringConstantProvider {
    private static Logger log = LoggerFactory.getLogger(RESTStringConstantProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTInterfaceV1 client;

    public RESTStringConstantProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
    }

    public RESTStringConstantV1 getRESTStringConstant(int id) {
        return getRESTStringConstant(id, null);
    }

    @Override
    public StringConstantWrapper getStringConstant(int id) {
        return getStringConstant(id, null);
    }

    public RESTStringConstantV1 getRESTStringConstant(int id, Integer revision) {
        try {
            final RESTStringConstantV1 stringConstant;
            if (entityCache.containsKeyValue(RESTStringConstantV1.class, id, revision)) {
                stringConstant = entityCache.get(RESTStringConstantV1.class, id, revision);
            } else {
                if (revision == null) {
                    stringConstant = client.getJSONStringConstant(id, null);
                    entityCache.add(stringConstant);
                } else {
                    stringConstant = client.getJSONStringConstantRevision(id, revision, null);
                    entityCache.add(stringConstant, revision);
                }
            }
            return stringConstant;
        } catch (Exception e) {
            log.error("Failed to retrieve String Constant " + id + (revision == null ? "" : (", Revision " + revision)), e);
        }
        return null;
    }

    @Override
    public StringConstantWrapper getStringConstant(int id, Integer revision) {
        return getWrapperFactory().create(getRESTStringConstant(id, revision), revision != null);
    }

    public RESTStringConstantCollectionV1 getRESTStringConstantRevisions(int id, Integer revision) {
        try {
            RESTStringConstantV1 stringConstant = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTStringConstantV1.class, id, revision)) {
                stringConstant = entityCache.get(RESTStringConstantV1.class, id, revision);

                if (stringConstant.getRevisions() != null) {
                    return stringConstant.getRevisions();
                }
            }
            // We need to expand the revisions in the string constant collection
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandRevisions = new ExpandDataTrunk(new ExpandDataDetails(RESTStringConstantV1.REVISIONS_NAME));
            expand.setBranches(CollectionUtilities.toArrayList(expandRevisions));
            final String expandString = mapper.writeValueAsString(expand);

            // Load the string constant from the REST Interface
            final RESTStringConstantV1 tempStringConstant;
            if (revision == null) {
                tempStringConstant = client.getJSONStringConstant(id, expandString);
            } else {
                tempStringConstant = client.getJSONStringConstantRevision(id, revision, expandString);
            }

            if (stringConstant == null) {
                stringConstant = tempStringConstant;
                if (revision == null) {
                    entityCache.add(stringConstant);
                } else {
                    entityCache.add(stringConstant, revision);
                }
            } else {
                stringConstant.setRevisions(tempStringConstant.getRevisions());
            }

            return stringConstant.getRevisions();
        } catch (Exception e) {
            log.error("Failed to retrieve the Revisions for String Constant " + id + (revision == null ? "" : (", Revision " + revision)),
                    e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<StringConstantWrapper> getStringConstantRevisions(int id, Integer revision) {
        return getWrapperFactory().createCollection(getRESTStringConstantRevisions(id, revision), RESTStringConstantV1.class, true);
    }
}
