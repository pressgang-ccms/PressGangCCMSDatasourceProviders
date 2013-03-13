package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedCSNodeStringProvider {
    CollectionWrapper<TranslatedCSNodeStringWrapper> getCSTranslatedNodeStringRevisions(int id, Integer revision);

    TranslatedCSNodeStringWrapper newCSTranslatedNodeString();

    TranslatedCSNodeStringWrapper newCSTranslatedNodeString(final TranslatedCSNodeWrapper parent);

    UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection();

    UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> newCSTranslatedNodeStringCollection(final TranslatedCSNodeWrapper parent);
}
