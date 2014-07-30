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

import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerSettingsV1;
import org.jboss.pressgang.ccms.wrapper.RESTEntityWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.RESTServerSettingsV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.ServerSettingsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTServerSettingsProvider extends RESTDataProvider implements ServerSettingsProvider {
    private static Logger LOG = LoggerFactory.getLogger(RESTServerSettingsProvider.class);

    protected RESTServerSettingsProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    protected RESTServerSettingsV1 getRESTServerSettingsV1() {
        try {
            return getRESTClient().getJSONServerSettings();
        } catch (Exception e) {
            LOG.debug("Failed to retrieve the Server Settings", e);
            throw handleException(e);
        }
    }

    @Override
    public ServerSettingsWrapper getServerSettings() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getRESTServerSettingsV1())
                .build();
    }

    @Override
    public ServerSettingsWrapper updateServerSettings(ServerSettingsWrapper serverSettings) {
        try {
            final RESTServerSettingsV1 settings = ((RESTServerSettingsV1Wrapper) serverSettings).unwrap();

            // Clean the entity to remove anything that doesn't need to be sent to the server
            cleanEntityForSave(settings);

            final RESTServerSettingsV1 updatedSettings;
            updatedSettings = getRESTClient().updateJSONServerSettings(settings);
            if (updatedSettings != null) {
                return RESTEntityWrapperBuilder.newBuilder()
                        .providerFactory(getProviderFactory())
                        .entity(updatedSettings)
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to update the Server Settings", e);
            throw handleException(e);
        }
    }
}
