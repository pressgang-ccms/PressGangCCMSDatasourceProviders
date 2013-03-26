package org.jboss.pressgang.ccms.provider;

import java.util.ArrayList;
import java.util.List;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTUserCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.rest.v1.query.RESTUserQueryBuilderV1;
import org.jboss.pressgang.ccms.utils.RESTCollectionCache;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTUserProvider extends RESTDataProvider implements UserProvider {
    private static Logger log = LoggerFactory.getLogger(RESTUserProvider.class);

    private final RESTEntityCache entityCache;
    private final RESTCollectionCache collectionsCache;
    private final RESTInterfaceV1 client;

    protected RESTUserProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
        collectionsCache = restManager.getRESTCollectionCache();
    }

    protected RESTUserV1 loadUser(int id, Integer revision, String expandString) {
        if (revision == null) {
            return client.getJSONUser(id, expandString);
        } else {
            return client.getJSONUserRevision(id, revision, expandString);
        }
    }

    public RESTUserV1 getRESTUser(int id) {
        return getRESTUser(id, null);
    }

    @Override
    public UserWrapper getUser(int id) {
        return getUser(id, null);
    }

    public RESTUserV1 getRESTUser(int id, Integer revision) {
        try {
            final RESTUserV1 user;
            if (entityCache.containsKeyValue(RESTUserV1.class, id, revision)) {
                user = entityCache.get(RESTUserV1.class, id, revision);
            } else {
                user = loadUser(id, revision, "");
                entityCache.add(user, revision);
            }
            return user;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public UserWrapper getUser(int id, Integer revision) {
        return getWrapperFactory().create(getRESTUser(id, revision), revision != null);
    }

    public RESTUserCollectionV1 getRESTUsersByName(final String name) {
        assert name != null;

        final List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add(name);
        try {
            final RESTUserCollectionV1 users;
            if (collectionsCache.containsKey(RESTUserV1.class, keys)) {
                users = collectionsCache.get(RESTUserV1.class, RESTUserCollectionV1.class, keys);
            } else {
                final RESTUserQueryBuilderV1 queryBuilder = new RESTUserQueryBuilderV1();
                queryBuilder.setUserName(name);

                // We need to expand the Users collection
                final String expandString = getExpansionString(RESTv1Constants.USERS_EXPANSION_NAME);

                users = client.getJSONUsersWithQuery(queryBuilder.buildQueryPath(), expandString);
                collectionsCache.add(RESTUserV1.class, users, keys);
            }
            return users;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<UserWrapper> getUsersByName(final String name) {
        return getWrapperFactory().createCollection(getRESTUsersByName(name), RESTUserV1.class, false);
    }

    public RESTUserCollectionV1 getRESTUserRevisions(int id, final Integer revision) {
        try {
            RESTUserV1 user = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTUserV1.class, id, revision)) {
                user = entityCache.get(RESTUserV1.class, id, revision);

                if (user.getRevisions() != null) {
                    return user.getRevisions();
                }
            }

            // We need to expand the all the revisions in the user
            final String expandString = getExpansionString(RESTUserV1.REVISIONS_NAME);

            // Load the user from the REST Interface
            final RESTUserV1 tempUser = loadUser(id, revision, expandString);

            if (user == null) {
                user = tempUser;
                entityCache.add(user, revision);
            } else {
                user.setRevisions(tempUser.getRevisions());
            }

            return user.getRevisions();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<UserWrapper> getUserRevisions(int id, final Integer revision) {
        return getWrapperFactory().createCollection(getRESTUserRevisions(id, revision), RESTUserV1.class, true);
    }
}
