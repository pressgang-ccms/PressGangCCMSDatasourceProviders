package org.jboss.pressgang.ccms.provider.exception;

import java.net.HttpURLConnection;

public class ForbiddenException extends ProviderException {
    private static final long serialVersionUID = -2235448622869517550L;

    public ForbiddenException() {
    }

    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getHTTPStatus() {
        return HttpURLConnection.HTTP_FORBIDDEN;
    }
}
