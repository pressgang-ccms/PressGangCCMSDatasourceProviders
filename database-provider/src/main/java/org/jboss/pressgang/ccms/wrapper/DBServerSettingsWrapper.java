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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.hibernate.Session;
import org.jboss.pressgang.ccms.model.Locale;
import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.model.config.EntitiesConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedSetting;
import org.jboss.pressgang.ccms.provider.DBLocaleProvider;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.provider.DBTranslationServerProvider;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBServerUndefinedSettingCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBServerUndefinedSettingCollectionHandler;

public class DBServerSettingsWrapper extends DBBaseWrapper<ServerSettingsWrapper,
        ApplicationConfig> implements ServerSettingsWrapper {
    private final DBServerUndefinedSettingCollectionHandler undefinedSettingCollectionHandler;
    private final ApplicationConfig applicationConfig;
    private final EntitiesConfig entitiesConfig;
    private Locale cachedDefaultLocale;
    private UpdateableCollectionWrapper<LocaleWrapper> cachedLocales;
    private UpdateableCollectionWrapper<TranslationServerExtendedWrapper> cachedTranslationServers;

    public DBServerSettingsWrapper(final DBProviderFactory providerFactory, final ApplicationConfig applicationConfig) {
        super(providerFactory);
        this.applicationConfig = applicationConfig;
        undefinedSettingCollectionHandler = new DBServerUndefinedSettingCollectionHandler(applicationConfig);
        entitiesConfig = EntitiesConfig.getInstance();
    }

    @Override
    protected ApplicationConfig getEntity() {
        return applicationConfig;
    }

    @Override
    public boolean isReadOnly() {
        return getEntity().getReadOnly();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        getEntity().setReadOnly(readOnly);
    }

    @Override
    public int getJMSUpdateFrequency() {
        return getEntity().getJmsUpdateFrequency();
    }

    @Override
    public void setJMSUpdateFrequency(int jmsUpdateFrequency) {
        getEntity().setJmsUpdateFrequency(jmsUpdateFrequency);
    }

    @Override
    public String getUIUrl() {
        return getEntity().getUIUrl();
    }

    @Override
    public void setUIUrl(String uiUrl) {
        getEntity().setUIUrl(uiUrl);
    }

    @Override
    public String getDocbuilderUrl() {
        return getEntity().getDocBuilderUrl();
    }

    @Override
    public void setDocbuilderUrl(String docbuilderUrl) {
        getEntity().setDocBuilderUrl(docbuilderUrl);
    }

    @Override
    public List<Integer> getDocBookTemplateIds() {
        return getEntity().getDocBookTemplateStringConstantIds();
    }

    @Override
    public void setDocBookTemplateIds(List<Integer> docBookTemplateIds) {
        getEntity().setDocBookTemplateStringConstantIds(docBookTemplateIds);
    }

    @Override
    public List<Integer> getSEOCategoryIds() {
        return getEntity().getSEOCategoryIds();
    }

    @Override
    public void setSEOCategoryIds(List<Integer> seoCategoryIds) {
        getEntity().setSEOCategoryIds(seoCategoryIds);
    }

    @Override
    public LocaleWrapper getDefaultLocale() {
        if (cachedDefaultLocale == null) {
            final Session session = getEntityManager().unwrap(Session.class);
            cachedDefaultLocale = (Locale) session.bySimpleNaturalId(Locale.class).load(getEntity().getDefaultLocale());
        }
        return getWrapperFactory().create(cachedDefaultLocale, false, LocaleWrapper.class);
    }

    @Override
    public void setDefaultLocale(LocaleWrapper defaultLocale) {
        final Locale defaultLocaleEntity = defaultLocale == null ? null : ((Locale) defaultLocale.unwrap());
        cachedDefaultLocale = defaultLocaleEntity;
        getEntity().setDefaultLocale(defaultLocaleEntity == null ? null : defaultLocaleEntity.getValue());
    }

    @Override
    public UpdateableCollectionWrapper<LocaleWrapper> getLocales() {
        if (cachedLocales == null) {
            cachedLocales = (UpdateableCollectionWrapper<LocaleWrapper>) getDatabaseProvider().getProvider(DBLocaleProvider.class).getLocales();
        }

        return cachedLocales;
    }

    @Override
    public void setLocales(UpdateableCollectionWrapper<LocaleWrapper> locales) {
        cachedLocales = locales;
    }

    @Override
    public UpdateableCollectionWrapper<TranslationServerExtendedWrapper> getTranslationServers() {
        if (cachedTranslationServers == null) {
            cachedTranslationServers = (UpdateableCollectionWrapper<TranslationServerExtendedWrapper>) getDatabaseProvider().getProvider
                    (DBTranslationServerProvider.class).getTranslationServersExtended();
        }

        return cachedTranslationServers;
    }

    @Override
    public void setTranslationServers(UpdateableCollectionWrapper<TranslationServerExtendedWrapper> translationServers) {
        cachedTranslationServers = translationServers;
    }

    @Override
    public ServerEntitiesWrapper getEntities() {
        return getWrapperFactory().create(entitiesConfig, false);
    }

    @Override
    public UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> getUndefinedSettings() {
        final CollectionWrapper<ServerUndefinedSettingWrapper> collection = getWrapperFactory().createCollection(getEntity()
                .getUndefinedSettings(), UndefinedSetting.class, false, ServerUndefinedSettingWrapper.class, undefinedSettingCollectionHandler);
        return (UpdateableCollectionWrapper<ServerUndefinedSettingWrapper>) collection;
    }

    @Override
    public void setUndefinedSettings(UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> undefinedSettings) {
        if (undefinedSettings == null) return;
        final DBServerUndefinedSettingCollectionWrapper dbEntities = (DBServerUndefinedSettingCollectionWrapper) undefinedSettings;
        dbEntities.setHandler(undefinedSettingCollectionHandler);

        // Add new undefined settings and skip any existing settings
        final Set<UndefinedSetting> currentUndefinedEntities = new HashSet<UndefinedSetting>(getEntity().getUndefinedSettings());
        final Collection<UndefinedSetting> newTags = dbEntities.unwrap();
        for (final UndefinedSetting Setting : newTags) {
            if (currentUndefinedEntities.contains(Setting)) {
                currentUndefinedEntities.remove(Setting);
                continue;
            } else {
                try {
                    getEntity().addUndefinedSetting(Setting.getKey(), Setting.getValue());
                } catch (ConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Remove settings that should no longer exist in the collection
        for (final UndefinedSetting removeSetting : currentUndefinedEntities) {
            getEntity().removeProperty(removeSetting.getKey());
        }
    }
}
