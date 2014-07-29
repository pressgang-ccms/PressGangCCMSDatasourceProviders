/*
  Copyright 2011-2014 Red Hat

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTUserCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.rest.v1.query.RESTUserQueryBuilderV1;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTUserProvider extends RESTDataProvider implements UserProvider {
    private static Logger log = LoggerFactory.getLogger(RESTUserProvider.class);

    protected RESTUserProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTUserV1 loadUser(int id, Integer revision, String expandString) {
        if (revision == null) {
            return getRESTClient().getJSONUser(id, expandString);
        } else {
            return getRESTClient().getJSONUserRevision(id, revision, expandString);
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
            if (getRESTEntityCache().containsKeyValue(RESTUserV1.class, id, revision)) {
                user = getRESTEntityCache().get(RESTUserV1.class, id, revision);
            } else {
                user = loadUser(id, revision, "");
                getRESTEntityCache().add(user, revision);
            }
            return user;
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public UserWrapper getUser(int id, Integer revision) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTUser(id, revision))
                .isRevision(revision != null)
                .build();
    }

    public RESTUserCollectionV1 getRESTUsersByName(final String name) {
        assert name != null;

        final List<String> keys = new ArrayList<String>();
        keys.add("name");
        keys.add(name);
        try {
            final RESTUserCollectionV1 users;
            if (getRESTCollectionCache().containsKey(RESTUserV1.class, keys)) {
                users = getRESTCollectionCache().get(RESTUserV1.class, RESTUserCollectionV1.class, keys);
            } else {
                final RESTUserQueryBuilderV1 queryBuilder = new RESTUserQueryBuilderV1();
                queryBuilder.setUserName(name);

                // We need to expand the Users collection
                final String expandString = getExpansionString(RESTv1Constants.USERS_EXPANSION_NAME);

                users = getRESTClient().getJSONUsersWithQuery(queryBuilder.buildQueryPath(), expandString);
                getRESTCollectionCache().add(RESTUserV1.class, users, keys);
            }
            return users;
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<UserWrapper> getUsersByName(final String name) {
        return RESTCollectionWrapperBuilder.<UserWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTUsersByName(name))
                .build();
    }

    @Override
    public UserWrapper getUserByName(final String name) {
        final RESTUserCollectionV1 users = getRESTUsersByName(name);
        if (users != null && users.getItems() != null && !users.getItems().isEmpty()) {
            for (final RESTUserV1 user : users.returnItems()) {
                if (user.getName().equals(name)) {
                    return RESTEntityWrapperBuilder.newBuilder()
                            .providerFactory(getProviderFactory())
                            .entity(user)
                            .build();
                }
            }
        }

        return null;
    }

    public RESTUserCollectionV1 getRESTUserRevisions(int id, final Integer revision) {
        try {
            RESTUserV1 user = null;
            // Check the cache first
            if (getRESTEntityCache().containsKeyValue(RESTUserV1.class, id, revision)) {
                user = getRESTEntityCache().get(RESTUserV1.class, id, revision);

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
                getRESTEntityCache().add(user, revision);
            } else {
                user.setRevisions(tempUser.getRevisions());
            }

            return user.getRevisions();
        } catch (Exception e) {
            log.debug("", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<UserWrapper> getUserRevisions(int id, final Integer revision) {
        return RESTCollectionWrapperBuilder.<UserWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTUserRevisions(id, revision))
                .isRevisionCollection()
                .expandedEntityMethods(Arrays.asList("getRevisions"))
                .build();
    }
}
