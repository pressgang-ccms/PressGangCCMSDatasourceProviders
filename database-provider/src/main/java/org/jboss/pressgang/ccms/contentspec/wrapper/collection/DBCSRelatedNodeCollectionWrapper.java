package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;

public class DBCSRelatedNodeCollectionWrapper extends DBUpdateableCollectionWrapper<CSRelatedNodeWrapper, CSNodeToCSNode> {
    public DBCSRelatedNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSNodeToCSNode> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSRelatedNodeWrapper.class);
    }
}
