package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.jboss.pressgang.ccms.model.config.ApplicationConfig;
import org.jboss.pressgang.ccms.model.config.EntitiesConfig;
import org.jboss.pressgang.ccms.model.config.UndefinedSetting;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.DBServerUndefinedSettingCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.handler.DBApplicationUndefinedSettingCollectionHandler;

public class DBServerSettingsWrapper extends DBBaseWrapper<ServerSettingsWrapper,
        ApplicationConfig> implements ServerSettingsWrapper {
    private final DBApplicationUndefinedSettingCollectionHandler undefinedSettingCollectionHandler;
    private final ApplicationConfig applicationConfig;
    private final EntitiesConfig entitiesConfig;

    public DBServerSettingsWrapper(final DBProviderFactory providerFactory, final ApplicationConfig applicationConfig) {
        super(providerFactory);
        this.applicationConfig = applicationConfig;
        undefinedSettingCollectionHandler = new DBApplicationUndefinedSettingCollectionHandler(applicationConfig);
        entitiesConfig = EntitiesConfig.getInstance();
    }

    @Override
    protected ApplicationConfig getEntity() {
        return applicationConfig;
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
    public List<String> getLocales() {
        return getEntity().getLocales();
    }

    @Override
    public void setLocales(List<String> locales) {
        getEntity().setLocales(locales);
    }

    @Override
    public String getDefaultLocale() {
        return getEntity().getDefaultLocale();
    }

    @Override
    public void setDefaultLocale(String defaultLocale) {
        getEntity().setDefaultLocale(defaultLocale);
    }

    @Override
    public ServerEntitiesWrapper getEntities() {
        return getWrapperFactory().create(entitiesConfig, false);
    }

    @Override
    public UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> getUndefinedSettings() {
        final CollectionWrapper<ServerUndefinedSettingWrapper> collection = getWrapperFactory().createCollection(getEntity()
                .getUndefinedProperties(), UndefinedSetting.class, false, ServerUndefinedSettingWrapper.class, undefinedSettingCollectionHandler);
        return (UpdateableCollectionWrapper<ServerUndefinedSettingWrapper>) collection;
    }

    @Override
    public void setUndefinedSettings(UpdateableCollectionWrapper<ServerUndefinedSettingWrapper> undefinedSettings) {
        if (undefinedSettings == null) return;
        final DBServerUndefinedSettingCollectionWrapper dbEntities = (DBServerUndefinedSettingCollectionWrapper) undefinedSettings;
        dbEntities.setHandler(undefinedSettingCollectionHandler);

        // Add new undefined settings and skip any existing settings
        final Set<UndefinedSetting> currentUndefinedEntities = new HashSet<UndefinedSetting>(getEntity().getUndefinedProperties());
        final Collection<UndefinedSetting> newTags = dbEntities.unwrap();
        for (final UndefinedSetting Setting : newTags) {
            if (currentUndefinedEntities.contains(Setting)) {
                currentUndefinedEntities.remove(Setting);
                continue;
            } else {
                try {
                    getEntity().addUndefinedProperty(Setting.getKey(), Setting.getValue());
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
