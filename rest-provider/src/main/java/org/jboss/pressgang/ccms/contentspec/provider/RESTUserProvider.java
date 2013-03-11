package org.jboss.pressgang.ccms.contentspec.provider;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.contentspec.rest.RESTManager;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTCollectionCache;
import org.jboss.pressgang.ccms.contentspec.rest.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTUserV1Wrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTUserCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.rest.v1.query.RESTUserQueryBuilderV1;
import org.jboss.pressgang.ccms.utils.common.CollectionUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTUserProvider extends RESTDataProvider implements UserProvider {
    private static Logger log = LoggerFactory.getLogger(RESTUserProvider.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTEntityCache entityCache;
    private final RESTCollectionCache collectionsCache;
    private final RESTInterfaceV1 client;

    protected RESTUserProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
        client = restManager.getRESTClient();
        entityCache = restManager.getRESTEntityCache();
        collectionsCache = restManager.getRESTCollectionCache();
    }

    @Override
    public UserWrapper getUser(int id) {
        return getUser(id, null);
    }

    @Override
    public UserWrapper getUser(int id, Integer revision) {
        try {
            final RESTUserV1 user;
            if (entityCache.containsKeyValue(RESTUserV1.class, id, revision)) {
                user = entityCache.get(RESTUserV1.class, id, revision);
            } else {
                if (revision == null) {
                    user = client.getJSONUser(id, "");
                    entityCache.add(user);
                } else {
                    user = client.getJSONUserRevision(id, revision, "");
                    entityCache.add(user, revision);
                }
            }
            return getWrapperFactory().create(user, revision != null);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public CollectionWrapper<UserWrapper> getUsersByName(final String name) {
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
                final ExpandDataTrunk expand = new ExpandDataTrunk();
                expand.setBranches(
                        CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails(RESTv1Constants.USERS_EXPANSION_NAME))));

                final String expandString = mapper.writeValueAsString(expand);

                users = client.getJSONUsersWithQuery(queryBuilder.buildQueryPath(), expandString);
                collectionsCache.add(RESTUserV1.class, users, keys);
            }
            return getWrapperFactory().createCollection(users, RESTUserV1.class, false);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public CollectionWrapper<UserWrapper> getUserRevisions(final RESTUserV1Wrapper user) {
        final RESTUserV1 originalUser = user.unwrap();
        return getUserRevisions(originalUser.getId(), user.isRevisionEntity() ? originalUser.getRevision() : null);
    }

    @Override
    public CollectionWrapper<UserWrapper> getUserRevisions(int id, final Integer revision) {
        try {
            RESTUserV1 user = null;
            // Check the cache first
            if (entityCache.containsKeyValue(RESTUserV1.class, id, revision)) {
                user = entityCache.get(RESTUserV1.class, id, revision);

                if (user.getRevisions() != null) {
                    return getWrapperFactory().createCollection(user.getRevisions(), RESTUserV1.class, true);
                }
            }

            /* We need to expand the all the items in the topic collection */
            final ExpandDataTrunk expand = new ExpandDataTrunk();
            final ExpandDataTrunk expandLanguageUsers = new ExpandDataTrunk(new ExpandDataDetails(RESTUserV1.REVISIONS_NAME));

            expand.setBranches(CollectionUtilities.toArrayList(expandLanguageUsers));

            final String expandString = mapper.writeValueAsString(expand);

            final RESTUserV1 tempUser;
            if (revision == null) {
                tempUser = client.getJSONUser(id, expandString);
            } else {
                tempUser = client.getJSONUserRevision(id, revision, expandString);
            }

            if (user == null) {
                user = tempUser;
                if (revision == null) {
                    entityCache.add(user);
                } else {
                    entityCache.add(user, revision);
                }
            } else {
                user.setRevisions(tempUser.getRevisions());
            }

            return getWrapperFactory().createCollection(user.getRevisions(), RESTUserV1.class, true);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }
}