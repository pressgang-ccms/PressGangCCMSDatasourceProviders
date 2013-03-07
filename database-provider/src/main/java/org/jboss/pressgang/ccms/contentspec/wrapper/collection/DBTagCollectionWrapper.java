package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.TagWrapper;
import org.jboss.pressgang.ccms.model.Tag;

public class DBTagCollectionWrapper extends DBCollectionWrapper<TagWrapper, Tag> {
    public DBTagCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<Tag> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TagWrapper.class);
    }
}
