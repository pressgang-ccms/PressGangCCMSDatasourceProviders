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
import org.jboss.pressgang.ccms.rest.v1.entities.RESTImageV1;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTLanguageImageV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTLanguageImageV1Wrapper extends RESTBaseEntityWrapper<LanguageImageWrapper, RESTLanguageImageV1> implements LanguageImageWrapper {

    protected RESTLanguageImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageImageV1 image, boolean isRevision,
            final RESTImageV1 parent, boolean isNewEntity) {
        super(providerFactory, image, isRevision, parent, isNewEntity);
    }

    protected RESTLanguageImageV1Wrapper(final RESTProviderFactory providerFactory, final RESTLanguageImageV1 image, boolean isRevision,
            final RESTImageV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, image, isRevision, parent, isNewEntity, expandedMethods);
    }

    @Override
    protected RESTImageV1 getParentEntity() {
        return (RESTImageV1) super.getParentEntity();
    }

    @Override
    public LanguageImageWrapper clone(boolean deepCopy) {
        return new RESTLanguageImageV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(),
                (deepCopy ? getParentEntity().clone(deepCopy) : getParentEntity()), isNewEntity());
    }

    @Override
    public String getFilename() {
        return getProxyEntity().getFilename();
    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public byte[] getImageData() {
        return getProxyEntity().getImageData();
    }

    @Override
    public byte[] getImageDataBase64() {
        return getProxyEntity().getImageDataBase64();
    }

    @Override
    public byte[] getThumbnail() {
        return getProxyEntity().getThumbnail();
    }
}
