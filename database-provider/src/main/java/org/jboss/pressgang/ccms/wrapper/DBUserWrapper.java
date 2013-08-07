package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.base.DBBaseWrapper;

public class DBUserWrapper extends DBBaseWrapper<UserWrapper, User> implements UserWrapper {

    private final User user;

    public DBUserWrapper(final DBProviderFactory providerFactory, final User user, boolean isRevision) {
        super(providerFactory, isRevision, User.class);
        this.user = user;
    }

    @Override
    protected User getEntity() {
        return user;
    }

    @Override
    public String getUsername() {
        return getEntity().getUserName();
    }

    @Override
    public void setId(Integer id) {
        getEntity().setUserId(id);
    }

    @Override
    public User unwrap() {
        return user;
    }
}
