/*
  Copyright 2011-2014 Red Hat

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBUserWrapper extends DBBaseEntityWrapper<UserWrapper, User> implements UserWrapper {

    private final User user;

    public DBUserWrapper(final DBProviderFactory providerFactory, final User user, boolean isRevision) {
        super(providerFactory, isRevision, User.class);
        this.user = user;
    }

    @Override
    protected User getEntity() {
        return user;
    }

    @Override
    public String getUsername() {
        return getEntity().getUserName();
    }

    @Override
    public void setId(Integer id) {
        getEntity().setUserId(id);
    }
}
