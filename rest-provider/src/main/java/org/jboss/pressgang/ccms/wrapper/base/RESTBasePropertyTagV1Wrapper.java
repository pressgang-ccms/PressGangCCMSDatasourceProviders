package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBasePropertyTagV1;

public abstract class RESTBasePropertyTagV1Wrapper<T extends BasePropertyTagWrapper<T>, U extends RESTBasePropertyTagV1<U, ?,
        ?>> extends RESTBaseEntityWrapper<T, U> implements BasePropertyTagWrapper<T> {

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, isNewEntity);
    }

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            RESTBaseEntityV1<?, ?, ?> parent, boolean isNewEntity) {
        super(providerFactory, entity, isRevision, parent, isNewEntity);
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }
}
