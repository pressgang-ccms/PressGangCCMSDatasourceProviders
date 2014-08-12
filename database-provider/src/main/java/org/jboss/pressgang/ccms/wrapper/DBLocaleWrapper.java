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

import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBLocaleWrapper extends DBBaseEntityWrapper<LocaleWrapper, Locale> implements LocaleWrapper {
    private final Locale locale;

    public DBLocaleWrapper(final DBProviderFactory providerFactory, final Locale locale, boolean isRevision) {
        super(providerFactory, isRevision, Locale.class);
        this.locale = locale;
    }

    @Override
    protected Locale getEntity() {
        return locale;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setLocaleId(id);
    }

    @Override
    public String getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().setValue(value);
    }

    @Override
    public String getTranslationValue() {
        return getEntity().getTranslationValue();
    }

    @Override
    public void setTranslationValue(String translationValue) {
        getEntity().setTranslationValue(translationValue);
    }

    @Override
    public String getBuildValue() {
        return getEntity().getBuildValue();
    }

    @Override
    public void setBuildValue(String buildValue) {
        getEntity().setBuildValue(buildValue);
    }
}
