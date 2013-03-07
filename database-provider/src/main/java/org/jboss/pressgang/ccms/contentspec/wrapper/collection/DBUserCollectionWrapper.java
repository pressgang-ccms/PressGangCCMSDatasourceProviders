package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.model.User;

public class DBUserCollectionWrapper extends DBCollectionWrapper<UserWrapper, User> {
    public DBUserCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<User> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, UserWrapper.class);
    }
}
