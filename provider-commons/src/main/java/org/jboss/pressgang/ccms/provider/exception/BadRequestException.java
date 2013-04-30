package org.jboss.pressgang.ccms.provider.exception;

import java.net.HttpURLConnection;

public class BadRequestException extends ProviderException {
    private static final long serialVersionUID = 1873701856473664818L;

    public BadRequestException() {
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getHTTPStatus() {
        return HttpURLConnection.HTTP_BAD_REQUEST;
    }
}
