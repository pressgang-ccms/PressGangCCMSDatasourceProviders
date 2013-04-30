package org.jboss.pressgang.ccms.provider;

import org.jboss.pressgang.ccms.wrapper.ImageWrapper;
import org.jboss.pressgang.ccms.wrapper.collection.CollectionWrapper;

public interface ImageProvider {
    ImageWrapper getImage(int id);

    ImageWrapper getImage(int id, Integer revision);

    CollectionWrapper<ImageWrapper> getImageRevisions(int id, Integer revision);
}
