package org.jboss.pressgang.ccms.wrapper;

import org.jboss.pressgang.ccms.wrapper.base.BaseCSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSNodeWrapper extends BaseCSNodeWrapper<CSNodeWrapper> {
    CollectionWrapper<CSNodeWrapper> getChildren();

    void setChildren(CollectionWrapper<CSNodeWrapper> nodes);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedToNodes();

    void setRelatedToNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedToNodes);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> getRelatedFromNodes();

    void setRelatedFromNodes(UpdateableCollectionWrapper<CSRelatedNodeWrapper> relatedFromNodes);

    CSNodeWrapper getParent();

    void setParent(CSNodeWrapper parent);

    ContentSpecWrapper getContentSpec();

    void setContentSpec(ContentSpecWrapper contentSpec);
}
