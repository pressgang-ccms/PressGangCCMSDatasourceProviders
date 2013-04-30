package org.jboss.pressgang.ccms.provider.exception;

import java.net.HttpURLConnection;

public class InternalServerErrorException extends ProviderException {
    private static final long serialVersionUID = -6477291923039606527L;

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(final String message) {
        super(message);
    }

    public InternalServerErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InternalServerErrorException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getHTTPStatus() {
        return HttpURLConnection.HTTP_INTERNAL_ERROR;
    }
}
