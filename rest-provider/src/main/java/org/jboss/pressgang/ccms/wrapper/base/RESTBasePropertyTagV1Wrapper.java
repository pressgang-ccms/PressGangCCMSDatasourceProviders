package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBasePropertyTagV1;

public abstract class RESTBasePropertyTagV1Wrapper<T extends BasePropertyTagWrapper<T>, U extends RESTBasePropertyTagV1<U, ?,
        ?>> extends RESTBaseWrapper<T, U> implements BasePropertyTagWrapper<T> {

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision) {
        super(providerFactory, entity, isRevision);
    }

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, U entity, boolean isRevision,
            RESTBaseEntityV1<?, ?, ?> parent) {
        super(providerFactory, entity, isRevision, parent);
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }
}
