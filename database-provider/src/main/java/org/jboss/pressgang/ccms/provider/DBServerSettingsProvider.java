package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.ServerSettingsWrapper;

public class DBServerSettingsProvider extends DBDataProvider implements ServerSettingsProvider {
    protected DBServerSettingsProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public ServerSettingsWrapper getServerSettings() {
        return getWrapperFactory().create(ApplicationConfig.getInstance(), false);
    }

    @Override
    public ServerSettingsWrapper updateServerSettings(ServerSettingsWrapper serverSettings) {
        // TODO
        return null;
    }
}
