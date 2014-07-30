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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;

public abstract class RESTBaseWrapper<T extends BaseWrapper<T>, U> implements BaseWrapper<T> {
    private final RESTProviderFactory providerFactory;
    private final U entity;

    protected RESTBaseWrapper(final RESTProviderFactory providerFactory, final U entity) {
        this.providerFactory = providerFactory;
        this.entity = entity;
    }

    protected RESTProviderFactory getProviderFactory() {
        return providerFactory;
    }

    public U unwrap() {
        return getEntity();
    }

    protected U getEntity() {
        return entity;
    }
}
