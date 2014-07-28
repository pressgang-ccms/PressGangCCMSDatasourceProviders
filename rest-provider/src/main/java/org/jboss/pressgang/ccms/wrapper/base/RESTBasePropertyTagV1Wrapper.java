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

package org.jboss.pressgang.ccms.wrapper.base;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBasePropertyTagV1;

public abstract class RESTBasePropertyTagV1Wrapper<T extends BasePropertyTagWrapper<T>, U extends RESTBasePropertyTagV1<U, ?,
        ?>> extends RESTBaseEntityWrapper<T, U> implements BasePropertyTagWrapper<T> {

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, isNewEntity, expandedMethods);
    }

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, parent, isNewEntity);
    }

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }
}
