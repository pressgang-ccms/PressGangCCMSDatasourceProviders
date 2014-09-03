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

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLocaleV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTLocaleV1Wrapper extends RESTBaseEntityWrapper<LocaleWrapper, RESTLocaleV1> implements LocaleWrapper {

    protected RESTLocaleV1Wrapper(final RESTProviderFactory providerFactory, final RESTLocaleV1 entity, boolean isNewEntity) {
        super(providerFactory, entity, isNewEntity);
    }

    protected RESTLocaleV1Wrapper(final RESTProviderFactory providerFactory, final RESTLocaleV1 entity, boolean isNewEntity,
            final Collection<String> expandedMethods) {
        super(providerFactory, entity, isNewEntity, expandedMethods);
    }

    @Override
    public LocaleWrapper clone(boolean deepCopy) {
        return new RESTLocaleV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isNewEntity(),
                getProxyProcessedMethodNames());
    }

    @Override
    public String getValue() {
        return getProxyEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getProxyEntity().explicitSetValue(value);
    }

    @Override
    public String getTranslationValue() {
        return getProxyEntity().getTranslationValue();
    }

    @Override
    public void setTranslationValue(String value) {
        getProxyEntity().explicitSetTranslationValue(value);
    }

    @Override
    public String getBuildValue() {
        return getProxyEntity().getBuildValue();
    }

    @Override
    public void setBuildValue(String value) {
        getProxyEntity().explicitSetBuildValue(value);
    }
}
