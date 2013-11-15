package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.config.UndefinedSetting;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;

public class DBServerUndefinedSettingWrapper extends DBBaseWrapper<ServerUndefinedSettingWrapper,
        UndefinedSetting> implements ServerUndefinedSettingWrapper {
    private final UndefinedSetting entity;

    protected DBServerUndefinedSettingWrapper(final DBProviderFactory providerFactory, final UndefinedSetting entity) {
        super(providerFactory);
        this.entity = entity;
    }

    @Override
    protected UndefinedSetting getEntity() {
        return entity;
    }

    @Override
    public String getKey() {
        return getEntity().getKey();
    }

    @Override
    public void setKey(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(String value) {
        getEntity().setValue(value);
    }
}
