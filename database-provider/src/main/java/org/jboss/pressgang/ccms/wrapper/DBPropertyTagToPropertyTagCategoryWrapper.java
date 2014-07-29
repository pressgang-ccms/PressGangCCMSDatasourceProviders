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
import org.jboss.pressgang.ccms.model.PropertyTagToPropertyTagCategory;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBasePropertyTagWrapper;

public class DBPropertyTagToPropertyTagCategoryWrapper extends DBBasePropertyTagWrapper<PropertyTagInPropertyCategoryWrapper,
        PropertyTagToPropertyTagCategory> implements PropertyTagInPropertyCategoryWrapper {

    private final PropertyTagToPropertyTagCategory propertyTagToPropertyCategory;

    public DBPropertyTagToPropertyTagCategoryWrapper(final DBProviderFactory providerFactory,
            final PropertyTagToPropertyTagCategory propertyTag, boolean isRevision) {
        super(providerFactory, isRevision, PropertyTagToPropertyTagCategory.class);
        this.propertyTagToPropertyCategory = propertyTag;
    }

    @Override
    protected PropertyTagToPropertyTagCategory getEntity() {
        return propertyTagToPropertyCategory;
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getEntity().getPropertyTag();
    }

    @Override
    public Integer getSort() {
        return getEntity().getSorting();
    }

    @Override
    public void setSort(Integer sort) {
        getEntity().setSorting(sort);
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }
}
