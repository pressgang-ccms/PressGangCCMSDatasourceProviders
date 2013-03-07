package org.jboss.pressgang.ccms.contentspec.wrapper.base;

import org.jboss.pressgang.ccms.contentspec.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.RESTBaseWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTPropertyTagV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBasePropertyTagV1;

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
