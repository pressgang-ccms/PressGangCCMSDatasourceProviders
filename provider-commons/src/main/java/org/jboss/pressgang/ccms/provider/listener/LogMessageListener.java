package org.jboss.pressgang.ccms.provider.listener;

import org.jboss.pressgang.ccms.wrapper.LogMessageWrapper;

public interface LogMessageListener extends ProviderListener {
    void handleLogMessage(LogMessageWrapper logMessage);
}
