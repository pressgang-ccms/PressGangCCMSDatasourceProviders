/*
  Copyright 2011-2014 Red Hat, Inc

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

package org.jboss.pressgang.ccms.wrapper.base;

import javax.persistence.EntityManager;

import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public abstract class DBBaseWrapper<T extends BaseWrapper<T>, U> implements BaseWrapper<T> {
    private final DBProviderFactory providerFactory;
    private final DBWrapperFactory wrapperFactory;

    protected DBBaseWrapper(final DBProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
        wrapperFactory = providerFactory.getWrapperFactory();
    }

    protected DBProviderFactory getDatabaseProvider() {
        return providerFactory;
    }

    protected DBWrapperFactory getWrapperFactory() {
        return wrapperFactory;
    }

    protected EntityManager getEntityManager() {
        return getDatabaseProvider().getEntityManager();
    }

    protected abstract U getEntity();

    @Override
    public U unwrap() {
        return getEntity();
    }
}
