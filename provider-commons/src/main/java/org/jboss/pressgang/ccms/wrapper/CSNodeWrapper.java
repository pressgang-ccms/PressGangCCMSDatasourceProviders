package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSNodeWrapper extends BaseCSNodeWrapper<CSNodeWrapper> {
    UpdateableCollectionWrapper<CSNodeWrapper> getChildren();

    void setChildren(UpdateableCollectionWrapper<CSNodeWrapper> nodes);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes();

    void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes();

    void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes);

    CSNodeWrapper getParent();

    void setParent(CSNodeWrapper parent);

    ContentSpecWrapper getContentSpec();

    CSNodeWrapper getNextNode();

    void setNextNode(CSNodeWrapper nextNode);

    void setContentSpec(ContentSpecWrapper contentSpec);

    String getInheritedCondition();

    CollectionWrapper<TranslatedCSNodeWrapper> getTranslatedNodes();
}
