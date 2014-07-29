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

import java.util.List;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.collections.RESTServerUndefinedSettingCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.elements.RESTServerSettingsV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.RESTCollectionWrapperBuilder;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public class RESTServerSettingsV1Wrapper extends RESTBaseWrapper<ServerSettingsWrapper, RESTServerSettingsV1> implements ServerSettingsWrapper {
    protected RESTServerSettingsV1Wrapper(final RESTProviderFactory providerFactory, final RESTServerSettingsV1 entity) {
        super(providerFactory, entity);
    }

    @Override
    public boolean isReadOnly() {
        return getEntity().isReadOnly();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        getEntity().explicitSetReadOnly(readOnly);
    }

    @Override
    public int getJMSUpdateFrequency() {
        return getEntity().getJmsUpdateFrequency();
    }

    @Override
    public void setJMSUpdateFrequency(int jmsUpdateFrequency) {
        getEntity().explicitSetJmsUpdateFrequency(jmsUpdateFrequency);
    }

    @Override
    public String getUIUrl() {
        return getEntity().getUiUrl();
    }

    @Override
    public void setUIUrl(String uiUrl) {
        getEntity().explicitSetUiUrl(uiUrl);
    }

    @Override
    public String getDocbuilderUrl() {
        return getEntity().getDocBuilderUrl();
    }

    @Override
    public void setDocbuilderUrl(String docbuilderUrl) {
        getEntity().explicitSetDocBuilderUrl(docbuilderUrl);
    }

    @Override
    public List<Integer> getDocBookTemplateIds() {
        return getEntity().getDocBookTemplateIds();
    }

    @Override
    public void setDocBookTemplateIds(List<Integer> docBookTemplateIds) {
        getEntity().explicitSetDocBookTemplateIds(docBookTemplateIds);
    }

    @Override
    public List<Integer> getSEOCategoryIds() {
        return getEntity().getSeoCategoryIds();
    }

    @Override
    public void setSEOCategoryIds(List<Integer> seoCategoryIds) {
        getEntity().explicitSetSeoCategoryIds(seoCategoryIds);
    }

    @Override
    public List<String> getLocales() {
        return getEntity().getLocales();
    }

    @Override
    public void setLocales(List<String> locales) {
        getEntity().explicitSetLocales(locales);
    }

    @Override
    public String getDefaultLocale() {
        return getEntity().getDefaultLocale();
    }

    @Override
    public void setDefaultLocale(String defaultLocale) {
        getEntity().explicitSetDefaultLocale(defaultLocale);
    }

    @Override
    public ServerEntitiesWrapper getEntities() {
        return RESTEntityWrapperBuilder.newBuilder()
                .providerFactory(getProviderFactory())
                .entity(getEntity().getEntities())
                .build();
    }

    @Override
    public UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> getUndefinedSettings() {
        return (UpdateableCollectionWrapper<ServerUndefinedSettingWrapper>) RESTCollectionWrapperBuilder.<ServerUndefinedSettingWrapper>newBuilder()
                .providerFactory(getProviderFactory())
                .collection(getEntity().getUndefinedSettings())
                .build();
    }

    @Override
    public void setUndefinedSettings(UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> undefinedSettings) {
        getEntity().explicitSetUndefinedSettings(
                undefinedSettings == null ? null : (RESTServerUndefinedSettingCollectionV1) undefinedSettings.unwrap());
    }
}
