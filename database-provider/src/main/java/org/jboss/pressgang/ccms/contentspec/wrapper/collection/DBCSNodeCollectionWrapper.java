package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.contentspec.CSNode;

public class DBCSNodeCollectionWrapper extends DBCollectionWrapper<CSNodeWrapper, CSNode> {
    public DBCSNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSNode> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSNodeWrapper.class);
    }
}
