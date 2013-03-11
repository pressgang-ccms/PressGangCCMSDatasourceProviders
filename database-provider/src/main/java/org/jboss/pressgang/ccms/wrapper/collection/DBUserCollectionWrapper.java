package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.User;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.UserWrapper;

public class DBUserCollectionWrapper extends DBCollectionWrapper<UserWrapper, User> {
    public DBUserCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<User> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, UserWrapper.class);
    }
}
