package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.CSNodeToCSNode;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCSRelatedNodeCollectionWrapper extends DBUpdateableCollectionWrapper<CSRelatedNodeWrapper, CSNodeToCSNode> {
    public DBCSRelatedNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSNodeToCSNode> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSRelatedNodeWrapper.class);
    }
}
