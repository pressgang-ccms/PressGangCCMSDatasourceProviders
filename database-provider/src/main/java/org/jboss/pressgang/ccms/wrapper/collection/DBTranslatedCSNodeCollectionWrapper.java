package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.contentspec.TranslatedCSNode;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;

public class DBTranslatedCSNodeCollectionWrapper extends DBUpdateableCollectionWrapper<TranslatedCSNodeWrapper, TranslatedCSNode> {
    public DBTranslatedCSNodeCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<TranslatedCSNode> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, TranslatedCSNodeWrapper.class);
    }
}
