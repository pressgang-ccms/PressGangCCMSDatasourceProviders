package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.TranslatedCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.TranslatedContentSpecWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface TranslatedContentSpecProvider {
    TranslatedContentSpecWrapper getTranslatedContentSpec(int id);

    TranslatedContentSpecWrapper getTranslatedContentSpec(int id, Integer revision);

    UpdateableCollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes(int id, Integer revision);

    CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecRevisions(int id, Integer revision);

    TranslatedContentSpecWrapper createTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec) throws Exception;

    TranslatedContentSpecWrapper updateTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec) throws Exception;

    CollectionWrapper<TranslatedContentSpecWrapper> createTranslatedContentSpecs(
            CollectionWrapper<TranslatedContentSpecWrapper> translatedNodes) throws Exception;

    TranslatedContentSpecWrapper newTranslatedContentSpec();

    CollectionWrapper<TranslatedContentSpecWrapper> newTranslatedContentSpecCollection();
}
