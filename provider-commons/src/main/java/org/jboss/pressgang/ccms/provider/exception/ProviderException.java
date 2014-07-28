/*
  Copyright 2011-2014 Red Hat

  This file is part of PresGang CCMS.

  PresGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PresGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PresGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

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
