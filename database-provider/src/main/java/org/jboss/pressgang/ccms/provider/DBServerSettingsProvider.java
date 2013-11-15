package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.ServerSettingsWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBServerSettingsProvider extends DBDataProvider implements ServerSettingsProvider {
    protected DBServerSettingsProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory, List<ProviderListener> listeners) {
        super(entityManager, wrapperFactory, listeners);
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
