package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import java.util.List;

import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.DBLogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;

public class DBLogMessageProvider extends DBDataProvider implements LogMessageProvider {
    protected DBLogMessageProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public LogMessageWrapper createLogMessage() {
        return new DBLogMessageWrapper();
    }
}
