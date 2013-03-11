package org.jboss.pressgang.ccms.wrapper.collection;

import java.util.Collection;

import org.jboss.pressgang.ccms.model.StringConstants;
import org.jboss.pressgang.ccms.wrapper.DBWrapperFactory;
import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;

public class DBStringConstantCollectionWrapper extends DBCollectionWrapper<StringConstantWrapper, StringConstants> {
    public DBStringConstantCollectionWrapper(final DBWrapperFactory wrapperFactory, final Collection<StringConstants> items,
            boolean isRevisionList) {
        super(wrapperFactory, items, isRevisionList, StringConstantWrapper.class);
    }
}
