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

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBStringConstantWrapper extends DBBaseEntityWrapper<StringConstantWrapper, StringConstants> implements StringConstantWrapper {
    private final StringConstants stringConstant;

    public DBStringConstantWrapper(final DBProviderFactory providerFactory, final StringConstants stringConstant, boolean isRevision) {
        super(providerFactory, isRevision, StringConstants.class);
        this.stringConstant = stringConstant;
    }

    @Override
    protected StringConstants getEntity() {
        return stringConstant;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setStringConstantsId(id);
    }

    @Override
    public String getName() {
        return getEntity().getConstantName();
    }

    @Override
    public void setName(String name) {
        getEntity().setConstantName(name);
    }

    @Override
    public String getValue() {
        return getEntity().getConstantValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().setConstantValue(value);
    }
}
