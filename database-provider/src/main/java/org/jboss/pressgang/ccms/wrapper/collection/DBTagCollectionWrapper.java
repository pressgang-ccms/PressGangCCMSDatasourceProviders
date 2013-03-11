package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.Tag;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.TagWrapper;

public class DBTagCollectionWrapper extends DBCollectionWrapper<TagWrapper, Tag> {
    public DBTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<Tag> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TagWrapper.class);
    }
}