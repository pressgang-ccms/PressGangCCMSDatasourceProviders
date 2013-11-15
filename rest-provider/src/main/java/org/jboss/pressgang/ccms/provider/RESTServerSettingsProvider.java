package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTServerSettingsV1;
import org.jboss.pressgang.ccms.wrapper.RESTServerSettingsV1Wrapper;
import org.jboss.pressgang.ccms.wrapper.ServerSettingsWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTServerSettingsProvider extends RESTDataProvider implements ServerSettingsProvider {
    private static Logger LOG = LoggerFactory.getLogger(RESTServerSettingsProvider.class);

    protected RESTServerSettingsProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(restManager, wrapperFactory);
    }

    @Override
    public ServerSettingsWrapper getServerSettings() {
        final RESTServerSettingsV1 serverSettings = getRESTClient().getJSONServerSettings();
        return getWrapperFactory().create(serverSettings, false);
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
                return getWrapperFactory().create(updatedSettings, false);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.debug("Failed to update the Server Settings", e);
            throw handleException(e);
        }
    }
}
