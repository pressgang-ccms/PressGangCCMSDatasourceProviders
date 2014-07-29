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

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBasePropertyTagWrapper;

public class DBPropertyTagWrapper extends DBBasePropertyTagWrapper<PropertyTagWrapper, PropertyTag> implements PropertyTagWrapper {

    private final PropertyTag propertyTag;

    public DBPropertyTagWrapper(final DBProviderFactory providerFactory, final PropertyTag propertyTag, boolean isRevision) {
        super(providerFactory, isRevision, PropertyTag.class);
        this.propertyTag = propertyTag;
    }

    @Override
    protected PropertyTag getEntity() {
        return propertyTag;
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getEntity();
    }
}
