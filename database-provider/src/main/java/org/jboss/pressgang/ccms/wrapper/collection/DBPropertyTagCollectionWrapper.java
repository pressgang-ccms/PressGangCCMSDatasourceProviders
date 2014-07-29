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

package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.PropertyTagWrapper;

public class DBPropertyTagCollectionWrapper extends DBCollectionWrapper<PropertyTagWrapper, PropertyTag> {
    public DBPropertyTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<PropertyTag> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, PropertyTagWrapper.class);
    }
}
