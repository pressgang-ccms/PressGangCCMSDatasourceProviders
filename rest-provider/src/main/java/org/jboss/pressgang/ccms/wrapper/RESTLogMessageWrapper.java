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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTLogDetailsV1;

public class RESTLogMessageWrapper implements LogMessageWrapper {
    final RESTLogDetailsV1 logDetails;

    public RESTLogMessageWrapper(final RESTLogDetailsV1 logDetails) {
        this.logDetails = logDetails;
    }

    @Override
    public String getMessage() {
        return logDetails.getMessage();
    }

    @Override
    public void setMessage(String message) {
        logDetails.setMessage(message);
    }

    @Override
    public Integer getFlags() {
        return logDetails.getFlag();
    }

    @Override
    public void setFlags(Integer flags) {
        logDetails.setFlag(flags);
    }

    @Override
    public String getUser() {
        if (logDetails.getUser() == null) {
            return null;
        } else if (logDetails.getUser().getId() != null) {
            return logDetails.getUser().getId().toString();
        } else {
            return logDetails.getUser().getName();
        }
    }

    @Override
    public void setUser(String user) {
        final RESTUserV1 userEntity = new RESTUserV1();
        if (user.matches("^\\d+$")) {
            userEntity.setId(Integer.parseInt(user));
        } else {
            userEntity.setName(user);
        }
        logDetails.setUser(userEntity);
    }

    @Override
    public RESTLogDetailsV1 unwrap() {
        return logDetails;
    }
}
