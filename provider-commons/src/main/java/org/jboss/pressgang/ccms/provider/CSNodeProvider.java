package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.UpdateableCollectionWrapper;

public interface CSNodeProvider {
    CSNodeWrapper getCSNode(int id);

    CSNodeWrapper getCSNode(int id, Integer revision);

    CollectionWrapper<CSNodeWrapper> getCSNodesWithQuery(String query);

    CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedToNodes(int id, Integer revision);

    CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedFromNodes(int id, Integer revision);

    UpdateableCollectionWrapper<CSNodeWrapper> getCSNodeChildren(int id, Integer revision);

    CollectionWrapper<CSNodeWrapper> getCSNodeRevisions(int id, Integer revision);

    CSNodeWrapper newCSNode();

    UpdateableCollectionWrapper<CSNodeWrapper> newCSNodeCollection();

    CSRelatedNodeWrapper newCSRelatedNode();

    CSRelatedNodeWrapper newCSRelatedNode(final CSNodeWrapper node);

    UpdateableCollectionWrapper<CSRelatedNodeWrapper> newCSRelatedNodeCollection();
}
