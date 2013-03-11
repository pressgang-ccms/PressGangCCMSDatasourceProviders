package org.jboss.pressgang.ccms.provider;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.model.utils.EnversUtilities;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public class DBUserProvider extends DBDataProvider implements UserProvider {
    protected DBUserProvider(EntityManager entityManager, DBWrapperFactory wrapperFactory) {
        super(entityManager, wrapperFactory);
    }

    @Override
    public UserWrapper getUser(int id) {
        final User user = getEntityManager().find(User.class, id);
        return getWrapperFactory().create(user, false);
    }

    @Override
    public UserWrapper getUser(int id, Integer revision) {
        if (revision == null) {
            return getUser(id);
        } else {
            final User dummyUser = new User();
            dummyUser.setUserId(id);

            return getWrapperFactory().create(EnversUtilities.getRevision(getEntityManager(), dummyUser, revision), true);
        }
    }

    @Override
    public CollectionWrapper<UserWrapper> getUsersByName(String name) {
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<User> user = criteriaBuilder.createQuery(User.class);
        final Root<User> from = user.from(User.class);
        user.select(from);
        user.where(criteriaBuilder.equal(from.get("userName"), name));

        final List<User> userList = getEntityManager().createQuery(user).getResultList();
        return getWrapperFactory().createCollection(userList, User.class, false);
    }

    @Override
    public CollectionWrapper<UserWrapper> getUserRevisions(int id, Integer revision) {
        final User user = new User();
        user.setUserId(id);
        final Map<Number, User> revisionMapping = EnversUtilities.getRevisionEntities(getEntityManager(), user);

        final List<User> revisions = new ArrayList<User>();
        for (final Map.Entry<Number, User> entry : revisionMapping.entrySet()) {
            revisions.add(entry.getValue());
        }

        return getWrapperFactory().createCollection(revisions, User.class, revision != null);
    }
}
