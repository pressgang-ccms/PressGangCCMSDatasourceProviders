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

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNodeString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBTranslatedCSNodeStringWrapper extends DBBaseEntityWrapper<TranslatedCSNodeStringWrapper,
        TranslatedCSNodeString> implements TranslatedCSNodeStringWrapper {
    private final TranslatedCSNodeString translatedCSNodeString;

    public DBTranslatedCSNodeStringWrapper(final DBProviderFactory providerFactory, final TranslatedCSNodeString translatedCSNodeString,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedCSNodeString.class);
        this.translatedCSNodeString = translatedCSNodeString;
    }

    @Override
    protected TranslatedCSNodeString getEntity() {
        return translatedCSNodeString;
    }

    @Override
    public Integer getId() {
        return getEntity().getId();
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTranslatedCSNodeStringId(id);
    }

    @Override
    public String getTranslatedString() {
        return getEntity().getTranslatedString();
    }

    @Override
    public void setTranslatedString(String translatedString) {
        getEntity().setTranslatedString(translatedString);
    }

    @Override
    public Boolean isFuzzy() {
        return getEntity().getFuzzyTranslation();
    }

    @Override
    public void setFuzzy(Boolean fuzzy) {
        getEntity().setFuzzyTranslation(fuzzy);
    }

    @Override
    public String getLocale() {
        return getEntity().getLocale();
    }

    @Override
    public void setLocale(String locale) {
        getEntity().setLocale(locale);
    }
}
