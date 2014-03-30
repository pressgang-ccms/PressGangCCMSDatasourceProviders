package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.CSInfoNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.CSNodeWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface CSInfoNodeProvider {

    CollectionWrapper<CSInfoNodeWrapper> getCSNodeInfoRevisions(int id, Integer revision, final CSNodeWrapper parent);

    CSInfoNodeWrapper newCSNodeInfo(CSNodeWrapper node);
}
