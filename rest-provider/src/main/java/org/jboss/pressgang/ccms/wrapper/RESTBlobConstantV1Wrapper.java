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

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTBlobConstantV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTBlobConstantV1Wrapper extends RESTBaseEntityWrapper<BlobConstantWrapper, RESTBlobConstantV1> implements BlobConstantWrapper {

    protected RESTBlobConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTBlobConstantV1 blobConstant,
            boolean isRevision, boolean isNewEntity) {
        super(providerFactory, blobConstant, isRevision, isNewEntity);
    }

    protected RESTBlobConstantV1Wrapper(final RESTProviderFactory providerFactory, final RESTBlobConstantV1 blobConstant,
            boolean isRevision, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, blobConstant, isRevision, isNewEntity, expandedMethods);
    }

    @Override
    public BlobConstantWrapper clone(boolean deepCopy) {
        return new RESTBlobConstantV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }

    @Override
    public void setName(String name) {
        getEntity().explicitSetName(name);
    }

    @Override
    public byte[] getValue() {
        return getProxyEntity().getValue();
    }

    @Override
    public void setValue(byte[] value) {
        getEntity().explicitSetValue(value);
    }
}
