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

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ServerSettingsWrapper extends BaseWrapper<ServerSettingsWrapper> {
    boolean isReadOnly();
    void setReadOnly(boolean readOnly);
    int getJMSUpdateFrequency();
    void setJMSUpdateFrequency(int jmsUpdateFrequency);
    String getUIUrl();
    void setUIUrl(String uiUrl);
    String getDocbuilderUrl();
    void setDocbuilderUrl(String docbuilderUrl);
    List<Integer> getDocBookTemplateIds();
    void setDocBookTemplateIds(List<Integer> docBookTemplateIds);
    List<Integer> getSEOCategoryIds();
    void setSEOCategoryIds(List<Integer> seoCategoryIds);
    List<String> getLocales();
    void setLocales(List<String> locales);
    String getDefaultLocale();
    void setDefaultLocale(String defaultLocale);
    ServerEntitiesWrapper getEntities();
    UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> getUndefinedSettings();
    void setUndefinedSettings(UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> undefinedSettings);
}
