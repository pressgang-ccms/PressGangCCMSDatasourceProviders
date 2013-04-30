package org.jboss.pressgang.ccms.provider.exception;

import java.net.HttpURLConnection;

public class UnauthorisedException extends ProviderException {
    private static final long serialVersionUID = -2235448622869517550L;

    public UnauthorisedException() {
    }

    public UnauthorisedException(final String message) {
        super(message);
    }

    public UnauthorisedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnauthorisedException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getHTTPStatus() {
        return HttpURLConnection.HTTP_UNAUTHORIZED;
    }
}
