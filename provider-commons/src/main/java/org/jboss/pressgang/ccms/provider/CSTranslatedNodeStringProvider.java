package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSTranslatedNodeStringProvider {
    CollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStringRevisions(int id, Integer revision);

    CSTranslatedNodeStringWrapper newCSTranslatedNodeString();

    CSTranslatedNodeStringWrapper newCSTranslatedNodeString(final CSTranslatedNodeWrapper parent);

    UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> newCSTranslatedNodeStringCollection();

    UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> newCSTranslatedNodeStringCollection(final CSTranslatedNodeWrapper parent);
}
