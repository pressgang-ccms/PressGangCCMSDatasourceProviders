package org.jboss.pressgang.ccms.provider.exception;

public class UpgradeException extends ProviderException {
    private static final long serialVersionUID = 152319077310154496L;

    public UpgradeException() {
    }

    public UpgradeException(final String message) {
        super(message);
    }

    public UpgradeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UpgradeException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getHTTPStatus() {
        return 426;
    }
}
