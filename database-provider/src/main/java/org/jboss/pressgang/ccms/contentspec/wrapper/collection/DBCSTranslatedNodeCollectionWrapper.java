package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.model.contentspec.CSTranslatedNode;

public class DBCSTranslatedNodeCollectionWrapper extends DBCollectionWrapper<CSTranslatedNodeWrapper, CSTranslatedNode> {
    public DBCSTranslatedNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<CSTranslatedNode> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, CSTranslatedNodeWrapper.class);
    }
}
