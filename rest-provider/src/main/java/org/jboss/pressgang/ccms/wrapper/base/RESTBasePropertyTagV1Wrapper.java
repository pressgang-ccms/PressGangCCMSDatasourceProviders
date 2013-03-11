package org.jboss.pressgang.ccms.wrapper.base;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBasePropertyTagV1;
import org.jboss.pressgang.ccms.wrapper.RESTBaseWrapper;

public abstract class RESTBasePropertyTagV1Wrapper<T extends BasePropertyTagWrapper<T>, U extends RESTBasePropertyTagV1<U, ?,
        ?>> extends RESTBaseWrapper<T, U> implements BasePropertyTagWrapper<T> {

    protected RESTBasePropertyTagV1Wrapper(final RESTProviderFactory providerFactory, boolean isRevision) {
        super(providerFactory, isRevision);
    }

    @Override
    public String getName() {
        return getProxyEntity().getName();
    }
}
