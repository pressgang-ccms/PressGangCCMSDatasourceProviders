package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.config.UndefinedEntity;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;

public class DBServerUndefinedEntityWrapper extends DBBaseWrapper<ServerUndefinedEntityWrapper, UndefinedEntity> implements ServerUndefinedEntityWrapper {
    private final UndefinedEntity entity;

    protected DBServerUndefinedEntityWrapper(final DBProviderFactory providerFactory, final UndefinedEntity entity) {
        super(providerFactory);
        this.entity = entity;
    }

    @Override
    protected UndefinedEntity getEntity() {
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
    public Integer getValue() {
        return getEntity().getValue();
    }

    @Override
    public void setValue(Integer value) {
        getEntity().setValue(value);
    }
}
