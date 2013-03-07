package org.jboss.pressgang.ccms.contentspec.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.contentspec.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.contentspec.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.model.StringConstants;

public class DBStringConstantCollectionWrapper extends DBCollectionWrapper<StringConstantWrapper, StringConstants> {
    public DBStringConstantCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<StringConstants> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, StringConstantWrapper.class);
    }
}
