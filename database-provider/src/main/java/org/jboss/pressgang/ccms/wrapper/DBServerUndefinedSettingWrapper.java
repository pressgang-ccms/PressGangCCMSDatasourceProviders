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

package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.config.UndefinedSetting;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;

public class DBServerUndefinedSettingWrapper extends DBBaseWrapper<ServerUndefinedSettingWrapper,
        UndefinedSetting> implements ServerUndefinedSettingWrapper {
    private final UndefinedSetting entity;

    protected DBServerUndefinedSettingWrapper(final DBProviderFactory providerFactory, final UndefinedSetting entity) {
        super(providerFactory);
        this.entity = entity;
    }

    @Override
    protected UndefinedSetting getEntity() {
        return entity;
    }

    @Override
    public String getKey() {
        return getEntity().getKey();
    }

    @Override
    public void setKey(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().setValue(value);
    }
}
