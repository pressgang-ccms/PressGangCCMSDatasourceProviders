package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.config.UndefinedEntity;
import org.jboss.pressgang.ccms.wrapper.ServerUndefinedEntityWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBServerUndefinedEntityCollectionWrapper extends DBUpdateableCollectionWrapper<ServerUndefinedEntityWrapper,
        UndefinedEntity> {
    public DBServerUndefinedEntityCollectionWrapper(DBWrapperFactory wrapperFactory, Collection<UndefinedEntity> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, ServerUndefinedEntityWrapper.class);
    }

    public DBServerUndefinedEntityCollectionWrapper(DBWrapperFactory wrapperFactory, Collection<UndefinedEntity> items,
            boolean isRevisionList, Class<ServerUndefinedEntityWrapper> wrapperClass) {
        super(wrapperFactory, items, isRevisionList, wrapperClass);
    }
}
