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

import org.jboss.pressgang.ccms.rest.v1.collections.RESTLocaleCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.constants.RESTv1Constants;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLocaleV1;
import org.jboss.pressgang.ccms.wrapper.LocaleWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTLocaleProvider extends RESTDataProvider implements LocaleProvider {
    private static final Logger log = LoggerFactory.getLogger(RESTLocaleProvider.class);
    private static final List<String> ALL_LOCALES_KEY = Arrays.asList("all");

    public RESTLocaleProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTLocaleV1 loadLocale(Integer id, String expandString) {
        return getRESTClient().getJSONLocale(id, expandString);
    }

    @Override
    public LocaleWrapper getLocale(int id) {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTLocale(id))
                .build();
    }

    public RESTLocaleV1 getRESTLocale(int id) {
        try {
            final RESTLocaleV1 locale;
            if (getRESTEntityCache().containsKeyValue(RESTLocaleV1.class, id)) {
                locale = getRESTEntityCache().get(RESTLocaleV1.class, id);
            } else {
                locale = loadLocale(id, null);
                getRESTEntityCache().add(locale);
            }
            return locale;
        } catch (Exception e) {
            log.debug("Failed to retrieve Locale " + id, e);
            throw handleException(e);
        }
    }

    public RESTLocaleCollectionV1 getRESTLocales() {
        try {
            // Check the cache first
            if (getRESTCollectionCache().containsKey(RESTLocaleV1.class, ALL_LOCALES_KEY)) {
                return getRESTCollectionCache().get(RESTLocaleV1.class, RESTLocaleCollectionV1.class, ALL_LOCALES_KEY);
            }

            // We need to expand the locales
            final String expandString = getExpansionString(RESTv1Constants.LOCALES_EXPANSION_NAME);

            // Load the locales from the REST Interface
            final RESTLocaleCollectionV1 locales = getRESTClient().getJSONLocales(expandString);
            getRESTCollectionCache().add(RESTLocaleV1.class, locales, ALL_LOCALES_KEY);
            return locales;
        } catch (Exception e) {
            log.debug("Failed to retrieve the Locales", e);
            throw handleException(e);
        }
    }

    @Override
    public CollectionWrapper<LocaleWrapper> getLocales() {
        return RESTCollectionWrapperBuilder.<LocaleWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getRESTLocales())
                .build();
    }
}
