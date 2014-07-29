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

package org.jboss.pressgang.ccms.utils;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.elements.base.RESTBaseElementV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;

public class RESTWrapperKey {
    private final Object o;
    private RESTBaseEntityV1<?, ?, ?> parent;
    private final Class<?> wrapperClass;

    public RESTWrapperKey(final RESTBaseElementV1<?> o) {
        this(o, null, null);
    }

    public RESTWrapperKey(final RESTBaseElementV1<?> o, final RESTBaseEntityV1<?, ?, ?> parent) {
        this(o, parent, null);
    }

    public RESTWrapperKey(final RESTBaseElementV1<?> o, final Class<?> wrapperClass) {
        this(o, null, wrapperClass);
    }

    public RESTWrapperKey(final RESTBaseElementV1<?> o, final RESTBaseEntityV1<?, ?, ?> parent, final Class<?> wrapperClass) {
        this.o = o;
        this.parent = parent;
        this.wrapperClass = wrapperClass;
    }

    public RESTWrapperKey(final RESTCollectionV1<?, ?> o) {
        this(o, null, null);
    }

    public RESTWrapperKey(final RESTCollectionV1<?, ?> o, RESTBaseEntityV1<?, ? ,?> parent) {
        this(o, parent, null);
    }

    public RESTWrapperKey(final RESTCollectionV1<?, ?> o, final Class<?> wrapperClass) {
        this(o, null, wrapperClass);
    }

    public RESTWrapperKey(final RESTCollectionV1<?, ?> o, RESTBaseEntityV1<?, ? ,?> parent, final Class<?> wrapperClass) {
        this.o = o;
        this.parent = parent;
        this.wrapperClass = wrapperClass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof RESTWrapperKey)) return false;

        final RESTWrapperKey other = (RESTWrapperKey) o;
        return this.o == o && new EqualsBuilder().append(wrapperClass, other.wrapperClass).append(parent,
                other.parent).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(o).append(wrapperClass).append(parent).toHashCode();
    }
}
