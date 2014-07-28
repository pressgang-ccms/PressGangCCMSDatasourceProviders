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

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTUserV1Wrapper extends RESTBaseEntityWrapper<UserWrapper, RESTUserV1> implements UserWrapper {

    protected RESTUserV1Wrapper(final RESTProviderFactory providerFactory, final RESTUserV1 user, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, user, isRevision, isNewEntity);
    }

    protected RESTUserV1Wrapper(final RESTProviderFactory providerFactory, final RESTUserV1 user, boolean isRevision,
            boolean isNewEntity, final Collection<String> ignoreMethods) {
        super(providerFactory, user, isRevision, isNewEntity, ignoreMethods);
    }

    @Override
    public UserWrapper clone(boolean deepCopy) {
        return new RESTUserV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getUsername() {
        return getProxyEntity().getName();
    }
}
