package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.StringConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface StringConstantProvider {
    StringConstantWrapper getStringConstant(int id);

    StringConstantWrapper getStringConstant(int id, Integer revision);

    CollectionWrapper<StringConstantWrapper> getStringConstantRevisions(int id, Integer revision);
}
