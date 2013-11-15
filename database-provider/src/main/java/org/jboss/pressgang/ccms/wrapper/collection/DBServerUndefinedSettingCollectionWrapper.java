package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.config.UndefinedSetting;
import org.jboss.pressgang.ccms.wrapper.ServerUndefinedSettingWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBServerUndefinedSettingCollectionWrapper extends DBUpdateableCollectionWrapper<ServerUndefinedSettingWrapper,
        UndefinedSetting> {
    public DBServerUndefinedSettingCollectionWrapper(DBWrapperFactory wrapperFactory, Collection<UndefinedSetting> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, ServerUndefinedSettingWrapper.class);
    }

    public DBServerUndefinedSettingCollectionWrapper(DBWrapperFactory wrapperFactory, Collection<UndefinedSetting> items,
            boolean isRevisionList, Class<ServerUndefinedSettingWrapper> wrapperClass) {
        super(wrapperFactory, items, isRevisionList, wrapperClass);
    }
}
