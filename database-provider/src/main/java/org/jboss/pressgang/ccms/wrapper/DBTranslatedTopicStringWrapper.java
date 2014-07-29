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

import org.jboss.pressgang.ccms.model.TranslatedTopicString;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseEntityWrapper;

public class DBTranslatedTopicStringWrapper extends DBBaseEntityWrapper<TranslatedTopicStringWrapper,
        TranslatedTopicString> implements TranslatedTopicStringWrapper {

    private final TranslatedTopicString translatedTopicString;

    public DBTranslatedTopicStringWrapper(final DBProviderFactory providerFactory, final TranslatedTopicString translatedTopicString,
            boolean isRevision) {
        super(providerFactory, isRevision, TranslatedTopicString.class);
        this.translatedTopicString = translatedTopicString;
    }

    @Override
    protected TranslatedTopicString getEntity() {
        return translatedTopicString;
    }

    @Override
    public void setId(Integer id) {
        getEntity().setTranslatedTopicStringID(id);
    }

    @Override
    public String getOriginalString() {
        return getEntity().getOriginalString();
    }

    @Override
    public void setOriginalString(String originalString) {
        getEntity().setOriginalString(originalString);
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
}
