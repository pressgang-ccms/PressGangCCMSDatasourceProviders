package org.jboss.pressgang.ccms.provider.exception;

public abstract class ProviderException extends RuntimeException {
    private static final long serialVersionUID = 7674692273300524778L;

    protected ProviderException() {
    }

    protected ProviderException(final String message) {
        super(message);
    }

    protected ProviderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected ProviderException(final Throwable cause) {
        super(cause);
    }

    public abstract int getHTTPStatus();
}
