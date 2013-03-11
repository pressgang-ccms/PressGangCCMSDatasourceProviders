package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.UserWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface UserProvider {
    UserWrapper getUser(int id);

    UserWrapper getUser(int id, Integer revision);

    CollectionWrapper<UserWrapper> getUsersByName(String name);

    CollectionWrapper<UserWrapper> getUserRevisions(int id, Integer revision);
}
