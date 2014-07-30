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
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeStringV1;
import org.jboss.pressgang.ccms.rest.v1.entities.contentspec.RESTTranslatedCSNodeV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTTranslatedCSNodeStringV1Wrapper extends RESTBaseEntityWrapper<TranslatedCSNodeStringWrapper, RESTTranslatedCSNodeStringV1> implements TranslatedCSNodeStringWrapper {
    private final RESTTranslatedCSNodeV1 parent;

    protected RESTTranslatedCSNodeStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeStringV1 translatedString,
            boolean isRevision, final RESTTranslatedCSNodeV1 parent, boolean isNewEntity) {
        super(providerFactory, translatedString, isRevision, parent, isNewEntity);
        this.parent = parent;
    }

    protected RESTTranslatedCSNodeStringV1Wrapper(final RESTProviderFactory providerFactory, final RESTTranslatedCSNodeStringV1 translatedString,
            boolean isRevision, final RESTTranslatedCSNodeV1 parent, boolean isNewEntity, final Collection<String> expandedMethods) {
        super(providerFactory, translatedString, isRevision, parent, isNewEntity, expandedMethods);
        this.parent = parent;
    }

    @Override
    public TranslatedCSNodeStringWrapper clone(boolean deepCopy) {
        return new RESTTranslatedCSNodeStringV1Wrapper(getProviderFactory(), getEntity().clone(deepCopy), isRevisionEntity(), parent,
                isNewEntity());
    }

    @Override
    public String getTranslatedString() {
        return getProxyEntity().getTranslatedString();
    }

    @Override
    public void setTranslatedString(String translatedString) {
        getEntity().explicitSetTranslatedString(translatedString);
    }

    @Override
    public Boolean isFuzzy() {
        return getProxyEntity().getFuzzyTranslation();
    }

    @Override
    public void setFuzzy(Boolean fuzzy) {
        getEntity().explicitSetFuzzyTranslation(fuzzy);
    }

    @Override
    public String getLocale() {
        return getProxyEntity().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().explicitSetLocale(locale);
    }
}
