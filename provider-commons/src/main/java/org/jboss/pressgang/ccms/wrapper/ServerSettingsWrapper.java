package org.jboss.pressgang.ccms.wrapper;

import java.util.List;

import org.jboss.pressgang.ccms.wrapper.base.BaseWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface ServerSettingsWrapper extends BaseWrapper<ServerSettingsWrapper> {
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
