package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.CSNode;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCSNodeCollectionWrapper extends DBUpdateableCollectionWrapper<CSNodeWrapper, CSNode> {
    public DBCSNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSNode> items, boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSNodeWrapper.class);
    }
}
