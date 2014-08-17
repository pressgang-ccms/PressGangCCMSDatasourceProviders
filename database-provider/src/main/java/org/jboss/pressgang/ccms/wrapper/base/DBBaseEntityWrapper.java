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

import org.jboss.pressgang.ccms.model.base.PressGangEntity;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;

public abstract class DBBaseEntityWrapper<T extends EntityWrapper<T>, U extends PressGangEntity> extends DBBaseWrapper<T, U> implements EntityWrapper<T> {
    protected final Class<U> clazz;
    protected final Class<T> wrapperClazz;

    protected DBBaseEntityWrapper(final DBProviderFactory providerFactory, Class<U> clazz) {
        this(providerFactory, null, clazz);
    }

    protected DBBaseEntityWrapper(final DBProviderFactory providerFactory, Class<T> wrapperClazz, Class<U> clazz) {
        super(providerFactory);
        this.clazz = clazz;
        this.wrapperClazz = wrapperClazz;
    }

    @Override
    public T clone(boolean deepCopy) {
        throw new UnsupportedOperationException("The clone method has no implementation.");
    }

    @Override
    public Integer getId() {
        return getEntity().getId();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof DBBaseEntityWrapper && getEntity() != null) {
            return o == null ? false : getEntity().equals(((DBBaseEntityWrapper) o).getEntity());
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        if (getEntity() != null) {
            return getEntity().hashCode();
        } else {
            return super.hashCode();
        }
    }
}
