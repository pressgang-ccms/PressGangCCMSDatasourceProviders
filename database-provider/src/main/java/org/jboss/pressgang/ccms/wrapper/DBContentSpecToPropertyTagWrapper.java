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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.contentspec.ContentSpecToPropertyTag;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseToPropertyTagWrapper;

public class DBContentSpecToPropertyTagWrapper extends DBBaseToPropertyTagWrapper<PropertyTagInContentSpecWrapper, ContentSpecToPropertyTag> implements
        PropertyTagInContentSpecWrapper {
    private final ContentSpecToPropertyTag propertyTag;

    public DBContentSpecToPropertyTagWrapper(final DBProviderFactory providerFactory, final ContentSpecToPropertyTag propertyTag,
            boolean isRevision) {
        super(providerFactory, isRevision, ContentSpecToPropertyTag.class);
        this.propertyTag = propertyTag;
    }

    @Override
    protected ContentSpecToPropertyTag getEntity() {
        return propertyTag;
    }

    @Override
    public String getValue() {
        return getEntity().getValue();
    }

    @Override
    public Integer getRelationshipId() {
        return getEntity().getId();
    }

    @Override
    public void setValue(String value) {
        getEntity().setValue(value);
    }

    @Override
    public Boolean isValid() {
        return getEntity().isValid(getEntityManager(), getEntity().getRevision());
    }
}
