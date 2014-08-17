/*
 * Copyright 2011-2014 Red Hat, Inc.
 *
 * This file is part of PressGang CCMS.
 *
 * PressGang CCMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PressGang CCMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PressGang CCMS. If not, see <http://www.gnu.org/licenses/>.
 */

package org.jboss.pressgang.ccms.provider;

import java.util.Arrays;
import java.util.List;

import org.jboss.pressgang.ccms.rest.v1.collections.RESTTranslationServerCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslationServerV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTTranslationServerProvider extends RESTDataProvider {
    private static final Logger log = LoggerFactory.getLogger(RESTTranslationServerProvider.class);
    private static final List<String> ALL_TRANSLATION_SERVER_KEY = Arrays.asList("all");

    public RESTTranslationServerProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTTranslationServerV1 loadTranslationServer(Integer id, String expandString) {
        return getRESTClient().getJSONTranslationServer(id, expandString);
    }

    public RESTTranslationServerV1 getRESTTranslationServer(int id) {
        try {
            final RESTTranslationServerV1 translationServer;
            if (getRESTEntityCache().containsKeyValue(RESTTranslationServerV1.class, id)) {
                translationServer = getRESTEntityCache().get(RESTTranslationServerV1.class, id);
            } else {
                translationServer = loadTranslationServer(id, null);
                getRESTEntityCache().add(translationServer);
            }
            return translationServer;
        } catch (Exception e) {
            log.debug("Failed to retrieve Translation Server " + id, e);
            throw handleException(e);
        }
    }

    public RESTTranslationServerCollectionV1 getRESTTranslationServers() {
        try {
            // Check the cache first
            if (getRESTCollectionCache().containsKey(RESTTranslationServerV1.class, ALL_TRANSLATION_SERVER_KEY)) {
                return getRESTCollectionCache().get(RESTTranslationServerV1.class, RESTTranslationServerCollectionV1.class,
                        ALL_TRANSLATION_SERVER_KEY);
            }

            // We need to expand the translation servers
            final String expandString = getExpansionString(RESTv1Constants.TRANSLATION_SERVERS_EXPANSION_NAME);

            // Load the translation servers from the REST Interface
            final RESTTranslationServerCollectionV1 translationServers = getRESTClient().getJSONTranslationServers(expandString);
            getRESTCollectionCache().add(RESTTranslationServerV1.class, translationServers, ALL_TRANSLATION_SERVER_KEY);
            return translationServers;
        } catch (Exception e) {
            log.debug("Failed to retrieve the Translation Servers", e);
            throw handleException(e);
        }
    }
}
