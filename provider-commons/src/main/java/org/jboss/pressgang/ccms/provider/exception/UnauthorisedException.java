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
