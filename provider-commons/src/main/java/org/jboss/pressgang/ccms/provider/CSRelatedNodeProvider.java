package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSRelatedNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface CSRelatedNodeProvider extends CSNodeProvider {
    CollectionWrapper<CSRelatedNodeWrapper> getCSRelatedNodeRevisions(int id, Integer revision);
}
