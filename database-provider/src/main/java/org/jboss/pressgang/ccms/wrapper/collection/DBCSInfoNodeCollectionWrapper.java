package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.CSInfoNode;
import org.jboss.pressgang.ccms.wrapper.CSInfoNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCSInfoNodeCollectionWrapper extends DBUpdateableCollectionWrapper<CSInfoNodeWrapper, CSInfoNode> {
    public DBCSInfoNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSInfoNode> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSInfoNodeWrapper.class);
    }
}
