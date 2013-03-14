package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedCSNodeProvider {
    TranslatedCSNodeWrapper getTranslatedCSNode(int id);

    TranslatedCSNodeWrapper getTranslatedCSNode(int id, Integer revision);

    UpdateableCollectionWrapper<TranslatedCSNodeStringWrapper> getTranslatedCSNodeStrings(int id, Integer revision);

    CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedCSNodeRevisions(int id, Integer revision);

    CollectionWrapper<TranslatedCSNodeWrapper> createTranslatedCSNodes(CollectionWrapper<TranslatedCSNodeWrapper> translatedNodes) throws
            Exception;

    TranslatedCSNodeWrapper newTranslatedCSNode();

    UpdateableCollectionWrapper<TranslatedCSNodeWrapper> newTranslatedCSNodeCollection();
}
