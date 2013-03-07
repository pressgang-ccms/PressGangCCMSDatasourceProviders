package org.jboss.pressgang.ccms.contentspec.wrapper.base;

import org.jboss.pressgang.ccms.contentspec.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBBaseWrapper;
import org.jboss.pressgang.ccms.model.PropertyTag;

public abstract class DBBasePropertyTagWrapper<T extends BasePropertyTagWrapper<T>> extends DBBaseWrapper<T> implements
        BasePropertyTagWrapper<T> {


    protected DBBasePropertyTagWrapper(final DBProviderFactory providerFactory, boolean isRevision) {
        super(providerFactory, isRevision);
    }

    protected abstract PropertyTag getPropertyTag();

    @Override
    public Integer getId() {
        return getPropertyTag().getId();
    }

    @Override
    public void setId(Integer id) {
        getPropertyTag().setPropertyTagId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getPropertyTag().getRevision();
    }

    @Override
    public String getName() {
        return getPropertyTag().getPropertyTagName();
    }

    @Override
    public void setName(String name) {
        getPropertyTag().setPropertyTagName(name);
    }
}
