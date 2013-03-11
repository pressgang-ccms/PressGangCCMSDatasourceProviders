package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNode;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBCSTranslatedNodeCollectionWrapper extends DBCollectionWrapper<CSTranslatedNodeWrapper, CSTranslatedNode> {
    public DBCSTranslatedNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSTranslatedNode> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSTranslatedNodeWrapper.class);
    }
}
