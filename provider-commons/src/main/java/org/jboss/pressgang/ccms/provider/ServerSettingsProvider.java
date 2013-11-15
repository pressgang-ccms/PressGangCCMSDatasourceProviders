package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.ServerSettingsWrapper;

public interface ServerSettingsProvider {
    ServerSettingsWrapper getServerSettings();

    ServerSettingsWrapper updateServerSettings(ServerSettingsWrapper applicationSettings);
}
