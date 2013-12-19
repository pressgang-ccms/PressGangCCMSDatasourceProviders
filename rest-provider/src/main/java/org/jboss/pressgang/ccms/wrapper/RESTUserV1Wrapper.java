package org.jboss.pressgang.ccms.wrapper;

import java.util.Collection;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.wrapper.base.RESTBaseEntityWrapper;

public class RESTUserV1Wrapper extends RESTBaseEntityWrapper<UserWrapper, RESTUserV1> implements UserWrapper {

    protected RESTUserV1Wrapper(final RESTProviderFactory providerFactory, final RESTUserV1 user, boolean isRevision, boolean isNewEntity) {
        super(providerFactory, user, isRevision, isNewEntity);
    }

    protected RESTUserV1Wrapper(final RESTProviderFactory providerFactory, final RESTUserV1 user, boolean isRevision,
            boolean isNewEntity, final Collection<String> ignoreMethods) {
        super(providerFactory, user, isRevision, isNewEntity, ignoreMethods);
    }

    @Override
    public UserWrapper clone(boolean deepCopy) {
        return new RESTUserV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity(), isNewEntity());
    }

    @Override
    public String getUsername() {
        return getProxyEntity().getName();
    }
}
