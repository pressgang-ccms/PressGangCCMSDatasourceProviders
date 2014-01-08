package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTLogDetailsV1;
import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;
import org.jboss.pressgang.ccms.wrapper.RESTLogMessageWrapper;

public class RESTLogMessageProvider extends RESTDataProvider implements LogMessageProvider {
    public RESTLogMessageProvider(final RESTProviderFactory providerFactory) {
        super(providerFactory);
    }

    @Override
    public LogMessageWrapper createLogMessage() {
        return new RESTLogMessageWrapper(new RESTLogDetailsV1());
    }
}
