package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.provider.DBProviderFactory;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBUserWrapper extends DBBaseWrapper<UserWrapper> implements UserWrapper {

    private final User user;

    public DBUserWrapper(final DBProviderFactory providerFactory, final User user, boolean isRevision) {
        super(providerFactory, isRevision);
        this.user = user;
    }

    protected User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return getUser().getUserName();
    }

    @Override
    public Integer getId() {
        return getUser().getUserId();
    }

    @Override
    public void setId(Integer id) {
        getUser().setUserId(id);
    }

    @Override
    public Integer getRevision() {
        return (Integer) getUser().getRevision();
    }

    @Override
    public CollectionWrapper<UserWrapper> getRevisions() {
        return getWrapperFactory().createCollection(EnversUtilities.getRevisionEntities(getEntityManager(), getUser()), User.class, true);
    }

    @Override
    public User unwrap() {
        return user;
    }
}
