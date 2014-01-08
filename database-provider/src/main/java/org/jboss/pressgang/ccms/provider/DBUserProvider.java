package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.provider.listener.ProviderListener;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBUserProvider extends DBDataProvider implements UserProvider {
    protected DBUserProvider(EntityManager entityManager, DBProviderFactory providerFactory, List<ProviderListener> listeners) {
        super(entityManager, providerFactory, listeners);
    }

    @Override
    public UserWrapper getUser(int id) {
        return getWrapperFactory().create(getEntity(User.class, id), false);
    }

    @Override
    public UserWrapper getUser(int id, Integer revision) {
        if (revision == null) {
            return getUser(id);
        } else {
            return getWrapperFactory().create(getRevisionEntity(User.class, id, revision), true);
        }
    }

    @Override
    public CollectionWrapper<UserWrapper> getUsersByName(String name) {
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<User> user = criteriaBuilder.createQuery(User.class);
        final Root<User> from = user.from(User.class);
        user.select(from);
        user.where(criteriaBuilder.equal(from.get("userName"), name));

        return getWrapperFactory().createCollection(executeQuery(user), User.class, false);
    }

    @Override
    public UserWrapper getUserByName(final String name) {
        final CollectionWrapper<UserWrapper> users = getUsersByName(name);
        if (users != null && !users.isEmpty()) {
            for (final UserWrapper user : users.getItems()) {
                if (user.getUsername().equals(name)) {
                    return user;
                }
            }
        }

        return null;
    }

    @Override
    public CollectionWrapper<UserWrapper> getUserRevisions(int id, Integer revision) {
        final List<User> revisions = getRevisionList(User.class, id);
        return getWrapperFactory().createCollection(revisions, User.class, revision != null);
    }
}
