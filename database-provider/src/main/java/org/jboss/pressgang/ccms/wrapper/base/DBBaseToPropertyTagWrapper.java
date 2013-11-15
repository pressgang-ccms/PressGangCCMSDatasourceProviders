package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.base.ToPropertyTag;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;

public abstract class DBBaseToPropertyTagWrapper<T extends BasePropertyTagWrapper<T>,
        U extends ToPropertyTag<?>> extends DBBasePropertyTagWrapper<T, U> {

    protected DBBaseToPropertyTagWrapper(final DBProviderFactory providerFactory, boolean isRevision, Class<U> clazz) {
        super(providerFactory, isRevision, clazz);
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getEntity().getPropertyTag();
    }
}
