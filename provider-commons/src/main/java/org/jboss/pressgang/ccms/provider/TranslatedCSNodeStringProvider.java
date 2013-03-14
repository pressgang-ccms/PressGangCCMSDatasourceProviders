package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedCSNodeStringProvider {
    CollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStringRevisions(int id, Integer revision);

    TranslatedCSNodeStringWrapper newTranslatedCSNodeString(final TranslatedCSNodeWrapper parent);

    UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection(final TranslatedCSNodeWrapper parent);
}
