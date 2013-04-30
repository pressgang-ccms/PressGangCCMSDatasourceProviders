package org.jboss.pressgang.ccms.provider.exception;

import java.net.HttpURLConnection;

public class NotFoundException extends ProviderException {
    private static final long serialVersionUID = -4837225617843061287L;

    public NotFoundException() {
    }

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getHTTPStatus() {
        return HttpURLConnection.HTTP_NOT_FOUND;
    }
}
