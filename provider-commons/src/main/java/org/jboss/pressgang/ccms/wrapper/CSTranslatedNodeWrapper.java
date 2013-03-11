package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.EntityWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface CSTranslatedNodeWrapper extends EntityWrapper<CSTranslatedNodeWrapper> {
    Integer getNodeId();

    void setNodeId(Integer id);

    Integer getNodeRevision();

    void setNodeRevision(Integer revision);

    String getZanataId();

    CollectionWrapper<CSTranslatedNodeStringWrapper> getTranslatedStrings();

    void setTranslatedStrings(CollectionWrapper<CSTranslatedNodeStringWrapper> translatedStrings);

    CSNodeWrapper getCSNode();

    void setCSNode(CSNodeWrapper node);
}
