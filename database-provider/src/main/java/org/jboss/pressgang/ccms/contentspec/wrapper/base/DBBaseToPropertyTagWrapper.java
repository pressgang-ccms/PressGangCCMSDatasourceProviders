package org.jboss.pressgang.ccms.contentspec.wrapper.base;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.model.PropertyTag;
import org.jboss.pressgang.ccms.model.base.ToPropertyTag;

public abstract class DBBaseToPropertyTagWrapper<T extends BasePropertyTagWrapper<T>> extends DBBasePropertyTagWrapper<T> {

    protected abstract ToPropertyTag<?> getToPropertyTag();

    protected DBBaseToPropertyTagWrapper(final DBProviderFactory providerFactory, boolean isRevision) {
        super(providerFactory, isRevision);
    }

    @Override
    protected PropertyTag getPropertyTag() {
        return getToPropertyTag().getPropertyTag();
    }

    @Override
    public Integer getRevision() {
        return (Integer) getToPropertyTag().getRevision();
    }

    @Override
    public abstract ToPropertyTag<?> unwrap();
}
