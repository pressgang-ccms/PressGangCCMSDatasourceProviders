package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.BlobConstantWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface BlobConstantProvider {
    BlobConstantWrapper getBlobConstant(int id);

    BlobConstantWrapper getBlobConstant(int id, Integer revision);

    CollectionWrapper<BlobConstantWrapper> getBlobConstantRevisions(int id, Integer revision);
}
