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

    CollectionWrapper<TranslatedContentSpecWrapper> getTranslatedContentSpecsWithQuery(String query);

    TranslatedContentSpecWrapper createTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec);

    TranslatedContentSpecWrapper updateTranslatedContentSpec(TranslatedContentSpecWrapper translatedContentSpec);

    CollectionWrapper<TranslatedContentSpecWrapper> createTranslatedContentSpecs(
            CollectionWrapper<TranslatedContentSpecWrapper> translatedNodes);

    TranslatedContentSpecWrapper newTranslatedContentSpec();

    CollectionWrapper<TranslatedContentSpecWrapper> newTranslatedContentSpecCollection();
}
