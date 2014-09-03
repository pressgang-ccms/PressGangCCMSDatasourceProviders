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
import org.jboss.pressgang.ccms.rest.v1.entities.RESTTranslationServerV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseTranslationServerV1Wrapper;

public class RESTTranslationServerV1Wrapper extends RESTBaseTranslationServerV1Wrapper<TranslationServerWrapper, RESTTranslationServerV1>
        implements TranslationServerWrapper {

    protected RESTTranslationServerV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslationServerV1 entity,
            boolean isNewEntity) {
        super(providerFactory, entity, isNewEntity);
    }

    protected RESTTranslationServerV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslationServerV1 entity,
            boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, entity, isNewEntity, expandedMethods);
    }

    @Override
    public TranslationServerWrapper clone(boolean deepCopy) {
        return new RESTTranslationServerV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isNewEntity(),
                getProxyProcessedMethodNames());
    }

    @Override
    public void setName(String name) {
        getEntity().explicitSetName(name);
    }

    @Override
    public void setUrl(String url) {
        getEntity().explicitSetUrl(url);
    }
}
