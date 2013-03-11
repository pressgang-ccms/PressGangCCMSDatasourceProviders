package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.provider.RESTProviderFactory;
import org.jboss.pressgang.ccms.proxy.RESTEntityProxyFactory;
import org.jboss.pressgang.ccms.rest.v1.entities.RESTUserV1;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class RESTUserV1Wrapper extends RESTBaseWrapper<UserWrapper, RESTUserV1> implements UserWrapper {

    private final RESTUserV1 user;

    protected RESTUserV1Wrapper(final RESTProviderFactory providerFactory, final RESTUserV1 user, boolean isRevision) {
        super(providerFactory, isRevision);
        this.user = RESTEntityProxyFactory.createProxy(providerFactory, user, isRevision);
    }

    @Override
    protected RESTUserV1 getProxyEntity() {
        return user;
    }

    @Override
    public Integer getId() {
        return getProxyEntity().getId();
    }

    @Override
    public Integer getRevision() {
        return getProxyEntity().getRevision();
    }

    @Override
    public CollectionWrapper<UserWrapper> getRevisions() {
        return getWrapperFactory().createCollection(getProxyEntity().getRevisions(), RESTUserV1.class, isRevisionEntity());
    }

    @Override
    public UserWrapper clone(boolean deepCopy) {
        return new RESTUserV1Wrapper(getProviderFactory(), unwrap().clone(deepCopy), isRevisionEntity());
    }

    @Override
    public String getUsername() {
        return getProxyEntity().getName();
    }
}
