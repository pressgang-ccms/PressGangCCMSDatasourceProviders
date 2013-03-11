package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeStringWrapper;
import org.jboss.pressgang.ccms.wrapper.CSTranslatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSTranslatedNodeProvider {
    CSTranslatedNodeWrapper getCSTranslatedNode(int id);

    CSTranslatedNodeWrapper getCSTranslatedNode(int id, Integer revision);

    UpdateableCollectionWrapper<CSTranslatedNodeStringWrapper> getCSTranslatedNodeStrings(int id, Integer revision);

    CollectionWrapper<CSTranslatedNodeWrapper> getCSTranslatedNodeRevisions(int id, Integer revision);

    CollectionWrapper<CSTranslatedNodeWrapper> createCSTranslatedNodes(
            CollectionWrapper<CSTranslatedNodeWrapper> translatedNodes) throws Exception;

    CSTranslatedNodeWrapper newCSTranslatedNode();

    CollectionWrapper<CSTranslatedNodeWrapper> newCSTranslatedNodeCollection();
}
