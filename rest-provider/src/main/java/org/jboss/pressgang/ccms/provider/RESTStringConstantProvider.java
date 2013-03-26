package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTStringConstantCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTStringConstantV1;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTStringConstantProvider extends RESTDataProvider implements StringConstantProvider {
    private static Logger log = LoggerFactory.getLogger(RESTStringConstantProvider.class);

    public RESTStringConstantProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    protected RESTStringConstantV1 loadStringConstant(Integer id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONStringConstant(id, expandString);
        } else {
            return getRESTClient().getJSONStringConstantRevision(id, revision, expandString);
        }
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
            if (getRESTEntityCache().containsKeyValue(RESTStringConstantV1.class, id, revision)) {
                stringConstant = getRESTEntityCache().get(RESTStringConstantV1.class, id, revision);
            } else {
                stringConstant = loadStringConstant(id, revision, null);
                getRESTEntityCache().add(stringConstant, revision);
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
            if (getRESTEntityCache().containsKeyValue(RESTStringConstantV1.class, id, revision)) {
                stringConstant = getRESTEntityCache().get(RESTStringConstantV1.class, id, revision);

                if (stringConstant.getRevisions() != null) {
                    return stringConstant.getRevisions();
                }
            }
            // We need to expand the revisions in the string constant
            final String expandString = getExpansionString(RESTStringConstantV1.REVISIONS_NAME);

            // Load the string constant from the REST Interface
            final RESTStringConstantV1 tempStringConstant = loadStringConstant(id, revision, expandString);

            if (stringConstant == null) {
                stringConstant = tempStringConstant;
                getRESTEntityCache().add(stringConstant, revision);
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
