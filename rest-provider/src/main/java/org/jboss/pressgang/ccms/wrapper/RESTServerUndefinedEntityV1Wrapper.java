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

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerUndefinedEntityV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;

public class RESTServerUndefinedEntityV1Wrapper extends RESTBaseWrapper<ServerUndefinedEntityWrapper, RESTServerUndefinedEntityV1> implements ServerUndefinedEntityWrapper {
    protected RESTServerUndefinedEntityV1Wrapper(RESTProviderFactory providerFactory, RESTServerUndefinedEntityV1 entity) {
        super(providerFactory, entity);
    }

    @Override
    public String getKey() {
        return getEntity().getKey();
    }

    @Override
    public void setKey(String key) {
        getEntity().setKey(key);
    }

    @Override
    public Integer getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(Integer value) {
        getEntity().explicitSetValue(value);
    }
}
