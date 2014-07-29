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

import org.jboss.pressgang.ccms.model.BlobConstants;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBBlobConstantWrapper extends DBBaseEntityWrapper<BlobConstantWrapper, BlobConstants> implements BlobConstantWrapper {

    private final BlobConstants blobConstant;

    public DBBlobConstantWrapper(final DBProviderFactory providerFactory, final BlobConstants blobConstant, boolean isRevision) {
        super(providerFactory, isRevision, BlobConstants.class);
        this.blobConstant = blobConstant;
    }

    @Override
    protected BlobConstants getEntity() {
        return blobConstant;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setBlobConstantsId(id);
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
    public byte[] getValue() {
        return getEntity().getConstantValue();
    }

    @Override
    public void setValue(byte[] value) {
        getEntity().setConstantValue(value);
    }
}
